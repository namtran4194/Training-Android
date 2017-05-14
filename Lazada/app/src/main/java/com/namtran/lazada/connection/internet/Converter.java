package com.namtran.lazada.connection.internet;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by namtr on 5/15/2017.
 */

public class Converter {

    public static int pixelToDp(Context context, float pixel) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (pixel / metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int DpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
