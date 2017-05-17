package com.namtran.lazada.customview;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;

import com.namtran.lazada.tools.RippleMixer;

/**
 * Created by namtr on 5/17/2017.
 */

public class ButtonRippleDrawable {
    private int color;
    private double fraction;

    public ButtonRippleDrawable(int color, double fraction) {
        this.color = color;
        this.fraction = fraction;
    }

    public Drawable getRipple() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ColorDrawable defaultColor = new ColorDrawable(color);
            Drawable rippleColor = RippleMixer.getRippleColor(color);
            ColorStateList pressedColor = ColorStateList.valueOf(RippleMixer.lightenOrDarken(color, fraction));
            return new RippleDrawable(pressedColor, defaultColor, rippleColor);
        } else {
            ColorDrawable colorDrawable = new ColorDrawable(RippleMixer.lightenOrDarken(color, fraction));
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.setEnterFadeDuration(200);
            stateListDrawable.setExitFadeDuration(200);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, colorDrawable);
            return stateListDrawable;
        }
    }
}
