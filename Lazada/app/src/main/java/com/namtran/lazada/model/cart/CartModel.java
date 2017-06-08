package com.namtran.lazada.model.cart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.namtran.lazada.model.objectclass.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 5/20/2017.
 */

public class CartModel {
    private SQLiteDatabase database;

    public void openConnection(Context context) {
        ProductDatabase productDatabase = new ProductDatabase(context);
        database = productDatabase.getWritableDatabase();
    }

    public boolean add(final Product product) {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                ContentValues values = new ContentValues();
                values.put(ProductDatabase.GIOHANG_PRODUCT_CODE, product.getProductCode());
                values.put(ProductDatabase.GIOHANG_PRODUCT_NAME, product.getProductName());
                values.put(ProductDatabase.GIOHANG_PRICE, product.getPrice());
                values.put(ProductDatabase.GIOHANG_IMAGE, product.getImage());

                long id = database.insert(ProductDatabase.GIO_HANG_TABLE_NAME, null, values);
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

    public List<Product> getAll() {
        AsyncTask<Void, Void, List<Product>> task = new AsyncTask<Void, Void, List<Product>>() {
            List<Product> sanPhamList = new ArrayList<>();

            @Override
            protected List<Product> doInBackground(Void... params) {
                String sql = "SELECT * FROM " + ProductDatabase.GIO_HANG_TABLE_NAME;
                Cursor cursor = database.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    do {
                        int maSP = cursor.getInt(cursor.getColumnIndex(ProductDatabase.GIOHANG_PRODUCT_CODE));
                        String tenSP = cursor.getString(cursor.getColumnIndex(ProductDatabase.GIOHANG_PRODUCT_NAME));
                        int gia = cursor.getInt(cursor.getColumnIndex(ProductDatabase.GIOHANG_PRICE));
                        byte[] hinhAnh = cursor.getBlob(cursor.getColumnIndex(ProductDatabase.GIOHANG_IMAGE));

                        Product product = new Product();
                        product.setProductCode(maSP);
                        product.setProductName(tenSP);
                        product.setPrice(gia);
                        product.setImage(hinhAnh);
                        sanPhamList.add(product);
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

    public long size() {
        return DatabaseUtils.queryNumEntries(database, ProductDatabase.GIO_HANG_TABLE_NAME);
    }
}
