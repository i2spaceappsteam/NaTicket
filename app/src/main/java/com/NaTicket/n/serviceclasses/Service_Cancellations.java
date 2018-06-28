package com.NaTicket.n.serviceclasses;

import com.NaTicket.n.common.activities.Reports_Holidays_Details_Activity;
import com.NaTicket.n.common.activities.Reports_Hotels_Details_Activity;
import com.NaTicket.n.common.cancel_pacakge.Cancel_Flight_Ticket;
import com.NaTicket.n.common.cancel_pacakge.Cancel_Ticket_Activity;
import com.NaTicket.n.utils.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.NaTicket.n.common.activities.Reports_Bus_Ticket_Details;
import com.NaTicket.n.common.activities.Reports_Flight_Ticket_Details;
import com.NaTicket.n.utils.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ankit on 16-01-2018.
 */

public class Service_Cancellations {
    private static int mStatusCode=0;
    public static void GetTicketDetails(final Cancel_Ticket_Activity activity, String URL) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketsResponse(response, mStatusCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.getTicketsResponse(null, mStatusCode);

                error.printStackTrace();
            }


        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
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

        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void GetTicketDetailsCan(final Reports_Bus_Ticket_Details activity, String URL) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketsResponse(response,mStatusCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.getTicketsResponse(null, mStatusCode);
                error.printStackTrace();
            }


        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void GetCancelFlightTicket_Details(final Cancel_Ticket_Activity activity, String URL) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketDetailsResponse(response,mStatusCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.getTicketsResponse(null, mStatusCode);
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void GetCancelHotelTicket_Details(final Cancel_Ticket_Activity activity, String URL) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getHotelTicketDetailsResponse(response,mStatusCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.getHotelTicketDetailsResponse(null, mStatusCode);
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void GetCancelHotelTicket_DetailsCan(final Reports_Hotels_Details_Activity activity, String URL) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getHotelTicketDetailsResponse(response,mStatusCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.getHotelTicketDetailsResponse(null, mStatusCode);
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void GetCancelFlightTicket_DetailsCan(final Reports_Flight_Ticket_Details activity, String URL) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketDetailsResponseCan(response,mStatusCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.getTicketDetailsResponseCan(null, mStatusCode);
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }



    public static void callGetHolidayTicketDetails(final Reports_Holidays_Details_Activity activity, String URL) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.GetHolidayTicketDetailsResponse(response,mStatusCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.GetHolidayTicketDetailsResponse(null, mStatusCode);
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void GetHolidayTicketDetails(final Cancel_Ticket_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getHolTicketDetailsResponse(response,mStatusCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.getHolTicketDetailsResponse(null, mStatusCode);
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void GetCancelTicketDetails(final Cancel_Flight_Ticket activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getCancelDetailsResponse(response,mStatusCode);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.getCancelDetailsResponse(null, mStatusCode);
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

}
