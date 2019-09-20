package com.rvlt.server.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.rvlt.server.data.dao.AccountData;
import com.rvlt.server.data.bean.Account;

public class PreInitializeConfig {

    public static void initializeInMemoryAccounts(){
        int response = 0;
        Account a = new Account(1111, new BigDecimal(1000.56).setScale(2, RoundingMode.CEILING));
        response = AccountData.addNewAccount(a);
        validateResponse(response, a);
        a = new Account(2222, new BigDecimal(2000.78).setScale(2, RoundingMode.CEILING));
        response = AccountData.addNewAccount(a);
        validateResponse(response, a);
        a = new Account(3333, new BigDecimal(1500.98).setScale(2, RoundingMode.CEILING));
        response = AccountData.addNewAccount(a);
        validateResponse(response, a);
    }

    private static void validateResponse(int response, Account account){
        if(Objects.equals(response, 0)){
            System.out.println("Account already exist, account id:" + account.getAccountId());
        }
    }
}
