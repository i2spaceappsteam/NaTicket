package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 8/19/2017.
 */


public class ImagesDTO implements Serializable {

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
