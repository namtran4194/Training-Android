package com.namtran.lazada.presenter.dangky;

import com.namtran.lazada.model.dangnhap_dangky.ModelDangKy;
import com.namtran.lazada.model.objectclass.NhanVien;
import com.namtran.lazada.view.dangnhap_dangky.ViewDangKy;

/**
 * Created by namtr on 05/05/2017.
 */

public class PresenterDangKy implements IPresenterDangKy {
    private ViewDangKy viewDangKy;
    private ModelDangKy modelDangKy;

    public PresenterDangKy(ViewDangKy viewDangKy) {
        this.viewDangKy = viewDangKy;
        modelDangKy = new ModelDangKy();
    }

    @Override
    public void thucHienDangKy(NhanVien nhanVien) {
        boolean isSuccessed = modelDangKy.dangKyThanhVIen(nhanVien);
        if (isSuccessed)
            viewDangKy.dangKyThanhCong();
        else
            viewDangKy.dangKyThatBai();
    }
}
