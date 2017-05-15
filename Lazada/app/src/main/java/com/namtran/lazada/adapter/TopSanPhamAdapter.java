package com.namtran.lazada.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.tools.RippleMixer;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by namtr on 09/05/2017.
 */

public class TopSanPhamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context context;
    private int mLayoutResId;
    private List<SanPham> mSanPhams;
    private int mWidth;

    public TopSanPhamAdapter(Context context, int layoutResId, List<SanPham> sanPhamList) {
        this.context = context;
        this.mLayoutResId = layoutResId;
        this.mSanPhams = sanPhamList;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mWidth = metrics.widthPixels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(mLayoutResId, parent, false);
            // hiệu ứng khi nhấn vào một item
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                int color = Color.parseColor("#ffffff");
                double fraction = 0.2;

                ColorDrawable defaultColor = new ColorDrawable(color);
                Drawable rippleColor = RippleMixer.getRippleColor(color);
                ColorStateList pressedColor = ColorStateList.valueOf(RippleMixer.lightenOrDarken(color, fraction));

                RippleDrawable drawable = new RippleDrawable(pressedColor, defaultColor, rippleColor);
                v.setBackground(drawable);
            }

            // chia một item chiếm một nửa chiều rộng màn hình
            if (mLayoutResId == R.layout.custom_recycler_dientu_gridview_topsp)
                v.getLayoutParams().width = mWidth / 2 - 15;
            return new ItemViewHolder(v);
        } else if (viewType == VIEW_TYPE_LOADING) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            SanPham sanPham = mSanPhams.get(position);
            int gia = sanPham.getGia();
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
            String giaFormatted = format.format(gia);

            Picasso.with(context).load(sanPham.getAnhLon()).placeholder(R.drawable.ic_color_lens_black_24dp).resize(150, 150).into(viewHolder.mIVHinhSP);
            viewHolder.mTVTenSP.setText(sanPham.getTenSP());
            viewHolder.mTVGia.setText(giaFormatted);
            viewHolder.mTVGiamGia.setText(giaFormatted);
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder viewHolder = (LoadingViewHolder) holder;
            viewHolder.mProgressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mSanPhams.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mSanPhams.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mIVHinhSP;
        TextView mTVGia, mTVTenSP, mTVGiamGia;

        ItemViewHolder(View itemView) {
            super(itemView);
            mIVHinhSP = (ImageView) itemView.findViewById(R.id.dientu_topdtvamtb_hinhanh);
            mTVGia = (TextView) itemView.findViewById(R.id.dientu_topdtvamtb_gia);
            mTVTenSP = (TextView) itemView.findViewById(R.id.dientu_topdtvamtb_tensp);
            mTVGiamGia = (TextView) itemView.findViewById(R.id.dientu_topdtvamtb_giamgia);
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
