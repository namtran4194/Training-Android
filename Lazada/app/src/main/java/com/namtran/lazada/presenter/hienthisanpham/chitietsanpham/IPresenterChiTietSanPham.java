package com.namtran.lazada.presenter.hienthisanpham.chitietsanpham;

import android.content.Context;

import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.SanPham;

/**
 * Created by namtr on 5/18/2017.
 */

interface IPresenterChiTietSanPham {
    void layChiTietSanPham(Action action, int maSP);

    void layDanhSachDanhGia(Action action, int maSP, int startIndex);

    void themGioHang(Context context, SanPham sanPham);
}
