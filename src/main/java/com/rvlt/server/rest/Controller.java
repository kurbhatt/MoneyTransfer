package com.rvlt.server.rest;

import com.rvlt.server.data.service.TransferService;
import com.rvlt.server.data.bean.TransferRequest;

import org.eclipse.jetty.http.HttpStatus;

import io.javalin.http.Handler;

public class Controller {
    private static TransferService service = TransferService.getInstance();

    public static Handler transferAmount = ctx -> {
        TransferRequest data = ctx.bodyAsClass(TransferRequest.class);
        ctx.json(service.transferMoney(data));
        ctx.status(HttpStatus.OK_200);
    };

    public static Handler getAccountData = ctx -> {
        String id = ctx.pathParam("id");
        ctx.json(service.getAccountData(id));
        ctx.status(HttpStatus.OK_200);
    };
}
