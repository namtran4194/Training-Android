package com.namtran.lazada.view.hienthisanpham.chitietsanpham;

import com.namtran.lazada.model.objectclass.DanhGia;
import com.namtran.lazada.model.objectclass.SanPham;

import java.util.List;

/**
 * Created by namtr on 5/18/2017.
 */

public interface ViewChiTietSanPham {
    void hienThiChiTietSanPham(SanPham sanPham);

    void hienThiSlider(String... link);

    void hienThiDanhGia(List<DanhGia> danhGias);

    void ketQuaThemGiohang(boolean result);
}
