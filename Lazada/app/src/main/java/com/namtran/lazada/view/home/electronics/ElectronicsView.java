package com.namtran.lazada.view.home.electronics;

import com.namtran.lazada.model.objectclass.Electronics;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.objectclass.Brand;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public interface ElectronicsView {
    void hienThiThuongHieuLon(List<Electronics> electronicsList);

    void hienThiLogoThuongHieu(List<Brand> brandList);

    void hienThiDanhSachHangMoiVe(List<Product> productList);
}
