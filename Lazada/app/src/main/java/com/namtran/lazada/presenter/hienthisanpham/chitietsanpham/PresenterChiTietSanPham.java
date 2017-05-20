package com.namtran.lazada.presenter.hienthisanpham.chitietsanpham;

import android.content.Context;

import com.namtran.lazada.model.giohang.ModelGioHang;
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
    private ModelGioHang mModelGioHang;

    public PresenterChiTietSanPham() {
        mModelChiTietSP = new ModelChiTietSanPham();
        mModelGioHang = new ModelGioHang();
    }

    public PresenterChiTietSanPham(ViewChiTietSanPham viewChiTietSP) {
        this.mViewChiTietSP = viewChiTietSP;
        mModelChiTietSP = new ModelChiTietSanPham();
        mModelGioHang = new ModelGioHang();
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
    public void layDanhSachDanhGia(Action action, int maSP, int startIndex) {
        List<DanhGia> danhGiaList = mModelChiTietSP.layDanhSachDanhGia(action, maSP, startIndex);
        mViewChiTietSP.hienThiDanhGia(danhGiaList);
    }

    @Override
    public void themGioHang(Context context, SanPham sanPham) {
        mModelGioHang.moKetNoi(context);
        boolean result = mModelGioHang.themGioHang(sanPham);
        mViewChiTietSP.ketQuaThemGiohang(result);
    }

    @Override
    public long soLuongSPCoTrongGioHang(Context context) {
        mModelGioHang.moKetNoi(context);
        return mModelGioHang.getRowsCount();
    }
}
