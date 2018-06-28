package com.NaTicket.n.serviceclasses;

import com.NaTicket.n.common.activities.Reports_Flight_Ticket_Details;
import com.NaTicket.n.common.activities.Reports_Holidays_Details_Activity;
import com.NaTicket.n.common.activities.Reports_Hotels_Details_Activity;
import com.NaTicket.n.common.cancel_pacakge.Cancel_Ticket_Activity;
import com.NaTicket.n.common.fragments.Reports_holidays;
import com.NaTicket.n.common.fragments.Reports_Bus;
import com.NaTicket.n.common.fragments.Reports_Flights;
import com.NaTicket.n.common.fragments.Reports_Hotels;
import com.NaTicket.n.common.fragments.Reports_Recharges;
import com.NaTicket.n.flights.Flight_Ticket_Details;
import com.NaTicket.n.holidays.Holiday_Booking_Details_Activity;
import com.NaTicket.n.hotels.Hotel_Booking_Details_Activity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ankit on 14-01-2018.
 */

public class Service_Reports {

    static int mStatusCode;
    public static void getBusReports(final Reports_Bus activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postBusResponse(response);
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
        VolleySingleton.getInstance(activity.getActivity()).getRequestQueue().add(jsonObjRequest);
    }



    public static void getHolidayReports(final Reports_holidays activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postHolidayResponse(response);
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
        VolleySingleton.getInstance(activity.getActivity()).getRequestQueue().add(jsonObjRequest);
    }

    public static void getFlightsReports(final Reports_Flights activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postFlightsResponse(response);
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
        VolleySingleton.getInstance(activity.getActivity()).getRequestQueue().add(jsonObjRequest);
    }

    public static void getHotelReports(final Reports_Hotels activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postHotelsResponse(response);
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
        VolleySingleton.getInstance(activity.getActivity()).getRequestQueue().add(jsonObjRequest);
    }

    public static void getRechargeReports(final Reports_Recharges activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postHotelsResponse(response);
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
        VolleySingleton.getInstance(activity.getActivity()).getRequestQueue().add(jsonObjRequest);
    }



    public static void GetFlightTicket_Details(final Flight_Ticket_Details activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketDetailsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }
    public static void GetHotelDetails(final Hotel_Booking_Details_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void GetHotelDetails(final Reports_Hotels_Details_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }




    public static void GetFlightTicket_Details(final Reports_Flight_Ticket_Details activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketDetailsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void GetHolidayReportsDetails(final Reports_Holidays_Details_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketDetailsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void GetHolidayTicketDetails(final Holiday_Booking_Details_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getTicketDetailsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


}
