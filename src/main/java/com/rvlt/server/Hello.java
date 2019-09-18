package com.rvlt.server;


import com.rvlt.server.config.PreInitializeConfig;
import com.rvlt.server.data.bean.BaseStatus;
import com.rvlt.server.data.bean.TransferRequest;
import com.rvlt.server.util.Constants;


import org.eclipse.jetty.http.HttpStatus;


import io.javalin.Javalin;

public class Hello {
    public static void main(String[] args) {
        TransferService s = new TransferService();
        Javalin app = Javalin.create(config -> {
            PreInitializeConfig.initializeInMemoryAccounts();
            config.requestLogger((ctx, ms)->{
                System.out.println(ms);
            });
        }).start(7000);
        app.exception(Exception.class, (e, ctx) ->{
            BaseStatus response = new BaseStatus();
            response.setStatus(Constants.RESPONSE_CODE_FAIL);
            response.setMessage(e.getMessage());
            ctx.json(response);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        });

        app.get("/", ctx -> {
            ctx.json(new Employee(10, "Keyur"));
            s.processRequest();
        });
        app.post("/transfer", ctx -> {
            TransferRequest data = ctx.bodyAsClass(TransferRequest.class);
            ctx.json(s.transferMoney(data));
            ctx.status(HttpStatus.OK_200);
        });
    }
}
