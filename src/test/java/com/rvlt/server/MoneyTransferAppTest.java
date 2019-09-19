package com.rvlt.server;

import com.rvlt.server.data.bean.TransferRequest;
import com.rvlt.server.util.Constants;

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
        restAssured.port = 7000;
        MoneyTransferApp.run();
    }

    @AfterAll
    public void tearDown(){
        MoneyTransferApp.stop();
    }

    @DisplayName("transfer-success-case")
    @Test
    public void sampleTest(){
        TransferRequest data = new TransferRequest();
        data.setFrom(1111);
        data.setTo(2222);
        data.setAmount(111.11);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(data);

        Response response = request.post("/transfer");
        if(response != null){
            Assertions.assertEquals(response.getStatusCode(), 200);
            Assertions.assertEquals(response.jsonPath().get("status"), Constants.RESPONSE_CODE_SUCCESS);
            Assertions.assertEquals(response.jsonPath().get("message"), Constants.RESPONSE_MESSAGE_SUCCESS);
        }
    }
}
