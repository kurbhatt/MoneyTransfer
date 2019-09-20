package com.rvlt.server.data.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.rvlt.server.data.bean.Account;
import com.rvlt.server.data.bean.AccountResponse;
import com.rvlt.server.data.dao.AccountData;
import com.rvlt.server.data.bean.BaseStatus;
import com.rvlt.server.data.bean.TransferRequest;
import com.rvlt.server.util.AppEnums;
import com.rvlt.server.util.Constants;
import com.rvlt.server.util.Util;

public class TransferService implements Serializable {

    private static volatile TransferService instance;

    public static TransferService getInstance() {
        if (instance == null) {
            synchronized (TransferService.class) {
                if (instance == null) instance = new TransferService();
            }
        }
        return instance;
    }

    protected TransferService readResolve() {
        return getInstance();
    }

    public BaseStatus transferMoney(TransferRequest data) throws Exception {
        BaseStatus response = new BaseStatus();
        try{
            // null validation for master data
            if(data.getFrom() == null || data.getTo() == null || data.getAmount() == null){
                response.setStatus(Constants.RESPONSE_CODE_FAIL);
                response.setMessage("Required parameters missing");
                return response;
            }
            // check if accounts already exist else generate failure response
            this.validateAccount(data.getFrom(), AppEnums.AccountType.DEPOSITOR, response);
            if(!Objects.equals(response.getStatus(), Constants.RESPONSE_CODE_SUCCESS)){
                return response;
            }

            this.validateAccount(data.getTo(), AppEnums.AccountType.RECEIVER, response);
            if(!Objects.equals(response.getStatus(), Constants.RESPONSE_CODE_SUCCESS)){
                return response;
            }

            // call data store/db layer for amount transfer
            AccountData.transferMoney(data.getFrom(), data.getTo(), new BigDecimal(data.getAmount()).setScale(2, RoundingMode.CEILING), response);
            // check if any issue then return
            if(!Objects.equals(response.getStatus(), Constants.RESPONSE_CODE_SUCCESS)){
                return response;
            }
            // if all well then success response
            response.setStatus(Constants.RESPONSE_CODE_SUCCESS);
            response.setMessage(Constants.RESPONSE_MESSAGE_SUCCESS);
        }catch(Exception e){
            throw new Exception(e);
        }
        return response;
    }

    private void validateAccount(Integer id, AppEnums.AccountType accountType, BaseStatus response){
        if(!AccountData.getAccountMap().containsKey(id)){
            response.setStatus(Constants.RESPONSE_CODE_FAIL);
            if(accountType == null){
                response.setMessage("Account not found");
                return;
            }
            if(Objects.equals(accountType, AppEnums.AccountType.DEPOSITOR)){
                response.setMessage("Depositor account not found");
            }
            if(Objects.equals(accountType, AppEnums.AccountType.DEPOSITOR)){
                response.setMessage("Receiver account not found");
            }
            return;
        }
        response.setStatus(Constants.RESPONSE_CODE_SUCCESS);
    }

    public AccountResponse getAccountData(String id){
        AccountResponse response = new AccountResponse();
        if(Util.isNullOrEmpty(id)){
            response.setStatus(Constants.RESPONSE_CODE_FAIL);
            response.setMessage("Input parameter not valid");
            return response;
        }

        Integer accountId = Integer.valueOf(id);
        this.validateAccount(accountId, null, response);
        if(!Objects.equals(response.getStatus(), Constants.RESPONSE_CODE_SUCCESS)){
            return response;
        }

        Account account = AccountData.getAccountMap().get(accountId);
        response.setAccount(account);
        response.setStatus(Constants.RESPONSE_CODE_SUCCESS);
        response.setMessage(Constants.RESPONSE_MESSAGE_SUCCESS);
        return response;
    }
}
