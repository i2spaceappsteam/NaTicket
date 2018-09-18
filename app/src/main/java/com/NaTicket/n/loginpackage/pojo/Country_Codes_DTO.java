package com.NaTicket.n.loginpackage.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 22-08-2018.
 */

public class Country_Codes_DTO implements Serializable {

    private String Name;

    private String Value;

    private String Title;

    private String Code;

    public void setName(String Name){
        this.Name = Name;
    }

    public String getName(){
        return this.Name;
    }
    public void setValue(String Value){
        this.Value = Value;
    }
    public String getValue(){
        return this.Value;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
