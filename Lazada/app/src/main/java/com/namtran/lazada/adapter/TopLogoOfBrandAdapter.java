package com.namtran.lazada.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.namtran.lazada.R;
import com.namtran.lazada.customview.ButtonRippleDrawable;
import com.namtran.lazada.model.objectclass.Brand;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by namtr on 11/05/2017.
 */

public class TopLogoOfBrandAdapter extends RecyclerView.Adapter<TopLogoOfBrandAdapter.ViewHolder> {
    private Context context;
    private List<Brand> brandList;
    private ButtonRippleDrawable rippleDrawable;

    public TopLogoOfBrandAdapter(Context context, List<Brand> brandList) {
        this.context = context;
        this.brandList = brandList;
        rippleDrawable = new ButtonRippleDrawable(Color.parseColor("#ffffff"), 0.2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_recycler_logo_of_brand, parent, false);
        // hiệu ứng khi nhấn vào một item
        v.setBackground(rippleDrawable.getRipple());

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        Picasso.with(context).load(brand.getBrandLogo()).placeholder(R.drawable.ic_image_black_24dp).resize(150, 150).into(holder.mIVLogo);
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIVLogo;

        ViewHolder(View itemView) {
            super(itemView);
            mIVLogo = (ImageView) itemView.findViewById(R.id.dientu_logoThuonghieulon_hinhanh);
        }
    }
}
