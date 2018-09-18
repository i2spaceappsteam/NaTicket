package com.NaTicket.n.serviceclasses;


import com.NaTicket.n.buses.SearchCityBus;
import com.NaTicket.n.common.MainActivity;
import com.NaTicket.n.common.Splash_Activity;
import com.NaTicket.n.common.drawer_items.FeedBackActivity;
import com.NaTicket.n.flights.Domestic_Flight_Review;
import com.NaTicket.n.flights.Flight_Booking_Activity;
import com.NaTicket.n.flights.Flight_Passenger_Information;
import com.NaTicket.n.flights.Flight_Ticket_Details;
import com.NaTicket.n.flights.Flights_City_Search_Activity;
import com.NaTicket.n.flights.Flights_Domestic_Onward;
import com.NaTicket.n.flights.Flights_Domestic_Return;
import com.NaTicket.n.flights.Flights_International_Onward;
import com.NaTicket.n.flights.Flights_International_Return;
import com.NaTicket.n.flights.International_Flight_Review;
import com.NaTicket.n.holidays.AvailableHolidaysActivity;
import com.NaTicket.n.holidays.HolidaySearchActivity;
import com.NaTicket.n.holidays.Holiday_Booking_Activity;
import com.NaTicket.n.holidays.ReviewHolidayActivity;
import com.NaTicket.n.hotels.AvailableHotelsActivity;
import com.NaTicket.n.hotels.CitySearchActivity;
import com.NaTicket.n.hotels.HotelDetailActivity;
import com.NaTicket.n.hotels.ReviewActivity;
import com.NaTicket.n.loginpackage.Forgot_Password_Activity;
import com.NaTicket.n.loginpackage.Otp_Waiting_Activity;
import com.NaTicket.n.loginpackage.Profile_Activity;
import com.NaTicket.n.loginpackage.SignUp_Activity;
import com.NaTicket.n.paymnet_gate_ways.Payment_Gateway_Main;
import com.NaTicket.n.recharges.Operator_list_activity_rec;
import com.NaTicket.n.recharges.RechargeResponseActivity;
import com.NaTicket.n.recharges.Recharge_MainActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nagarjuna on 8/17/2017.
 */

public class ServiceClasses {
    public static void getClientDetails(final Splash_Activity activity, String URL) {
        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getClientDetailsResponse(response);
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


    public static void getClientDetails(final Payment_Gateway_Main activity, String URL) {
        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getClientDetailsResponse(response);
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

    public static void getParentlist(final Splash_Activity activity, String URL) {
        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getParentlistResponse(response);
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


    public static void postUserRegistration(final Profile_Activity activity, String URL, final String requestBody) {
        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.postUserRegistrationResponse(response);
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

    public static void postUserRegistration(final Otp_Waiting_Activity activity, String URL, final String requestBody) {

        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.postUserRegistrationResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void DYNAMICCONTENT(final MainActivity activity, String URL) {
        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getDynamicresponse(response);
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


    public static void SendFeedBack(final FeedBackActivity activity, String URL, final String requestBody) {
        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.postFeedBackResponse(response);
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


    public static void getPromocodes(final Flight_Passenger_Information activity, String URL) {
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

    public static void getPromocodes(final ReviewHolidayActivity activity, String URL) {
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

    public static void getHotels(final AvailableHotelsActivity activity, String url) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {

                            activity.getHotelsResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.errorreponse(error.toString());
                error.printStackTrace();
            }
        }) {

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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void getHotelDeatils(final HotelDetailActivity activity, String url) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getHotelDetailsResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.errorreponse(error.toString());
                error.printStackTrace();
            }
        }) {

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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void getBusesCity(final SearchCityBus activity, String url) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {

                            activity.getBusesResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.errorreponse(error.toString());
                error.printStackTrace();
            }
        }) {

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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void postBlockHotel(final ReviewActivity activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            try {
                                activity.postBlockhotelResponse(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.errorreponse(error.toString());
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void getPromocodes(final ReviewActivity activity, String URL) {
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

    public static void getPromocodes(final Recharge_MainActivity activity, String URL) {
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


    public static void getFlightCities(final Flights_City_Search_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getFlightCities(response);
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

    public static void getAvaliableFlights(final Flights_Domestic_Onward activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getFlightsResponse(response);
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
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void getAvaliableFlights(final Flights_International_Return activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getFlightsResponse(response);
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
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void getAvaliableFlights(final Flights_International_Onward activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getFlightsResponse(response);
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
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void getTaxDetails(final Flights_Domestic_Onward activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postTaxDetailsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void getTaxDetails(final Flights_Domestic_Return activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postTaxDetailsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void getTaxDetails(final Flights_International_Onward activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postTaxDetailsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void getTaxDetails(final Flights_International_Return activity, String url, final String requestBody) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postTaxDetailsResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void getFareRules(final Domestic_Flight_Review activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getFareRulesResponse(response);
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
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void getFareRules(final International_Flight_Review activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getFareRulesResponse(response);
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
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void postBlockFlight(final Flight_Passenger_Information activity, String url, final String requestBody) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            activity.postBlockFlightResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                activity.errorResponse(error.getMessage());
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void BookFlight(final Flight_Booking_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getBookingResponse(response);
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


/*    public static void GetFlightTicket_Details(final Reports_Flight_Ticket_Details activity, String URL) {
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
    }*/


    public static void getOperatorsrecharge(final Operator_list_activity_rec activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getCategoriesResponse(response);
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


    public static void Recharge(final RechargeResponseActivity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                activity.getBookresponse(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

    public static void RechargeStatus(final RechargeResponseActivity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                activity.getStausresponse(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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


    public static void getConvenienceFee(final Recharge_MainActivity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                activity.getStausresponse(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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


    public static void getHolidays(final AvailableHolidaysActivity activity, String url) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getHolidaysResponse(response);
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }

    public static void getCategoriesDeails(final HolidaySearchActivity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getCategoriesResponse(response);
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


    public static void getDestinationDetails(final HolidaySearchActivity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getDestinationResponse(response);
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

    public static void bookHoliday(final Holiday_Booking_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                activity.getBookresponse(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

    public static void postBlockHoliday(final ReviewHolidayActivity activity, String url, final String requestBody) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            //Util.showMessage(activity,response);
                            try {
                                activity.postBlockhotelResponse(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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


    public static void getHotelCities(final CitySearchActivity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getHotelCitiesResponse(response);
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

    public static void GETVERSIONCODE(final Splash_Activity activity, String URL) {
        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getversioncoderesponse(response);
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
                headers.put("ConsumerKey", "AEBB856E87A0B01562197ABA066BF28C8E9E6C6C");
                headers.put("ConsumerSecret", "04DAF1E2C80A758DCC6BCEBE436D25BC");
                return headers;
            }
        };
        VolleySingleton.getInstance(activity).getRequestQueue().add(jsonObjRequest);
    }


    public static void GETVERIFYUSER(final SignUp_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getVerifyUserResponse(response);
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


    public static void GETOTP(final SignUp_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getOTPResponse(response);
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

    public static void GETOTP(final Forgot_Password_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getOTPResponse(response);
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


    public static void GETOTP(final Otp_Waiting_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getOTPResponse(response);
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


    public static void GEVERIFYTOTP(final Otp_Waiting_Activity activity, String URL) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getVERIFYOTPResponse(response);
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

    public static void getDailingcodes(final Splash_Activity activity, String URL) {
        String url = URL;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            activity.getdialingcodesresponse(response);
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


}
