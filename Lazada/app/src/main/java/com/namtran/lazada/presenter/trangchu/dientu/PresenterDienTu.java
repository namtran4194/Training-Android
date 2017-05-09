package com.namtran.lazada.presenter.trangchu.dientu;

import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.model.objectclass.ThuongHieu;
import com.namtran.lazada.model.trangchu.dientu.ModelDienTu;
import com.namtran.lazada.view.trangchu.dientu.ViewDienTu;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public class PresenterDienTu implements IPresenterDienTu {
    private ViewDienTu viewDienTu;
    private ModelDienTu modelDienTu;

    public PresenterDienTu(ViewDienTu viewDienTu) {
        this.viewDienTu = viewDienTu;
        this.modelDienTu = new ModelDienTu();
    }

    @Override
    public void layDanhSachDienTu() {
        List<ThuongHieu> thuongHieuList = modelDienTu.layDanhSachThuongHieuLon();
        List<SanPham> sanPhamList = modelDienTu.layDanhSachDTvaMTB();
        if (thuongHieuList != null && sanPhamList != null) {
            viewDienTu.hienThiThuongHieuLon(thuongHieuList, sanPhamList);
        }
    }
}
