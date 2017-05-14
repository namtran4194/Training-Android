package com.namtran.lazada.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.ThuongHieu;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by namtr on 08/05/2017.
 */

class ThuongHieuLonAdapter extends RecyclerView.Adapter<ThuongHieuLonAdapter.ViewHolder> {
    private Context context;
    private List<ThuongHieu> thuongHieuList;

    ThuongHieuLonAdapter(Context context, List<ThuongHieu> thuongHieuList) {
        this.context = context;
        this.thuongHieuList = thuongHieuList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_recycler_dientu_thuonghieulon, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ThuongHieu thuongHieu = thuongHieuList.get(position);
        holder.mTVTieuDe.setText(thuongHieu.getTenThuongHieu());
        Picasso.with(context).load(thuongHieu.getHinhThuongHieu()).resize(120, 120).into(holder.mIVHinhAnh);
    }

    @Override
    public int getItemCount() {
        return thuongHieuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTVTieuDe;
        ImageView mIVHinhAnh;

        ViewHolder(View itemView) {
            super(itemView);
            mTVTieuDe = (TextView) itemView.findViewById(R.id.dientu_thuonghieulon_tenth);
            mIVHinhAnh = (ImageView) itemView.findViewById(R.id.dientu_thuonghieulon_hinhanh);
        }
    }
}
