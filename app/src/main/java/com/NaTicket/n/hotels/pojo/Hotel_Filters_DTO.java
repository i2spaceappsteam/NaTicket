package com.NaTicket.n.hotels.pojo;

/**
 * Created by Nagarjuna on 12/26/2017.
 */

        import java.io.Serializable;

public class Hotel_Filters_DTO implements Serializable {

    private int Min_Rate;

    private int Max_Rate;

    private int one_star;

    private int two_star;

    private int three_star;

    private int four_star;

    private int five_star;


    public int getMin_Rate() {
        return Min_Rate;
    }

    public void setMin_Rate(int min_Rate) {
        this.Min_Rate = min_Rate;
    }

    public int getMax_Rate() {
        return Max_Rate;
    }

    public void setMax_Rate(int Max_Rate) {
        this.Max_Rate = Max_Rate;
    }

    public int getone_star() {
        return one_star;
    }

    public void setone_star(int one_star) {
        this.one_star = one_star;
    }

    public int gettwo_star() {
        return two_star;
    }

    public void settwo_star(int two_star) {
        this.two_star = two_star;}

    public int getthree_star() {
        return three_star;
    }

    public void setthree_star(int three_star) {
        this.three_star = three_star;
    }

    public int getfour_star() {
        return four_star;
    }

    public void setfour_star(int four_star) {
        this.four_star = four_star;
    }

    public int getfive_star() {
        return five_star;
    }

    public void setfive_star(int five_star) {
        this.five_star = five_star;
    }



}

