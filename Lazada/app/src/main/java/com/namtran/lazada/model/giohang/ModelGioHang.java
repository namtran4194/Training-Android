package com.namtran.lazada.model.giohang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.namtran.lazada.model.objectclass.SanPham;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 5/20/2017.
 */

public class ModelGioHang {
    private SQLiteDatabase database;

    public void moKetNoi(Context context) {
        SanPhamDataBase sanPhamDataBase = new SanPhamDataBase(context);
        database = sanPhamDataBase.getWritableDatabase();
    }

    public boolean themGioHang(final SanPham sanPham) {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                ContentValues values = new ContentValues();
                values.put(SanPhamDataBase.GIOHANG_MASP, sanPham.getMaSP());
                values.put(SanPhamDataBase.GIOHANG_TENSP, sanPham.getTenSP());
                values.put(SanPhamDataBase.GIOHANG_GIA, sanPham.getGia());
                values.put(SanPhamDataBase.GIOHANG_HINHANH, sanPham.getHinhAnh());

                long id = database.insert(SanPhamDataBase.GIO_HANG_TABLE_NAME, null, values);
                return id > 0;
            }
        };
        task.execute();
        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SanPham> laySanPhamTrongGioHang() {
        AsyncTask<Void, Void, List<SanPham>> task = new AsyncTask<Void, Void, List<SanPham>>() {
            List<SanPham> sanPhamList = new ArrayList<>();

            @Override
            protected List<SanPham> doInBackground(Void... params) {
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
        };

        task.execute();
        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getRowsCount() {
        return DatabaseUtils.queryNumEntries(database, SanPhamDataBase.GIO_HANG_TABLE_NAME);
    }
}
