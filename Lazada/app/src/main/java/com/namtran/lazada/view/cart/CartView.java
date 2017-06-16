package com.namtran.lazada.view.cart;

import com.namtran.lazada.model.objectclass.Product;

import java.util.List;

/**
 * Created by namtr on 06/16/2017.
 */

public interface CartView {
    void showProductsInCart(List<Product> productList);
}
