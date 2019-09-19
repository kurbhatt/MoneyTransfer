package com.rvlt.server;


import com.rvlt.server.config.PreInitializeConfig;
import com.rvlt.server.data.bean.BaseStatus;
import com.rvlt.server.data.bean.TransferRequest;
import com.rvlt.server.util.Constants;


import org.eclipse.jetty.http.HttpStatus;


import io.javalin.Javalin;

public class MoneyTransferApp {
    private static Javalin app;

    public static void main(String[] args) {
        run();
    }

    public static void run(){
        TransferService s = new TransferService();
        app = Javalin.create(config -> {
            // initialize account data
            PreInitializeConfig.initializeInMemoryAccounts();
            // request logger config
            config.requestLogger((ctx, ms)->{
                System.out.println(ms);
            });
        }).start(7000);
        // handle runtime exception
        app.exception(Exception.class, (e, ctx) ->{
            BaseStatus response = new BaseStatus();
            response.setStatus(Constants.RESPONSE_CODE_FAIL);
            response.setMessage(e.getMessage());
            ctx.json(response);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        });

        // simple get request for demonstration
        app.get("/", ctx -> {
            ctx.json(new Employee(10, "Keyur"));
            s.processRequest();
        });
        // money transfer post api
        app.post("/transfer", ctx -> {
            TransferRequest data = ctx.bodyAsClass(TransferRequest.class);
            ctx.json(s.transferMoney(data));
            ctx.status(HttpStatus.OK_200);
        });
    }

    public static void stop(){
        if(app != null){
            app.stop();
        }
    }
}
