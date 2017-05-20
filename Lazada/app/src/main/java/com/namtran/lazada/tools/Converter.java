package com.namtran.lazada.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by namtr on 5/15/2017.
 */

public class Converter {

    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static byte[] drawableToByteArray(Drawable drawable){
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return bitmapToByteArray(bitmap);
    }

    public static String formatCurrency(int price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return format.format(price);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

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
