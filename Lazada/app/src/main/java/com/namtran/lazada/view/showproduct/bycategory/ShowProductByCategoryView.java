package com.namtran.lazada.view.showproduct.bycategory;

import com.namtran.lazada.model.objectclass.Product;

import java.util.List;

/**
 * Created by namtr on 5/14/2017.
 */

public interface ShowProductByCategoryView {

    // hiển thị danh sách sản phẩm
    void showProducts(List<Product> productList);
}
