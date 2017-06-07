package com.namtran.lazada.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.customview.ButtonRippleDrawable;
import com.namtran.lazada.model.objectclass.Brand;
import com.namtran.lazada.view.showproduct.bycategory.ShowProductByCategory;
import com.namtran.lazada.view.home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

class TopBrandAdapter extends RecyclerView.Adapter<TopBrandAdapter.ViewHolder> {
    private Context context;
    private boolean check;
    private List<Brand> brandList;
    private ButtonRippleDrawable rippleDrawable;

    TopBrandAdapter(Context context, List<Brand> brandList, boolean check) {
        this.context = context;
        this.check = check;
        this.brandList = brandList;
        rippleDrawable = new ButtonRippleDrawable(Color.parseColor("#ffffff"), 0.2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_recycler_electronics_top_brand, parent, false);
        // hiệu ứng khi nhấn vào một item
        v.setBackground(rippleDrawable.getRipple());

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Brand brand = brandList.get(position);
        holder.mTVTitle.setText(brand.getBrandName());
        Picasso.with(context).load(brand.getBrandLogo()).placeholder(R.drawable.ic_image_black_24dp).resize(120, 120).into(holder.mIVImage);

        holder.mCVItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailItemActivity = new Intent(context, ShowProductByCategory.class);
                detailItemActivity.putExtra("MALOAI", brand.getBrandCode());
                detailItemActivity.putExtra("TENLOAI", brand.getBrandName());
                detailItemActivity.putExtra("CHECK", check);
                ((Activity) context).startActivityForResult(detailItemActivity, HomeActivity.REQUEST_CODE_CART);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTVTitle;
        ImageView mIVImage;
        CardView mCVItem;

        ViewHolder(View itemView) {
            super(itemView);
            mTVTitle = (TextView) itemView.findViewById(R.id.dientu_thuonghieulon_tenth);
            mIVImage = (ImageView) itemView.findViewById(R.id.dientu_thuonghieulon_hinhanh);
            mCVItem = (CardView) itemView.findViewById(R.id.dientu_thuonghieulon_item);
        }
    }
}
