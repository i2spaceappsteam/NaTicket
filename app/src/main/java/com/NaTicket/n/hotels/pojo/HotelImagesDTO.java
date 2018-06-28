package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;

/**
 * Created by admin on 18-Jun-17.
 */

public class HotelImagesDTO implements Serializable{

    private String Imagedesc;

    private String Imagepath;

    public String getImagedesc() {
        return Imagedesc;
    }

    public void setImagedesc(String imagedesc) {
        Imagedesc = imagedesc;
    }

    public String getImagepath() {
        return Imagepath;
    }

    public void setImagepath(String imagepath) {
        Imagepath = imagepath;
    }
}
