package com.NaTicket.n.custom;

/**
 * Created by Ankit on 9/11/2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.NaTicket.n.R;

import static android.graphics.Typeface.BOLD;

/**
 * Created by Ankit on 8/17/2017.
 */

public class MyTextViewBold extends android.support.v7.widget.AppCompatTextView {
    public MyTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public MyTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public MyTextViewBold(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R
                    .styleable.MyTextView);
            String fontName = a.getString(R.styleable.MyTextView_fontName);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface, BOLD);
            }
            a.recycle();
        }
    }
}