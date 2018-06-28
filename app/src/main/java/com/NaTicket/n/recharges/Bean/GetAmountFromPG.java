package com.NaTicket.n.recharges.Bean;

import java.io.Serializable;

/**
 * Created by Ankit on 10-02-2018.
 */

public class GetAmountFromPG implements Serializable {
    public float getAmt() {
        return amt;
    }

    public void setAmt(float amt) {
        this.amt = amt;
    }

    private float amt;
}
