package com.NaTicket.n.recharges.Enums;

/**
 * Created by Administrator on 01-03-2016.
 */
public enum RechargeType {


    PrepaidDTH("PrepaidDTH",  1),PostpaidDTH("PostpaidDTH", 2), PrepaidDatacard("PrepaidDatacard",3),
    PostpaidDatacard("PostpaidDatacard", 4), PrepaidMobile("PrepaidMobile" , 5), PostpaidMobile("PostpaidMobile" , 6);
    private String stringValue;
    private int intValue;
    RechargeType(String typename, int typenum) {
        stringValue=typename;
        intValue=typenum;
    }


    public String typename() {
        return stringValue;
    }
    public int typenum() {
        return intValue;
    }


}
