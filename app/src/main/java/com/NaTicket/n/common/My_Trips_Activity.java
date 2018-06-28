package com.NaTicket.n.common;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.fragments.Reports_holidays;
import com.NaTicket.n.common.fragments.Reports_Bus;
import com.NaTicket.n.common.fragments.Reports_Flights;
import com.NaTicket.n.common.fragments.Reports_Hotels;
import com.NaTicket.n.common.fragments.Reports_Recharges;
import com.NaTicket.n.custom.BottomNavigationViewHelper;
import com.NaTicket.n.custom.CustomTypefaceSpan;
import com.NaTicket.n.custom.FontTypeface;
import com.NaTicket.n.utils.BackActivity;

/**
 * Created by Ankit on 14-01-2018.
 */

public class My_Trips_Activity extends BackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        inittoolbar();
        TextView toolbar_title= (TextView) findViewById(R.id.toolbartitle);
        toolbar_title.setText(""+"Reports"+"");
        setupNavigationView();
    }

    private void setupNavigationView() {
       BottomNavigationView bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.inflateMenu(R.menu.bottom_nav_items);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        changeTypeface(bottomNavigationView);

        if (bottomNavigationView!=null){
            Menu menu=bottomNavigationView.getMenu();
            selectFragment(menu.getItem(0));

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectFragment(item);
                    return false;
                }
            });
        }
    }

    private void selectFragment(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.action_bus:
                pushFragment(new Reports_Bus());
                break;
            case R.id.action_flight:
                pushFragment(new Reports_Flights());
                break;
            case R.id.action_hotel:
                pushFragment(new Reports_Hotels());
                break;
            case R.id.action_recharges:
                pushFragment(new Reports_Recharges());
                break;
            case R.id.action_bills:
                pushFragment(new Reports_holidays());
                break;
        }
    }


    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.content, fragment);
                ft.commit();
            }
        }
    }

    private void changeTypeface(BottomNavigationView navigationView){
        FontTypeface fontTypeface = new FontTypeface(this);
        Typeface typeface = fontTypeface.getTypefaceAndroid();

        MenuItem item;

        item = navigationView.getMenu().findItem(R.id.action_bus);
        applyFontToItem(item, typeface);
        item = navigationView.getMenu().findItem(R.id.action_flight);
        applyFontToItem(item, typeface);
        item = navigationView.getMenu().findItem(R.id.action_hotel);
        applyFontToItem(item, typeface);
        item = navigationView.getMenu().findItem(R.id.action_recharges);
        applyFontToItem(item, typeface);
        item = navigationView.getMenu().findItem(R.id.action_bills);
        applyFontToItem(item, typeface);

    }

    private void applyFontToItem(MenuItem item, Typeface font) {
        SpannableString mNewTitle = new SpannableString(item.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font, 8), 0 ,
                mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        item.setTitle(mNewTitle);
    }
}

