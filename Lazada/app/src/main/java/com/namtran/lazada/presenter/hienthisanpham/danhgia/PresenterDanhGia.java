package com.namtran.lazada.presenter.hienthisanpham.danhgia;

import com.namtran.lazada.model.hienthisanpham.danhgia.ModelDanhGia;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.DanhGia;
import com.namtran.lazada.view.hienthisanpham.danhgia.ViewDanhGia;

import java.util.List;

/**
 * Created by namtr on 5/19/2017.
 */

public class PresenterDanhGia implements IPresenterDanhGia {
    private ViewDanhGia viewDanhGia;
    private ModelDanhGia modelDanhGia;

    public PresenterDanhGia(ViewDanhGia viewDanhGia) {
        this.viewDanhGia = viewDanhGia;
        modelDanhGia = new ModelDanhGia();
    }

    @Override
    public void themDanhGia(DanhGia danhGia) {
        Action action = Action.THEM_DANH_GIA;
        boolean result = modelDanhGia.themDanhGia(action, danhGia);
        viewDanhGia.ketQuaThemDanhGia(result);
    }

    @Override
    public void layDanhSachDanhGiaTheoMaSP(Action action, int maSP, int startIndex) {
        List<DanhGia> danhGiaList = modelDanhGia.layDanhSachDanhGia(action, maSP, startIndex);
        viewDanhGia.hienThiDanhSachDanhGia(danhGiaList);
    }
}
