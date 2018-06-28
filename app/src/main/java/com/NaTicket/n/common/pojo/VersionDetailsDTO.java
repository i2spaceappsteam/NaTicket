package com.NaTicket.n.common.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 05-05-2018.
 */

public class VersionDetailsDTO implements Serializable {
    private int ApplicationID;

    private int ClientID;

    private String ApplicationName;

    private String AndroidVersion;

    private String IOSVersion;

    private String CreatedBy;

    private String CreatedDate;

    private String ModifiedBy;

    private String ModifiedDate;

    public void setApplicationID(int ApplicationID){
        this.ApplicationID = ApplicationID;
    }
    public int getApplicationID(){
        return this.ApplicationID;
    }
    public void setClientID(int ClientID){
        this.ClientID = ClientID;
    }
    public int getClientID(){
        return this.ClientID;
    }
    public void setApplicationName(String ApplicationName){
        this.ApplicationName = ApplicationName;
    }
    public String getApplicationName(){
        return this.ApplicationName;
    }
    public void setAndroidVersion(String AndroidVersion){
        this.AndroidVersion = AndroidVersion;
    }
    public String getAndroidVersion(){
        return this.AndroidVersion;
    }
    public void setIOSVersion(String IOSVersion){
        this.IOSVersion = IOSVersion;
    }
    public String getIOSVersion(){
        return this.IOSVersion;
    }
    public void setCreatedBy(String CreatedBy){
        this.CreatedBy = CreatedBy;
    }
    public String getCreatedBy(){
        return this.CreatedBy;
    }
    public void setCreatedDate(String CreatedDate){
        this.CreatedDate = CreatedDate;
    }
    public String getCreatedDate(){
        return this.CreatedDate;
    }
    public void setModifiedBy(String ModifiedBy){
        this.ModifiedBy = ModifiedBy;
    }
    public String getModifiedBy(){
        return this.ModifiedBy;
    }
    public void setModifiedDate(String ModifiedDate){
        this.ModifiedDate = ModifiedDate;
    }
    public String getModifiedDate(){
        return this.ModifiedDate;
    }

}