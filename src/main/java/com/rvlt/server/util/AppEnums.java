package com.rvlt.server.util;

public class AppEnums {

    public enum AccountType {
        DEPOSITOR("Depositor"), RECEIVER("Receiver");
        private String type;

        AccountType(String type){
            this.type = type;
        }
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
