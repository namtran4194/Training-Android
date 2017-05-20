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
import com.namtran.lazada.model.objectclass.ThuongHieu;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by namtr on 11/05/2017.
 */

public class LogoThuongHieuLonAdapter extends RecyclerView.Adapter<LogoThuongHieuLonAdapter.ViewHolder> {
    private Context context;
    private List<ThuongHieu> thuongHieuList;
    private ButtonRippleDrawable rippleDrawable;

    public LogoThuongHieuLonAdapter(Context context, List<ThuongHieu> thuongHieuList) {
        this.context = context;
        this.thuongHieuList = thuongHieuList;
        rippleDrawable = new ButtonRippleDrawable(Color.parseColor("#ffffff"), 0.2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_recycler_logothuonghieulon, parent, false);
        // hiệu ứng khi nhấn vào một item
        v.setBackground(rippleDrawable.getRipple());

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ThuongHieu thuongHieu = thuongHieuList.get(position);
        Picasso.with(context).load(thuongHieu.getHinhThuongHieu()).placeholder(R.drawable.ic_image_black_24dp).resize(150, 150).into(holder.mIVLogo);
    }

    @Override
    public int getItemCount() {
        return thuongHieuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIVLogo;

        ViewHolder(View itemView) {
            super(itemView);
            mIVLogo = (ImageView) itemView.findViewById(R.id.dientu_logoThuonghieulon_hinhanh);
        }
    }
}
