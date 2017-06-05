package com.namtran.lazada.model.cart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by namtr on 5/20/2017.
 */

public class ProductDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "SQLSANPHAM";
    private static final int DB_VERSION = 1;
    // giỏ hàng
    public static final String GIO_HANG_TABLE_NAME = "GioHang";
    public static final String GIOHANG_PRODUCT_CODE = "MASP";
    public static final String GIOHANG_PRODUCT_NAME = "TENSP";
    public static final String GIOHANG_PRICE = "GIA";
    public static final String GIOHANG_IMAGE = "HINHANH";

    // sản phẩm yêu thích
    public static final String YEU_THICH_TABLE_NAME = "YeuThich";
    public static final String YEU_THICH_PRODUCT_CODE = "MASP";
    public static final String YEU_THICH_PRODUCT_NAME = "TENSP";
    public static final String YEU_THICH_PRICE = "GIA";
    public static final String YEU_THICH_IMAGE = "HINHANH";

    public ProductDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlGioHang = "create table " + GIO_HANG_TABLE_NAME + " (" + GIOHANG_PRODUCT_CODE + " integer primary key, "
                + GIOHANG_PRODUCT_NAME + " text, " + GIOHANG_PRICE + " real, " + GIOHANG_IMAGE + " blob)";

        String sqlYeuThich = "create table " + YEU_THICH_TABLE_NAME + " (" + YEU_THICH_PRODUCT_CODE + " integer primary key, "
                + YEU_THICH_PRODUCT_NAME + " text, " + YEU_THICH_PRICE + " real, " + YEU_THICH_IMAGE + " blob)";

        db.execSQL(sqlGioHang);
        db.execSQL(sqlYeuThich);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
