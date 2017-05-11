package com.namtran.lazada.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.SanPham;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by namtr on 09/05/2017.
 */

public class TopSanPhamAdapter extends RecyclerView.Adapter<TopSanPhamAdapter.ViewHolder> {
    private Context context;
    private List<SanPham> sanPhamList;

    public TopSanPhamAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_recycler_dientu_topsp, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        int gia = sanPham.getGia();
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String giaFormatted = format.format(gia);

        Picasso.with(context).load(sanPham.getAnhLon()).resize(200, 200).into(holder.mIVHinhSP);
        holder.mTVTenSP.setText(sanPham.getTenSP());
        holder.mTVGia.setText(giaFormatted);
        holder.mTVGiamGia.setText(giaFormatted);
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIVHinhSP;
        TextView mTVGia, mTVTenSP, mTVGiamGia;

        ViewHolder(View itemView) {
            super(itemView);
            mIVHinhSP = (ImageView) itemView.findViewById(R.id.dientu_topdtvamtb_hinhanh);
            mTVGia = (TextView) itemView.findViewById(R.id.dientu_topdtvamtb_gia);
            mTVTenSP = (TextView) itemView.findViewById(R.id.dientu_topdtvamtb_tensp);
            mTVGiamGia = (TextView) itemView.findViewById(R.id.dientu_topdtvamtb_giamgia);
        }
    }
}
