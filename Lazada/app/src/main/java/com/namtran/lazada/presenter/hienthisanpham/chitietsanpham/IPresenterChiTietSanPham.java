package com.namtran.lazada.presenter.hienthisanpham.chitietsanpham;

import com.namtran.lazada.model.objectclass.Action;

/**
 * Created by namtr on 5/18/2017.
 */

interface IPresenterChiTietSanPham {
    void layChiTietSanPham(Action action, int maSP);

    void layDanhSachDanhGia(Action action, int maSP, int limit);
}
