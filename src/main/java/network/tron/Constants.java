package network.tron;

public interface Constants {

    String COMMAND_START = "start";
    String COMMAND_ADDRESSES = "addresses";

    String REPLY_START = "Hello! Set your TRC-20 address to watch.";

    String BUTTON_SET_ADDRESS = "Set address";
    String BUTTON_REMOVE_ADDRESS = "Remove address";

    String REPLY_SPECIFY_ADDRESS = "Please specify your TRC-20 address:";
    String REPLY_ADDRESS_VALIDATION_FAILED = "Unknown TRC-20 address, please specify correct address:";
    String REPLY_ADDRESS_ADDED = "Thanks! We set this address as yours to watch.";
    String REPLY_ADDRESS_REMOVED = "Thanks! Address unlinked from you.";
    String REPLY_SOMETHING_WRONG = "Something went wrong.";
    String REPLY_TX_MESSAGE = """
            New transaction:
            Tx: [%s](https://tronscan.org/#/transaction/%s)
            From: [%s](https://tronscan.org/#/address/%s)
            To: [%s](https://tronscan.org/#/address/%s)
            Amount: %s %s
            """;

    String DB_ADDRESSES = "SAVED_ADDRESSES";
    String DB_WAITING_ADDRESSES = "WAITING_ADDRESSES";

    String URI_TX = "https://api.trongrid.io/v1/accounts/{address}/transactions/trc20?limit=200&only_confirmed=true&min_timestamp=";
    String URI_ADDRESS = "https://api.trongrid.io/v1/accounts/";
}
