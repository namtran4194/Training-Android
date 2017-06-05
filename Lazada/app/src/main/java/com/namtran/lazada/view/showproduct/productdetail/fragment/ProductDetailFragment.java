package com.namtran.lazada.view.showproduct.productdetail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.namtran.lazada.R;
import com.squareup.picasso.Picasso;

/**
 * Created by namtr on 5/18/2017.
 */

public class ProductDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_detail_slider, container, false);

        Bundle bundle = getArguments();
        String link = bundle.getString("LINKANH"); // link ảnh truyền từ ProductDetailActivity

        ImageView imageView = (ImageView) v.findViewById(R.id.fragment_chitietsanpham_hinhanh);
        Picasso.with(getContext()).load(link).placeholder(R.drawable.ic_image_black_24dp).into(imageView);

        return v;
    }
}
