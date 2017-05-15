package com.namtran.lazada.tools;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

import java.util.Arrays;

/**
 * Created by namtr on 5/15/2017.
 */

public class RippleMixer {

    public static Drawable getRippleColor(int color) {
        float[] outerRadii = new float[8];
        Arrays.fill(outerRadii, 3);
        RoundRectShape shape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    public static int lightenOrDarken(int color, double fraction) {
        if (canLighten(color, fraction))
            return lighten(color, fraction);
        return darken(color, fraction);
    }

    private static int lighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        red = lightenColor(red, fraction);
        green = lightenColor(green, fraction);
        blue = lightenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    private static int darken(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        red = darkenColor(red, fraction);
        green = darkenColor(green, fraction);
        blue = darkenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    private static boolean canLighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return canLightenComponent(red, fraction)
                && canLightenComponent(green, fraction)
                && canLightenComponent(blue, fraction);
    }

    private static boolean canLightenComponent(int colorComponent, double fraction) {
        int red = Color.red(colorComponent);
        int green = Color.green(colorComponent);
        int blue = Color.blue(colorComponent);
        return red + (red * fraction) < 255
                && green + (green * fraction) < 255
                && blue + (blue * fraction) < 255;
    }

    private static int darkenColor(int color, double fraction) {
        return (int) Math.max(color - (color * fraction), 0);
    }

    private static int lightenColor(int color, double fraction) {
        return (int) Math.min(color + (color * fraction), 255);
    }
}
