package com.NaTicket.n.custom;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by SHIVA SAI on 03-Jun-17.
 */

public class FontTypeface {

    private Context context;

    public FontTypeface(Context context){
        this.context = context;
    }

    public Typeface getTypefaceAndroid(){
        Typeface typeFace = Typeface.DEFAULT;
        String strFont = "assets/fonts/OpenSans_Regular.ttf";
        try {
            if (!strFont.equals("")){
                String strLeft = strFont.substring(0, 13);
                if (strLeft.equals("assets/fonts/")){
                    typeFace = Typeface.createFromAsset(context.getAssets(), strFont.replace("assets/", ""));
                } else {
                    typeFace = Typeface.createFromFile(strFont);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeFace;
    }
}
