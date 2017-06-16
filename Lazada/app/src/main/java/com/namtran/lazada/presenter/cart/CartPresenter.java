package com.namtran.lazada.presenter.cart;

import android.content.Context;

import com.namtran.lazada.model.cart.CartModel;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.view.cart.CartView;

import java.util.List;

/**
 * Created by namtr on 06/16/2017.
 */

public class CartPresenter implements ICart {
    private CartModel cartModel;
    private CartView cartView;

    public CartPresenter(CartView cartView) {
        this.cartModel = new CartModel();
        this.cartView = cartView;
    }

    @Override
    public void getProductsInCart(Context context) {
        cartModel.openConnection(context);
        List<Product> productList = cartModel.getAll();
        if (productList != null && productList.size() > 0)
            cartView.showProductsInCart(productList);
    }
}
