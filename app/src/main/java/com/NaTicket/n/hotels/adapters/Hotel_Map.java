package com.NaTicket.n.hotels.adapters;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.NaTicket.n.R;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;



/**
 * Created by Nagarjuna on 11/22/2017.
 */

public  class Hotel_Map extends Fragment{

    public Hotel_Map(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.hotel_booking,container,false);
        Intent ip=new Intent(getActivity(),MapsActivity.class);
        getActivity().startActivity(ip);
        return view;
    }
    public void isConnected() {
        if (Util.isNetworkAvailable(getActivity())) {

        } else {
            Util.showMessage(getActivity(), Constants.NO_INT_MSG);
        }
    }


/*    public void onDestroyView()
    {
        super.onDestroyView();
        Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }*/

}


