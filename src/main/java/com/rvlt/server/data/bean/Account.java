package com.rvlt.server.data.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public void addBalance(BigDecimal amount){
        setCurrentBalance(getCurrentBalance().add(amount).setScale(2, RoundingMode.CEILING));
    }

    public void deductBalance(BigDecimal amount){
        setCurrentBalance(getCurrentBalance().subtract(amount).setScale(2, RoundingMode.CEILING));
    }
}
