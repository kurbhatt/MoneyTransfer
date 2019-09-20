package com.rvlt.server.data.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountResponse extends BaseStatus {

    @JsonProperty("account-data")
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
