package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 8/18/2017.
 */

public class countriesListDTO implements Serializable {

    private int num_code;

    private String alpha_2_code;

    private String alpha_3_code;

    private String en_short_name;

    private String nationality;

    public int getNum_code() {
        return num_code;
    }

    public void setNum_code(int num_code) {
        this.num_code = num_code;
    }

    public String getAlpha_2_code() {
        return alpha_2_code;
    }

    public void setAlpha_2_code(String alpha_2_code) {
        this.alpha_2_code = alpha_2_code;
    }

    public String getAlpha_3_code() {
        return alpha_3_code;
    }

    public void setAlpha_3_code(String alpha_3_code) {
        this.alpha_3_code = alpha_3_code;
    }

    public String getEn_short_name() {
        return en_short_name;
    }

    public void setEn_short_name(String en_short_name) {
        this.en_short_name = en_short_name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

