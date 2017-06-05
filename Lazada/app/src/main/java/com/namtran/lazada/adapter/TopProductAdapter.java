package com.namtran.lazada.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.customview.ButtonRippleDrawable;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.tools.Converter;
import com.namtran.lazada.view.showproduct.productdetail.ProductDetailActivity;
import com.namtran.lazada.view.showproduct.bycategory.ShowProductByCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by namtr on 09/05/2017.
 */

public class TopProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context mContext;
    private int mLayoutResId;
    private List<Product> mProducts;
    private ButtonRippleDrawable rippleDrawable;
    private int mWidth;

    public TopProductAdapter(Context context, int layoutResId, List<Product> productList) {
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mProducts = productList;
        rippleDrawable = new ButtonRippleDrawable(Color.parseColor("#ffffff"), 0.2);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mWidth = metrics.widthPixels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(mLayoutResId, parent, false);
            // hiệu ứng khi nhấn vào một item
            v.setBackground(rippleDrawable.getRipple());

            // chia một item chiếm một nửa chiều rộng màn hình
            if (mLayoutResId == R.layout.custom_recycler_electronics_gridview_top_product)
                v.getLayoutParams().width = mWidth / 2 - 15;
            return new ItemViewHolder(v);
        } else if (viewType == VIEW_TYPE_LOADING) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            Product product = mProducts.get(position);
            int gia = product.getPrice();

            Picasso.with(mContext).load(product.getBigImageUrl()).placeholder(R.drawable.ic_image_black_24dp).resize(150, 150).into(viewHolder.mIVProductImage);
            viewHolder.mTVProductName.setText(product.getProductName());
            viewHolder.mTVPrice.setText(Converter.formatCurrency(gia));
            viewHolder.mTVDiscount.setText(Converter.formatCurrency(gia));
            viewHolder.mCVItem.setTag(product.getProductCode()); // setTag cho cardview

            viewHolder.mCVItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chitietsanpham = new Intent(mContext, ProductDetailActivity.class);
                    chitietsanpham.putExtra("MASP", (int) v.getTag());
                    ((Activity) mContext).startActivityForResult(chitietsanpham, ShowProductByCategory.REQUEST_CODE_CART);
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder viewHolder = (LoadingViewHolder) holder;
            viewHolder.mProgressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mProducts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mIVProductImage;
        TextView mTVPrice, mTVProductName, mTVDiscount;
        CardView mCVItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            mIVProductImage = (ImageView) itemView.findViewById(R.id.dientu_topdtvamtb_hinhanh);
            mTVPrice = (TextView) itemView.findViewById(R.id.dientu_topdtvamtb_gia);
            mTVProductName = (TextView) itemView.findViewById(R.id.dientu_topdtvamtb_tensp);
            mTVDiscount = (TextView) itemView.findViewById(R.id.dientu_topdtvamtb_giamgia);
            mCVItem = (CardView) itemView.findViewById(R.id.dientu_topdtvamtb_cardview);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar mProgressBar;

        LoadingViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loading_item_progressBar);
        }
    }
}
