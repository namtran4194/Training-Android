package com.namtran.lazada.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.ThuongHieu;
import com.namtran.lazada.tools.RippleMixer;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by namtr on 11/05/2017.
 */

public class LogoThuongHieuLonAdapter extends RecyclerView.Adapter<LogoThuongHieuLonAdapter.ViewHolder> {
    private Context context;
    private List<ThuongHieu> thuongHieuList;

    public LogoThuongHieuLonAdapter(Context context, List<ThuongHieu> thuongHieuList) {
        this.context = context;
        this.thuongHieuList = thuongHieuList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_recycler_logothuonghieulon, parent, false);
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

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ThuongHieu thuongHieu = thuongHieuList.get(position);
        Picasso.with(context).load(thuongHieu.getHinhThuongHieu()).placeholder(R.drawable.ic_color_lens_black_24dp).resize(150, 150).into(holder.mIVLogo);
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
