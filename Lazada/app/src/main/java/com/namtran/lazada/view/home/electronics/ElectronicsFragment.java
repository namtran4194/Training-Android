package com.namtran.lazada.view.home.electronics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.ElectronicsAdapter;
import com.namtran.lazada.adapter.TopLogoOfBrandAdapter;
import com.namtran.lazada.adapter.TopProductAdapter;
import com.namtran.lazada.connection.internet.Internet;
import com.namtran.lazada.model.objectclass.Electronics;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.objectclass.Brand;
import com.namtran.lazada.presenter.home.electronics.ElectronicsPresenter;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by namtr on 25/04/2017.
 */

public class ElectronicsFragment extends Fragment implements ElectronicsView {
    private RecyclerView mRecyclerElectronics, mRecyclerTopBrand, mRecyclerNewProduct;
    private ImageView mIV1, mIV2, mIV3;
    private TextView mTV1, mTV2, mTV3, mTV4, mTV5, mTV6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dien_tu, container, false);
        mRecyclerElectronics = (RecyclerView) v.findViewById(R.id.dientu_recycler_root);
        mRecyclerTopBrand = (RecyclerView) v.findViewById(R.id.dientu_recycler_logoTHLon);
        mRecyclerNewProduct = (RecyclerView) v.findViewById(R.id.dientu_recycler_hangmoive);

        mIV1 = (ImageView) v.findViewById(R.id.dientu_iv_sanpham1);
        mIV2 = (ImageView) v.findViewById(R.id.dientu_iv_sanpham2);
        mIV3 = (ImageView) v.findViewById(R.id.dientu_iv_sanpham3);

        mTV1 = (TextView) v.findViewById(R.id.dientu_tv_tieude1);
        mTV2 = (TextView) v.findViewById(R.id.dientu_tv_tieude2);
        mTV3 = (TextView) v.findViewById(R.id.dientu_tv_tieude3);
        mTV4 = (TextView) v.findViewById(R.id.dientu_tv_gia1);
        mTV5 = (TextView) v.findViewById(R.id.dientu_tv_gia2);
        mTV6 = (TextView) v.findViewById(R.id.dientu_tv_gia3);

        ElectronicsPresenter electronicsPresenter = new ElectronicsPresenter(this);
        if (new Internet(getContext()).isOnline()) {
            electronicsPresenter.getElectronicsList();
            electronicsPresenter.getTopBrandLogo();
            electronicsPresenter.getnewProducts();
        }
        return v;
    }

    @Override
    public void showTopBrands(List<Electronics> electronicsList) {
        if (electronicsList != null) {
            ElectronicsAdapter adapter = new ElectronicsAdapter(getContext(), electronicsList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerElectronics.setHasFixedSize(true);
            mRecyclerElectronics.setNestedScrollingEnabled(false);
            mRecyclerElectronics.setLayoutManager(layoutManager);
            mRecyclerElectronics.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showTopBrandLogo(List<Brand> brandList) {
        if (brandList != null) {
            TopLogoOfBrandAdapter adapter = new TopLogoOfBrandAdapter(getContext(), brandList);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerTopBrand.setHasFixedSize(true);
            mRecyclerTopBrand.setNestedScrollingEnabled(false);
            mRecyclerTopBrand.setLayoutManager(layoutManager);
            mRecyclerTopBrand.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showNewProducts(List<Product> productList) {
        if (productList != null) {
            TopProductAdapter adapter = new TopProductAdapter(getContext(), R.layout.custom_recycler_electronics_gridview_top_product, productList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerNewProduct.setHasFixedSize(true);
            mRecyclerNewProduct.setNestedScrollingEnabled(false);
            mRecyclerNewProduct.setLayoutManager(layoutManager);
            mRecyclerNewProduct.setAdapter(adapter);

            Random random = new Random();
            Product product;
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
            int position;

            position = random.nextInt(productList.size());
            product = productList.get(position);
            Picasso.with(getContext()).load(product.getBigImageUrl()).resize(200, 200).into(mIV1);
            mTV1.setText(product.getProductName());
            mTV4.setText(format.format(product.getPrice()));

            position = random.nextInt(productList.size());
            product = productList.get(position);
            Picasso.with(getContext()).load(product.getBigImageUrl()).resize(200, 200).into(mIV2);
            mTV2.setText(product.getProductName());
            mTV5.setText(format.format(product.getPrice()));

            position = random.nextInt(productList.size());
            product = productList.get(position);
            Picasso.with(getContext()).load(product.getBigImageUrl()).resize(200, 200).into(mIV3);
            mTV3.setText(product.getProductName());
            mTV6.setText(format.format(product.getPrice()));
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}
