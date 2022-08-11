package network.tron.job;

import network.tron.Address;
import network.tron.Constants;
import network.tron.TronWatcherBot;
import network.tron.dto.Transaction;
import network.tron.dto.TronResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static network.tron.util.CommonUtils.prepareMessage;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class TronWatchTask implements Task {

    private final RestTemplate restTemplate;

    public TronWatchTask(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void execute(TronWatcherBot bot) {
        try {
            Map<Long, Address> addressesToUpdate = new HashMap<>(100);
            bot.responseHandler.addresses.forEach((userId, address) -> {
                TronResponse response = restTemplate.getForObject(Constants.URI_TX + address.timestampLastCheck(), TronResponse.class);

                if (null != response && !isEmpty(response.getData())) {
                    response.getData().forEach(tx -> {
                        String amount = new BigDecimal(tx.getValue()).movePointLeft(6).stripTrailingZeros().toString().replace(".", ",");
                        String msg = Constants.REPLY_TX_MESSAGE.formatted(
                                tx.getTransactionId(),
                                tx.getTransactionId(),
                                tx.getFrom(),
                                tx.getFrom(),
                                tx.getTo(),
                                tx.getTo(),
                                amount,
                                tx.getTokenInfo().getSymbol());

                        try {
                            SendMessage messagePrepared = prepareMessage(address.chatId(), msg);
                            messagePrepared.enableMarkdownV2(true);
                            bot.sender().execute(messagePrepared);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    });
                    //noinspection OptionalGetWithoutIsPresent
                    Long lastTxTimestamp = response
                            .getData()
                            .stream()
                            .max(Comparator.comparing(Transaction::getBlockTimestamp))
                            .get()
                            .getBlockTimestamp();
                    address.setTimestampLastCheck(lastTxTimestamp + 1000);
                    addressesToUpdate.put(userId, address);
                }

                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            bot.responseHandler.addresses.putAll(addressesToUpdate);
            bot.db().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}