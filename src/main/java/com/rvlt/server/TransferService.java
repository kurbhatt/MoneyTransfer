package com.rvlt.server;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.rvlt.server.data.AccountData;
import com.rvlt.server.data.bean.BaseStatus;
import com.rvlt.server.data.bean.TransferRequest;
import com.rvlt.server.util.Constants;

public class TransferService {

    public void processRequest(){
        System.out.println("TransferService.processRequest()");
    }

    public BaseStatus transferMoney(TransferRequest data) throws Exception {
        BaseStatus response = new BaseStatus();
        try{
            if(data.getFrom() == null || data.getTo() == null || data.getAmount() == null){
                response.setStatus(Constants.RESPONSE_CODE_FAIL);
                response.setMessage(Constants.RESPONSE_MESSAGE_FAIL);
            }
            AccountData.transferMoney(data.getFrom(), data.getTo(), new BigDecimal(data.getAmount()).setScale(2, RoundingMode.CEILING), response);
            if(!Objects.equals(response.getStatus(), Constants.RESPONSE_CODE_SUCCESS)){
                return response;
            }
            response.setStatus(Constants.RESPONSE_CODE_SUCCESS);
            response.setMessage(Constants.RESPONSE_MESSAGE_SUCCESS);
        }catch(Exception e){
            throw new Exception(e);
        }
        return response;
    }
}
