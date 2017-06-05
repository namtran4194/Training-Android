package com.namtran.lazada.presenter.showproduct.productdetail;

import android.content.Context;

import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.Product;

/**
 * Created by namtr on 5/18/2017.
 */

interface IProductDetail {
    void layChiTietSanPham(Action action, int maSP);

    void layDanhSachDanhGia(Action action, int maSP, int startIndex);

    void themGioHang(Context context, Product product);

    long soLuongSPCoTrongGioHang(Context context);
}
