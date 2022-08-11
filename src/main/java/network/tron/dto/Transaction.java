package network.tron.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("token_info")
    private TokenInfo tokenInfo;

    @JsonProperty("block_timestamp")
    private Long blockTimestamp;

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private String value;

    public static class TokenInfo {

        @JsonProperty("symbol")
        private String symbol;

        @JsonProperty("address")
        private String address;

        @JsonProperty("decimals")
        private Integer decimals;

        @JsonProperty("name")
        private String name;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getDecimals() {
            return decimals;
        }

        public void setDecimals(Integer decimals) {
            this.decimals = decimals;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public Long getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(Long blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
