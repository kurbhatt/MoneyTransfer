package com.rvlt.server.config;

import java.math.BigDecimal;
import java.math.MathContext;

import com.rvlt.server.data.AccountData;
import com.rvlt.server.data.bean.Account;

public class PreInitializeConfig {

    public static void initializeInMemoryAccounts(){
        try{
            Account a = new Account(1111, new BigDecimal(1000.56, MathContext.DECIMAL32));
            AccountData.addNewAccount(a);
            a = new Account(2222, new BigDecimal(2000.78, MathContext.DECIMAL32));
            AccountData.addNewAccount(a);
            a = new Account(3333, new BigDecimal(1500.98, MathContext.DECIMAL32));
            AccountData.addNewAccount(a);
            System.out.println(AccountData.getAccountMap().size());
        }catch (Exception e){
            System.out.println("Error while initializing pre filled data of accounts, error: "+ e.getMessage());
        }
    }
}
