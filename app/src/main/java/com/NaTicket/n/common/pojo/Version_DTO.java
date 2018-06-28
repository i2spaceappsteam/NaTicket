package com.NaTicket.n.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ankit on 05-05-2018.
 */

public class Version_DTO  implements Serializable {


    private ArrayList<VersionDetailsDTO> VersionDetails;

    private boolean IsSuccess;

    private String Message;

    private int StatusCode;

    public void setVersionDetails(ArrayList<VersionDetailsDTO> VersionDetails){
        this.VersionDetails = VersionDetails;
    }
    public ArrayList<VersionDetailsDTO> getVersionDetails(){
        return this.VersionDetails;
    }
    public void setIsSuccess(boolean IsSuccess){
        this.IsSuccess = IsSuccess;
    }
    public boolean getIsSuccess(){
        return this.IsSuccess;
    }
    public void setMessage(String Message){
        this.Message = Message;
    }
    public String getMessage(){
        return this.Message;
    }
    public void setStatusCode(int StatusCode){
        this.StatusCode = StatusCode;
    }
    public int getStatusCode(){
        return this.StatusCode;
    }

}
