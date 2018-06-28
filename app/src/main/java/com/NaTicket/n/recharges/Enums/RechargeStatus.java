package com.NaTicket.n.recharges.Enums;

/**
 * Created by Administrator on 09-03-2016.
 */
public enum RechargeStatus {

       PleaseSelect("PleaseSelect",0) ,Success("Success",  1),Failed("Failed", 2), Pending("Pending" , 3),Initiated("Initiated",4);

        private String stringValue;
        private int intValue;
        RechargeStatus(String rechargeStatus, int i) {
            stringValue = rechargeStatus;
            intValue = i;

        }
        public String rechargeStatus() {
            return stringValue;
        }

    public int rechargenum() {
        return intValue;
    }

}
