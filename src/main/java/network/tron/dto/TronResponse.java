package network.tron.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TronResponse {

    @JsonProperty("data")
    private List<Transaction> data;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("meta")
    private Meta meta;

    public static class Meta {

        @JsonProperty("at")
        private Long at;

        @JsonProperty("page_size")
        private Integer pageSize;

        public Long getAt() {
            return at;
        }

        public void setAt(Long at) {
            this.at = at;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }
    }

    public List<Transaction> getData() {
        return data;
    }

    public void setData(List<Transaction> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
