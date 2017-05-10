package com.namtran.lazada.view.trangchu.dientu;

import com.namtran.lazada.model.objectclass.DienTu;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.model.objectclass.ThuongHieu;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public interface ViewDienTu {
    void hienThiThuongHieuLon(List<DienTu> dienTuList);
}
