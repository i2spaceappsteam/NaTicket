package com.NaTicket.n.hotels.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by Nagarjuna on 11/22/2017.
 */

public class Hotel_Details_Pager  extends FragmentStatePagerAdapter {

    public Hotel_Details_Pager(FragmentManager supportFragmentManager) {
       super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
    /*    switch (position) {
            case 0:
                return new Hotel_Details();
            case 1:
                return new Hotel_Amenities();
            case 2:
                return new Hotel_Map();

        }*/

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
