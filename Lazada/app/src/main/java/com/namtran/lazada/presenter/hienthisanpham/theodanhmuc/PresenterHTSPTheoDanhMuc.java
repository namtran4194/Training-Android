package com.namtran.lazada.presenter.hienthisanpham.theodanhmuc;

import android.util.Log;

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
    public void layDanhSachSanPham(int typeCode, boolean loadThuongHieu) {
        List<SanPham> sanPhamList = loadMore(typeCode, loadThuongHieu, 0);
        mHTSPTheoDanhMuc.hienThiDanhSachSanPham(sanPhamList);
    }

    public List<SanPham> loadMore(int typeCode, boolean loadThuongHieu, int startIndex) {
        List<SanPham> sanPhamList;
        Action action;

        if (loadThuongHieu) {
            action = Action.SAN_PHAM_THEO_THUONG_HIEU;
            sanPhamList = mModelHTSPTheoDanhMuc.layDanhSachSanPhamTheoMaThuongHieu(action, typeCode, startIndex);
        } else {
            action = Action.SAN_PHAM_THEO_MA_LOAI;
            sanPhamList = mModelHTSPTheoDanhMuc.layDanhSachSanPhamTheoMaLoai(action, typeCode, startIndex);
        }
//        Log.d("kiemtra", sanPhamList.size() + "");
        return sanPhamList;
    }
}
