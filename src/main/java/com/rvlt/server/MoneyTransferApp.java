package com.rvlt.server;


import com.rvlt.server.config.PreInitializeConfig;
import com.rvlt.server.data.bean.BaseStatus;
import com.rvlt.server.rest.Controller;
import com.rvlt.server.util.Constants;


import org.eclipse.jetty.http.HttpStatus;


import io.javalin.Javalin;

public class MoneyTransferApp {
    private static Javalin app;

    public static void main(String[] args) {
        run();
    }

    public static void run(){
        app = Javalin.create(config -> {
            // initialize account data
            PreInitializeConfig.initializeInMemoryAccounts();
            // request logger config
            config.requestLogger((ctx, ms)->{
                System.out.println(ms);
            });
        }).start(Constants.APPLICATION_PORT);
        // handle runtime exception
        app.exception(Exception.class, (e, ctx) ->{
            BaseStatus response = new BaseStatus();
            response.setStatus(Constants.RESPONSE_CODE_FAIL);
            response.setMessage(e.getMessage());
            ctx.json(response);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        });

        // money transfer post api
        app.post("/transfer", Controller.transferAmount);
    }

    public static void stop(){
        if(app != null){
            app.stop();
        }
    }
}
