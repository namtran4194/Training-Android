package com.namtran.lazada.presenter.hienthisanpham.theodanhmuc;

import com.namtran.lazada.model.hienthisanpham.theodanhmuc.ModelHTSPTheoDanhMuc;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.view.hienthisanpham.theodanhmuc.ViewHienThiSPTheoDanhMuc;

import java.util.List;

/**
 * Created by namtr on 5/14/2017.
 */

public class PresenterHTSPTheoDanhMuc implements IPresenterHTSPTheoDanhMuc {
    private ViewHienThiSPTheoDanhMuc mHTSPTheoDanhMuc;
    private ModelHTSPTheoDanhMuc mModelHTSPTheoDanhMuc;

    public PresenterHTSPTheoDanhMuc(ViewHienThiSPTheoDanhMuc mHTSPTheoDanhMuc) {
        this.mHTSPTheoDanhMuc = mHTSPTheoDanhMuc;
        mModelHTSPTheoDanhMuc = new ModelHTSPTheoDanhMuc();
    }

    @Override
    public void layDanhSachSanPham(int code, boolean check) {
        List<SanPham> sanPhamList;
        Action action;

        if (check) {
            action = Action.SAN_PHAM_THEO_THUONG_HIEU;
            sanPhamList = mModelHTSPTheoDanhMuc.layDanhSachSanPhamTheoMaThuongHieu(action.getAction(), action.getParentNodeName(), code);
        } else {
            action = Action.SAN_PHAM_THEO_MA_LOAI;
            sanPhamList = mModelHTSPTheoDanhMuc.layDanhSachSanPhamTheoMaLoai(action.getAction(), action.getParentNodeName(), code);
        }

        mHTSPTheoDanhMuc.hienThiDanhSachSanPham(sanPhamList);
    }
}
