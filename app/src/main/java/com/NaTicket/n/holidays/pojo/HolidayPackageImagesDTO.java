package com.NaTicket.n.holidays.pojo;

import java.io.Serializable;

/**
 * Created by admin on 10-Jul-17.
 */

public class HolidayPackageImagesDTO implements Serializable {

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
