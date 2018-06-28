package com.NaTicket.n.hotels;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.File;

/**
 * Created by Ankit on 8/24/2017.
 */

public class Hotel_utils {

    Context ctx;

    public Hotel_utils(Context context) {
        this.ctx = context;
    }

    public  boolean addPaxDetails(String Adults, String Children, String rooms,String Adultscount,String childrencount,String TotalChildAges){
        SharedPreferences.Editor localEditor = ctx.getSharedPreferences("PaxDetails", 0).edit();
        localEditor.putString("Adults", Adults);
        localEditor.putString("Child", Children);
        localEditor.putString("Rooms",rooms);
        localEditor.putString("TotalADT",Adultscount);
        localEditor.putString("TotalCHD",childrencount);
        localEditor.putString("ChildAges",TotalChildAges);
        return localEditor.commit();
    }

    public String getPaxDetails(String id) {
        SharedPreferences localSharedPreferences = ctx.getSharedPreferences("PaxDetails", 0);
        return localSharedPreferences.getString(id, "");
    }

    public boolean PaxDetails() {
        Boolean bool = false;
        File data = new File(ctx.getFilesDir().getPath());
        if (data.exists()) {
            SharedPreferences localSharedPreferences = ctx.getSharedPreferences("PaxDetails", 0);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            bool = prefs.edit().clear().commit() && localSharedPreferences.edit().clear().commit() && data.delete();
        }
        return bool;
    }

    public  boolean addHotelDetails(String HotelName, String Description, String HotelAddress,String Amenities,
                                    String latitude,String longitude,String Hotelprice,String CurrencySymbol){
        SharedPreferences.Editor localEditor = ctx.getSharedPreferences("HotelDetails", 0).edit();
        localEditor.putString("HotelName", HotelName);
        localEditor.putString("Description", Description);
        localEditor.putString("HotelAddress",HotelAddress);
        localEditor.putString("Amenities",Amenities);
        localEditor.putString("latitude",latitude);
        localEditor.putString("longitude",longitude);
        localEditor.putString("HotelPrice",Hotelprice);
        localEditor.putString("CurrencySymbol",CurrencySymbol);
        return localEditor.commit();
    }

    public String getHotelDetails(String id) {
        SharedPreferences localSharedPreferences = ctx.getSharedPreferences("HotelDetails", 0);
        return localSharedPreferences.getString(id, "");
    }

}
