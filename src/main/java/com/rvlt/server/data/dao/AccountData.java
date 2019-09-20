package com.rvlt.server.data.dao;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rvlt.server.data.bean.Account;
import com.rvlt.server.data.bean.BaseStatus;
import com.rvlt.server.util.Constants;

public class AccountData {
    private static volatile Map<Integer, Account> accountMap = new ConcurrentHashMap<>();

    public static Map<Integer, Account> getAccountMap() {
        synchronized (ConcurrentHashMap.class){
            return accountMap;
        }
    }

    public static int addNewAccount(Account account) {
        if(getAccountMap().containsKey(account.getAccountId())){
            return 0;
        }else{
            getAccountMap().put(account.getAccountId(), account);
            return 1;
        }
    }

    public static void transferMoney(Integer from, Integer to, BigDecimal amountToBeTransfer, BaseStatus response) {
        // check if balance is available or not in from/depositor account
        if(getAccountMap().get(from).getCurrentBalance().compareTo(amountToBeTransfer) < 0){
            response.setStatus(Constants.RESPONSE_CODE_FAIL);
            response.setMessage("Depositor account do not have enough balance for transfer");
            return;
        }

        // amount transfer processing
        getAccountMap().get(to).addBalance(amountToBeTransfer);
        getAccountMap().get(from).deductBalance(amountToBeTransfer);
        response.setStatus(Constants.RESPONSE_CODE_SUCCESS);
    }
}
