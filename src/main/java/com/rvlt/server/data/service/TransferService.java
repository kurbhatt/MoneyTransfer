package com.rvlt.server.data.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.rvlt.server.data.dao.AccountData;
import com.rvlt.server.data.bean.BaseStatus;
import com.rvlt.server.data.bean.TransferRequest;
import com.rvlt.server.util.Constants;

public class TransferService {

    public BaseStatus transferMoney(TransferRequest data) throws Exception {
        BaseStatus response = new BaseStatus();
        try{
            // null validation for master data
            if(data.getFrom() == null || data.getTo() == null || data.getAmount() == null){
                response.setStatus(Constants.RESPONSE_CODE_FAIL);
                response.setMessage("Required parameters missing");
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
}
