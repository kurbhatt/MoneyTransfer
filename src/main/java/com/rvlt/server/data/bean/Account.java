package com.rvlt.server.data.bean;

import java.math.BigDecimal;

public class Account {

    Integer accountId;
    BigDecimal currentBalance;

    public Account() {
    }

    public Account(Integer accountId, BigDecimal currentBalance) {
        this.accountId = accountId;
        this.currentBalance = currentBalance;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
