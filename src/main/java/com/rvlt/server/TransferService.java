package com.rvlt.server;

import java.math.BigDecimal;

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
            AccountData.transferMoney(data.getFrom(), data.getTo(), new BigDecimal(data.getAmount()));
            response.setStatus(1);
            response.setMessage("success");
        }catch(Exception e){
            throw new Exception(e);
        }
        return response;
    }
}
