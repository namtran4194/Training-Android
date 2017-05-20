package com.namtran.lazada.model.giohang;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by namtr on 5/20/2017.
 */

public class SanPhamDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "SQLSANPHAM";
    private static final int DB_VERSION = 1;
    // giỏ hàng
    public static final String GIO_HANG_TABLE_NAME = "GioHang";
    public static final String GIOHANG_MASP = "MASP";
    public static final String GIOHANG_TENSP = "TENSP";
    public static final String GIOHANG_GIA = "GIA";
    public static final String GIOHANG_HINHANH = "HINHANH";

    // sản phẩm yêu thích
    public static final String YEU_THICH_TABLE_NAME = "YeuThich";
    public static final String YEU_THICH_MASP = "MASP";
    public static final String YEU_THICH_TENSP = "TENSP";
    public static final String YEU_THICH_GIA = "GIA";
    public static final String YEU_THICH_HINHANH = "HINHANH";

    public SanPhamDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlGioHang = "create table " + GIO_HANG_TABLE_NAME + " (" + GIOHANG_MASP + " integer primary key, "
                + GIOHANG_TENSP + " text, " + GIOHANG_GIA + " real, " + GIOHANG_HINHANH + " blob)";

        String sqlYeuThich = "create table " + YEU_THICH_TABLE_NAME + " (" + YEU_THICH_MASP + " integer primary key, "
                + YEU_THICH_TENSP + " text, " + YEU_THICH_GIA + " real, " + YEU_THICH_HINHANH + " blob)";

        db.execSQL(sqlGioHang);
        db.execSQL(sqlYeuThich);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
