package com.NaTicket.n.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.NaTicket.n.R;

/**
 * Created by Nagarjuna on 8/18/2017.
 */

public class BackActivity extends AppCompatActivity {

    public void inittoolbar(){
        ImageView backBtn = (ImageView)findViewById(R.id.backBtn);
        ImageView toolbarlogo = (ImageView)findViewById(R.id.toolbarlogo);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.startHomeActivity(getApplicationContext());
            }
        });
    }
}