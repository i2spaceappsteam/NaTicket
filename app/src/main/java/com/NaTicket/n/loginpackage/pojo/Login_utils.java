package com.NaTicket.n.loginpackage.pojo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.NaTicket.n.utils.Constants;

import java.io.File;

/**
 * Created by Ankit on 09-01-2018.
 */

public class Login_utils {


    Context ctx;
    public  Login_utils(Context context){
        this.ctx=context;
    }

    public boolean setUserDetails(String UserName, String Email,String Password,String Mobile,String DOB,String Gender,String UserId,
                                  String Deprct_User_Id,String UserType,String Response,String Address,String Balance){
        SharedPreferences.Editor localEditor = ctx.getSharedPreferences("UserDetails", 0).edit();
        localEditor.putString(Constants.USERNAME, UserName);
        localEditor.putString(Constants.USEREMAIL, Email);
        localEditor.putString(Constants.USERPASSWORD,Password);
        localEditor.putString(Constants.USERPHONE,Mobile);
        localEditor.putString(Constants.USERDOB,DOB);
        localEditor.putString(Constants.USERGENDER,Gender);
        localEditor.putString(Constants.USERID,UserId);
        localEditor.putString(Constants.DEPRCYT_USER_ID,Deprct_User_Id);
        localEditor.putString(Constants.USERTYPE,UserType);
        localEditor.putString(Constants.RESPONSE,Response);
        localEditor.putString(Constants.USERADDRESS,Address);
        localEditor.putString(Constants.BALANCE,Balance);
        return localEditor.commit();
    }

    public String getUserDetails(String id) {
        SharedPreferences localSharedPreferences = ctx.getSharedPreferences("UserDetails", 0);
        return localSharedPreferences.getString(id, "");
    }


    public boolean ClearUserDetails() {
        Boolean bool = false;
        File data = new File(ctx.getFilesDir().getPath());
        if (data.exists()) {
            SharedPreferences localSharedPreferences = ctx.getSharedPreferences("UserDetails", 0);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            bool = prefs.edit().clear().commit() && localSharedPreferences.edit().clear().commit() && data.delete();
        }
        return bool;
    }

    public boolean setClientDetails(String Clientid,String Parentid,String ClientResponse){
        SharedPreferences.Editor localEditor = ctx.getSharedPreferences("ClientDetails", 0).edit();
        localEditor.putString(Constants.CLIENTID, Clientid);
        localEditor.putString(Constants.PARENTID, Parentid);
        localEditor.putString(Constants.CRESPONSE, ClientResponse);

        return localEditor.commit();
    }

    public String getClientDetails(String id) {
        SharedPreferences localSharedPreferences = ctx.getSharedPreferences("ClientDetails", 0);
        return localSharedPreferences.getString(id, "");
    }


    public boolean setDynamicData(String Content){
        SharedPreferences.Editor localEditor = ctx.getSharedPreferences("DynamicData", 0).edit();
        localEditor.putString(Constants.CONTENT, Content);
        return localEditor.commit();
    }

    public String getDynamicData(String id) {
        SharedPreferences localSharedPreferences = ctx.getSharedPreferences("DynamicData", 0);
        return localSharedPreferences.getString(id, "");
    }


    public void setDailindcodesresponse(String Content){
        SharedPreferences.Editor localEditor = ctx.getSharedPreferences("DailingCodes", 0).edit();
        localEditor.putString(Constants.DAILING_CODES, Content);
        localEditor.apply();
    }

    public String getDailindcodesresponse(String id) {
        SharedPreferences localSharedPreferences = ctx.getSharedPreferences("DailingCodes", 0);
        return localSharedPreferences.getString(id, "");
    }


}
