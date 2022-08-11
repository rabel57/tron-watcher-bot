package network.tron.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public final class CommonUtils {

    private CommonUtils() {}

    public static SendMessage prepareMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        return sendMessage;
    }
}
