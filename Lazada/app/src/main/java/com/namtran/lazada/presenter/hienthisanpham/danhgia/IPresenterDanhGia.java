package com.namtran.lazada.presenter.hienthisanpham.danhgia;

import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.DanhGia;

/**
 * Created by namtr on 5/19/2017.
 */

interface IPresenterDanhGia {
    void themDanhGia(DanhGia danhGia);

    void layDanhSachDanhGiaTheoMaSP(Action action, int maSP, int startIndex);
}
