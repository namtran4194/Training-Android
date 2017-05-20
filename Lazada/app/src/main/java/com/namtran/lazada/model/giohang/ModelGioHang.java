package com.namtran.lazada.model.giohang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.namtran.lazada.model.objectclass.SanPham;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 5/20/2017.
 */

public class ModelGioHang {
    private SQLiteDatabase database;

    public void moKetNoi(Context context) {
        SanPhamDataBase sanPhamDataBase = new SanPhamDataBase(context);
        database = sanPhamDataBase.getWritableDatabase();
    }

    public boolean themGioHang(SanPham sanPham) {
        ContentValues values = new ContentValues();
        values.put(SanPhamDataBase.GIOHANG_MASP, sanPham.getMaSP());
        values.put(SanPhamDataBase.GIOHANG_TENSP, sanPham.getTenSP());
        values.put(SanPhamDataBase.GIOHANG_GIA, sanPham.getGia());
        values.put(SanPhamDataBase.GIOHANG_HINHANH, sanPham.getHinhAnh());

        long id = database.insert(SanPhamDataBase.GIO_HANG_TABLE_NAME, null, values);
        return id > 0;
    }

    public List<SanPham> laySanPhamTrongGioHang() {
        List<SanPham> sanPhamList = new ArrayList<>();
        String sql = "SELECT * FROM " + SanPhamDataBase.GIO_HANG_TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int maSP = cursor.getInt(cursor.getColumnIndex(SanPhamDataBase.GIOHANG_MASP));
                String tenSP = cursor.getString(cursor.getColumnIndex(SanPhamDataBase.GIOHANG_TENSP));
                int gia = cursor.getInt(cursor.getColumnIndex(SanPhamDataBase.GIOHANG_GIA));
                byte[] hinhAnh = cursor.getBlob(cursor.getColumnIndex(SanPhamDataBase.GIOHANG_HINHANH));

                SanPham sanPham = new SanPham();
                sanPham.setMaSP(maSP);
                sanPham.setTenSP(tenSP);
                sanPham.setGia(gia);
                sanPham.setHinhAnh(hinhAnh);
                sanPhamList.add(sanPham);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sanPhamList;
    }
}
