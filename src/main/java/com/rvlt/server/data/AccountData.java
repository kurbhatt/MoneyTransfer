package com.rvlt.server.data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rvlt.server.data.bean.Account;

public class AccountData {
    private static volatile Map<Integer, Account> accountMap = new ConcurrentHashMap<>();

    public static Map<Integer, Account> getAccountMap() {
        synchronized (ConcurrentHashMap.class){
            return accountMap;
        }
    }

    public static void addNewAccount(Account account) throws Exception {
        if(getAccountMap().containsKey(account.getAccountId())){
            throw new Exception("Account already exist");
        }else{
            getAccountMap().put(account.getAccountId(), account);
        }
    }

    public static void transferMoney(Integer from, Integer to, BigDecimal amountToBeTransfer) throws Exception {
        if(!getAccountMap().containsKey(from)){
            throw new Exception("Depositor account not found");
        }
        if(!getAccountMap().containsKey(to)){
            throw new Exception("Receiver account not found");
        }
        getAccountMap().get(to).getCurrentBalance().add(amountToBeTransfer);
        getAccountMap().get(from).getCurrentBalance().subtract(amountToBeTransfer);
    }
}
