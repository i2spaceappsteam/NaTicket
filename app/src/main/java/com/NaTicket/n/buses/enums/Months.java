package com.NaTicket.n.buses.enums;

/**
 * Created by Ankit on 8/17/2017.
 */

public enum Months {


    Jan("JAN",0), Feb("FEB",1), Mar("MAR",2), Apr("APR",3), May("MAY",4), Jun("JUNE",5),
    Jul("JULY",6), Aug("AUG",7), Sep("SEP",8), Oct("OCT",9), Nov("NOV",10), Dec("DEC",11);
    private String stringValue;
    private int intValue;
    Months(String month, int numaricmonth) {
        stringValue =month ;
        intValue = numaricmonth;

    }
    public String month() {
        return stringValue;
    }

    public int numaricmonth() {
        return intValue;
    }
}
