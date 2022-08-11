package network.tron;

import com.google.common.base.Joiner;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.Set;

import static network.tron.util.CommonUtils.prepareMessage;

public class ResponseHandler {

    private final MessageSender sender;
    public final Set<Long> waitingAddressUsers;
    public final Map<Long, Address> addresses;

    public ResponseHandler(MessageSender sender, DBContext db) {
        this.sender = sender;
        this.waitingAddressUsers = db.getSet(Constants.DB_WAITING_ADDRESSES);
        this.addresses = db.getMap(Constants.DB_ADDRESSES);
    }

    public void replyToStart(Long chatId) {
        try {
            SendMessage sendMessage = prepareMessage(chatId, Constants.REPLY_START);
            sendMessage.setReplyMarkup(KeyboardFactory.manageAddressButtons());
            sender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToAddresses(Long chatId) {
        try {
            if (addresses.isEmpty()) {
                sender.execute(prepareMessage(chatId, "[empty]"));
            } else {
                sender.execute(prepareMessage(chatId, Joiner.on("\n").withKeyValueSeparator(": ").join(addresses)));
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToButtons(Long chatId, long userId, String buttonId) {
        try {
            switch (buttonId) {
                case Constants.BUTTON_SET_ADDRESS -> replyToSetAddress(chatId, userId);
                case Constants.BUTTON_REMOVE_ADDRESS -> replyToRemoveAddress(chatId, userId);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToSetAddress(long chatId, long userId) throws TelegramApiException {
        sender.execute(prepareMessage(chatId, Constants.REPLY_SPECIFY_ADDRESS));
        waitingAddressUsers.add(userId);
    }

    public void replyToRemoveAddress(long chatId, long userId) throws TelegramApiException {
        sender.execute(prepareMessage(chatId, Constants.REPLY_ADDRESS_REMOVED));
        addresses.remove(userId);
    }

}
