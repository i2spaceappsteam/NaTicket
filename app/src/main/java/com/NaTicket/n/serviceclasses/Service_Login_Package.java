package com.NaTicket.n.serviceclasses;

import com.NaTicket.n.common.BaseActivity;
import com.NaTicket.n.loginpackage.Forgot_Password_Activity;
import com.NaTicket.n.loginpackage.Otp_Waiting_Activity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.NaTicket.n.loginpackage.Change_Password_Activity;
import com.NaTicket.n.loginpackage.Login_Activity;
import com.NaTicket.n.loginpackage.Profile_Activity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.VolleySingleton;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nagarjuna on 1/12/2018.
 */

public class Service_Login_Package {

    public static void UserLOGIN(final Login_Activity activity, String url, final String requestBody) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postUserLOGINResponse(response);

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
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
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

    public static void getUserDetails(final Login_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getUserDetailsResponses(response);
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

    public static void getUserDetails(final Forgot_Password_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getUserDetailsResponses(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.errorresponse(error.getMessage());
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

    public static void getUserDetails(final BaseActivity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getAgentDetailsResponses(response);
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

    public static void getDecrpted_User_Id(final Login_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getDecrptedUserResponses(response);
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

    public static void getDecrpted_User_Id(final BaseActivity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getDecrptedUserResponses(response);
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
    public static void getUserDetails(final Profile_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getUserDetailsResponses(response);
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
    public static void getDecrpted_User_Id(final Profile_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getDecrptedUserResponses(response);
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


    public static void CHANGEPASSWORD(final Change_Password_Activity activity, String url, final String requestBody) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postchangepassresponse(response);

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
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
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

    public static void FORGOTPASSWORD(final Forgot_Password_Activity activity, String url, final String requestBody) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postforgotresponse(response);

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
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
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
    public static void FORGOTPASSWORD(final Otp_Waiting_Activity activity, String url, final String requestBody) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postforgotresponse(response);

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
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
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
