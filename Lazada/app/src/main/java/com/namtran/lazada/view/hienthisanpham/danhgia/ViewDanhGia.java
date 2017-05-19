package com.namtran.lazada.view.hienthisanpham.danhgia;

import com.namtran.lazada.model.objectclass.DanhGia;

import java.util.List;

/**
 * Created by namtr on 5/19/2017.
 */

public interface ViewDanhGia {
    void ketQuaThemDanhGia(boolean result);

    void hienThiDanhSachDanhGia(List<DanhGia> danhGias);
}
