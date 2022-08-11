package network.tron;

import network.tron.dto.TronResponse;
import network.tron.job.InfiniteTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.BiConsumer;

import static network.tron.util.CommonUtils.prepareMessage;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class TronWatcherBot extends AbilityBot {

    public final ResponseHandler responseHandler;
    private final RestTemplate restTemplate;

    public TronWatcherBot(RestTemplate restTemplate, InfiniteTaskExecutor taskExecutor) {
        super("5545788950:AAFZbGyBWAMceRrIosO93OH0KedYU-ZN-X4", "TronWatcherBot");
        this.responseHandler = new ResponseHandler(sender, db);
        this.restTemplate = restTemplate;
        taskExecutor.start(this);
    }

    @Override
    public long creatorId() {
        return 332894225L;
    }

    public Ability startReply() {
        return Ability
                .builder()
                .name(Constants.COMMAND_START)
                .info("replies to start")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Ability addresses() {
        return Ability
                .builder()
                .name(Constants.COMMAND_ADDRESSES)
                .info("added addresses list")
                .locality(Locality.USER)
                .privacy(Privacy.ADMIN)
                .action(ctx -> responseHandler.replyToAddresses(ctx.chatId()))
                .build();
    }

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (bab, upd) ->
                responseHandler.replyToButtons(
                        getChatId(upd),
                        AbilityUtils.getUser(upd).getId(),
                        upd.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }

    public Reply replyToAddressInput() {
        BiConsumer<BaseAbilityBot, Update> action = (bab, upd) -> {
            try {
                Long currentTimestamp = null;
                Long userId = AbilityUtils.getUser(upd).getId();
                try {
                    TronResponse response = restTemplate.getForObject(Constants.URI_ADDRESS + upd.getMessage().getText(), TronResponse.class);
                    if (null == response) {
                        throw new RuntimeException();
                    }
                    currentTimestamp = response.getMeta().getAt();
                } catch (HttpClientErrorException.BadRequest e) {
                    execute(prepareMessage(upd.getMessage().getChatId(), Constants.REPLY_ADDRESS_VALIDATION_FAILED));
                } catch (Exception e) {
                    execute(prepareMessage(upd.getMessage().getChatId(), Constants.REPLY_SOMETHING_WRONG));
                    return;
                }

                responseHandler.addresses.put(userId, new Address(upd.getMessage().getChatId(), upd.getMessage().getText(), currentTimestamp));
                responseHandler.waitingAddressUsers.remove(userId);
                execute(prepareMessage(upd.getMessage().getChatId(), Constants.REPLY_ADDRESS_ADDED));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        };

        return Reply.of(action, Flag.TEXT, upd -> responseHandler.waitingAddressUsers.contains(AbilityUtils.getUser(upd).getId()));
    }
}