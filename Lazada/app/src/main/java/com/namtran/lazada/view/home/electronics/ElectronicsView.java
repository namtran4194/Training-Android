package com.namtran.lazada.view.home.electronics;

import com.namtran.lazada.model.objectclass.Electronics;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.objectclass.Brand;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public interface ElectronicsView {

    // hiển thị các thương hiệu lớn
    void showTopBrands(List<Electronics> electronicsList);
    // hiển thị logo các thương hiệu lớn
    void showTopBrandLogo(List<Brand> brandList);
    // hiển thị hàng mới về
    void showNewProducts(List<Product> productList);
}
