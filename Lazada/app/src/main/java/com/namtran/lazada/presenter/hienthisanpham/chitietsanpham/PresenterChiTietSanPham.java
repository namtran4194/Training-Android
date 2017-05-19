package com.namtran.lazada.presenter.hienthisanpham.chitietsanpham;

import com.namtran.lazada.model.hienthisanpham.chitietsanpham.ModelChiTietSanPham;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.DanhGia;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.view.hienthisanpham.chitietsanpham.ViewChiTietSanPham;

import java.util.List;

/**
 * Created by namtr on 5/18/2017.
 */

public class PresenterChiTietSanPham implements IPresenterChiTietSanPham {
    private ViewChiTietSanPham mViewChiTietSP;
    private ModelChiTietSanPham mModelChiTietSP;

    public PresenterChiTietSanPham(ViewChiTietSanPham viewChiTietSP) {
        this.mViewChiTietSP = viewChiTietSP;
        this.mModelChiTietSP = new ModelChiTietSanPham();
    }

    @Override
    public void layChiTietSanPham(Action action, int maSP) {
        SanPham sanPham = mModelChiTietSP.layChiTietSanPham(action, maSP);
        String stringAnhNho = sanPham.getAnhNho();
        String[] link = stringAnhNho.split(",");
        mViewChiTietSP.hienThiChiTietSanPham(sanPham);
        mViewChiTietSP.hienThiSlider(link);
    }

    @Override
    public void layDanhSachDanhGia(Action action, int maSP, int limit) {
        List<DanhGia> danhGiaList = mModelChiTietSP.layDanhSachDanhGia(action, maSP, limit);
        mViewChiTietSP.hienThiDanhGia(danhGiaList);
    }
}
