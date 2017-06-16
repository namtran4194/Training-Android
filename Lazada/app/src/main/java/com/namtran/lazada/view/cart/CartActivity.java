package com.namtran.lazada.view.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.CartAdapter;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.presenter.cart.CartPresenter;

import java.util.List;

/**
 * Created by namtr on 06/15/2017.
 */

public class CartActivity extends AppCompatActivity implements CartView {
    private RecyclerView cartRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecycler = (RecyclerView) findViewById(R.id.cart_recycler);
        CartPresenter cartPresenter = new CartPresenter(this);
        cartPresenter.getProductsInCart(this);
    }

    @Override
    public void showProductsInCart(List<Product> productList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        CartAdapter adapter = new CartAdapter(this, productList);
        cartRecycler.setLayoutManager(layoutManager);
        cartRecycler.setAdapter(adapter);
    }
}
