package com.NaTicket.n.hotels.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.NaTicket.n.R;
import com.NaTicket.n.hotels.Hotel_utils;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Hotel_utils hotel_utils;
    Float Lati,Longi;
    String HotelName;

    String TotalFare,CurrencySymbol,Imagepath;
    TextView toolbartitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        hotel_utils=new Hotel_utils(this);
        String Latitude=hotel_utils.getHotelDetails("latitude");
        String Longitude=hotel_utils.getHotelDetails("longitude");
        HotelName=hotel_utils.getHotelDetails("HotelName");
        TotalFare=hotel_utils.getHotelDetails("HotelPrice");
        CurrencySymbol=hotel_utils.getHotelDetails("CurrencySymbol");
        Imagepath=hotel_utils.getHotelDetails("HotelImage");

        if(Latitude.equals("")||Longitude.equals("")){

            Toast.makeText(this, "Location not availbale", Toast.LENGTH_SHORT).show();

            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
            ft.remove(mapFragment);
            ft.commit();
        }else{
            Lati= Float.valueOf(Latitude);
            Longi= Float.valueOf(Longitude);
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        toolbartitle.setText(""+HotelName+"");

        ImageView backBtn = (ImageView)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.layout_marker, null);

                TextView info= (TextView) v.findViewById(R.id.info);
                TextView hotelname= (TextView) v.findViewById(R.id.info1);


                hotelname.setText(""+HotelName+"");
                info.setText(""+CurrencySymbol+""+TotalFare);

                return v;
            }
        });
        int height = 180;
        int width = 200;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.blu_pointer);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);





        mMap.getUiSettings().setMapToolbarEnabled(false);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Lati,Longi);
        mMap.addMarker(new MarkerOptions().position(sydney).
                title(HotelName).
                snippet(""+CurrencySymbol+" "+TotalFare).
                icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(18).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        //googleMap.moveCamera(cameraUpdate);
        mMap.animateCamera(cameraUpdate);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }





/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.remove(mapFragment);
        ft.commit();
    }*/
}
