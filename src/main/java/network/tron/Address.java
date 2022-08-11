package network.tron;

import java.io.Serializable;
import java.util.Objects;

public final class Address implements Serializable {

    private Long chatId;
    private String address;
    private Long timestampLastCheck;

    public Address(Long chatId, String address, Long timestampLastCheck) {
        this.chatId = chatId;
        this.address = address;
        this.timestampLastCheck = timestampLastCheck;
    }

    public Long chatId() {
        return chatId;
    }

    public String address() {
        return address;
    }

    public Long timestampLastCheck() {
        return timestampLastCheck;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTimestampLastCheck(Long timestampLastCheck) {
        this.timestampLastCheck = timestampLastCheck;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Address) obj;
        return Objects.equals(this.chatId, that.chatId) &&
               Objects.equals(this.address, that.address) &&
               Objects.equals(this.timestampLastCheck, that.timestampLastCheck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, address, timestampLastCheck);
    }

    @Override
    public String toString() {
        return "Address[" +
               "chatId=" + chatId + ", " +
               "address=" + address + ", " +
               "timestampLastCheck=" + timestampLastCheck + ']';
    }

}
