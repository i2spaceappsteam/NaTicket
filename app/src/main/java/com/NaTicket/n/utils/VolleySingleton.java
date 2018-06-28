package com.NaTicket.n.utils;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Ankit on 8/17/2017.
 */


public class VolleySingleton {


    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private JSONObject resObject = null;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);

    }

    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }



    public RetryPolicy getDefaultretryPolicy() {
        //RetryPolicy policy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RetryPolicy policy = new DefaultRetryPolicy(20 * 1000, -1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);


        return policy;

    }
}