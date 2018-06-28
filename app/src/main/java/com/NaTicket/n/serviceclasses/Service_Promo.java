package com.NaTicket.n.serviceclasses;

import com.NaTicket.n.common.Offers_Activity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ankit on 14-01-2018.
 */

public class Service_Promo {

    public static void getPromocodes(final Offers_Activity activity, String URL) {
        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getPromoCodesResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("ConsumerKey", Constants.ConsumerKey);
                headers.put("ConsumerSecret", Constants.ConsumerSecret);
                return headers;
            }


        };
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }
}
