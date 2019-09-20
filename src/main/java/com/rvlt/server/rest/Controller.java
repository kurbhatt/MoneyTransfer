package com.rvlt.server.rest;

import com.rvlt.server.data.service.TransferService;
import com.rvlt.server.data.bean.TransferRequest;

import org.eclipse.jetty.http.HttpStatus;


import io.javalin.http.Handler;

public class Controller {
    private static TransferService s = new TransferService();

    public static Handler transferAmount = ctx -> {
        TransferRequest data = ctx.bodyAsClass(TransferRequest.class);
            ctx.json(s.transferMoney(data));
            ctx.status(HttpStatus.OK_200);
    };
}
