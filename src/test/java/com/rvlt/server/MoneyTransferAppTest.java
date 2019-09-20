package com.rvlt.server;

import com.rvlt.server.data.bean.TransferRequest;
import com.rvlt.server.util.Constants;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MoneyTransferAppTest {

    RestAssured restAssured = new RestAssured();

    @BeforeAll
    public void setUp(){
        restAssured.port = Constants.APPLICATION_PORT;
        MoneyTransferApp.run();
    }

    @AfterAll
    public void tearDown(){
        MoneyTransferApp.stop();
    }

    @DisplayName("transfer-success-case")
    @Test
    public void successCase(){
        TransferRequest data = new TransferRequest();
        data.setFrom(1111);
        data.setTo(2222);
        data.setAmount(111.11);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(data);

        Response response = request.post("/transfer");
        if(response != null){
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK_200);
            Assertions.assertEquals(response.jsonPath().get("status"), Constants.RESPONSE_CODE_SUCCESS);
            Assertions.assertEquals(response.jsonPath().get("message"), Constants.RESPONSE_MESSAGE_SUCCESS);
        }
    }

    @DisplayName("transfer-fail-case-null-data")
    @Test
    public void failedCaseNullData(){
        TransferRequest data = new TransferRequest();
        data.setFrom(1111);
        data.setAmount(111.11);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(data);

        Response response = request.post("/transfer");
        if(response != null){
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK_200);
            Assertions.assertEquals(response.jsonPath().get("status"), Constants.RESPONSE_CODE_FAIL);
            Assertions.assertEquals(response.jsonPath().get("message"), "Required parameters missing");
        }
    }

    @DisplayName("transfer-fail-case-depositor-not-found")
    @Test
    public void failedCaseDepositor(){
        TransferRequest data = new TransferRequest();
        data.setFrom(111);
        data.setTo(2222);
        data.setAmount(111.11);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(data);

        Response response = request.post("/transfer");
        if(response != null){
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK_200);
            Assertions.assertEquals(response.jsonPath().get("status"), Constants.RESPONSE_CODE_FAIL);
            Assertions.assertEquals(response.jsonPath().get("message"), "Depositor account not found");
        }
    }

    @DisplayName("transfer-fail-case-receiver-not-found")
    @Test
    public void failedCaseReceiver(){
        TransferRequest data = new TransferRequest();
        data.setFrom(1111);
        data.setTo(222);
        data.setAmount(111.11);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(data);

        Response response = request.post("/transfer");
        if(response != null){
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK_200);
            Assertions.assertEquals(response.jsonPath().get("status"), Constants.RESPONSE_CODE_FAIL);
            Assertions.assertEquals(response.jsonPath().get("message"), "Receiver account not found");
        }
    }

    @DisplayName("transfer-fail-case-balance-not-enough")
    @Test
    public void failedCaseBalanceNotEnough(){
        TransferRequest data = new TransferRequest();
        data.setFrom(1111);
        data.setTo(2222);
        data.setAmount(1111.11);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(data);

        Response response = request.post("/transfer");
        if(response != null){
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK_200);
            Assertions.assertEquals(response.jsonPath().get("status"), Constants.RESPONSE_CODE_FAIL);
            Assertions.assertEquals(response.jsonPath().get("message"), "Depositor account do not have enough balance for transfer");
        }
    }

    @DisplayName("account-data-success-case")
    @Test
    public void successCaseAccountData(){
        String accountId = "1111";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        Response response = request.get("/account/"+accountId);
        if(response != null){
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK_200);
            Assertions.assertEquals(response.jsonPath().get("status"), Constants.RESPONSE_CODE_SUCCESS);
            Assertions.assertEquals(response.jsonPath().get("message"), Constants.RESPONSE_MESSAGE_SUCCESS);
            Assertions.assertEquals(response.jsonPath().get("account-data.accountId"), Integer.valueOf(accountId));
        }
    }

    @DisplayName("account-data-fail-case-cast-exception")
    @Test
    public void failCaseCastException(){
        String accountId = "abcd";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        Response response = request.get("/account/"+accountId);
        if(response != null){
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR_500);
            Assertions.assertEquals(response.jsonPath().get("status"), Constants.RESPONSE_CODE_FAIL);
        }
    }

    @DisplayName("account-data-fail-case-wrond-account")
    @Test
    public void failCaseWrongAccount(){
        String accountId = "4444";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        Response response = request.get("/account/"+accountId);
        if(response != null){
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK_200);
            Assertions.assertEquals(response.jsonPath().get("status"), Constants.RESPONSE_CODE_FAIL);
            Assertions.assertEquals(response.jsonPath().get("message"), "Account not found");
        }
    }
}
