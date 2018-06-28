package com.NaTicket.n.common.pojo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ankit on 15-01-2018.
 */

public class Currency_Utils {

    Context ctx;
    public  Currency_Utils(Context context){
        this.ctx=context;
    }

    public boolean addCurrency(String Currency, String Currency_Value,String Currency_Symbol){
        SharedPreferences.Editor localEditor = ctx.getSharedPreferences("Currency", 0).edit();
        localEditor.putString("Currency", Currency);
        localEditor.putString("Currency_Value", Currency_Value);
        localEditor.putString("Currency_Symbol",Currency_Symbol);
        return localEditor.commit();
    }

    public String getCurrencyValue(String id) {
        SharedPreferences localSharedPreferences = ctx.getSharedPreferences("Currency", 0);
        return localSharedPreferences.getString(id, "");
    }
}
