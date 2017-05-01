package com.namtran.lazadaapp.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by namtr on 01/05/2017.
 */

public class PasswordEditText extends AppCompatEditText {
    private Drawable eye, eyeStrike;
    private boolean visible = false;

    public PasswordEditText(Context context) {
        super(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setup(AttributeSet attrs){

    }
}
