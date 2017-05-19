package com.namtran.lazada.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.DanhGia;

import java.util.List;

/**
 * Created by namtr on 5/20/2017.
 */

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ViewHolder> {
    private Context mContext;
    private List<DanhGia> mDanhGiaList;
    private int mLimit;

    public DanhGiaAdapter(Context context, List<DanhGia> danhGiaList, int limit) {
        this.mContext = context;
        this.mDanhGiaList = danhGiaList;
        this.mLimit = limit;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.custom_recycler_danhgia_sanpham, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DanhGia danhGia = mDanhGiaList.get(position);
        holder.mTVTieuDe.setText(danhGia.getTieuDe());
        holder.mTVNoiDung.setText(danhGia.getNoiDung());
        String nguoiDanhGia = "Được đăng bởi " + danhGia.getTenThietBi() + " ngày " + danhGia.getNgayDanhGia();
        holder.mTVNguoiDanhGia.setText(nguoiDanhGia);
        holder.mRating.setRating(danhGia.getSoSao());
    }

    @Override
    public int getItemCount() {
        if (mDanhGiaList.size() > mLimit) return mLimit <= 0 ? mDanhGiaList.size() : mLimit;
        return mDanhGiaList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTVTieuDe, mTVNoiDung, mTVNguoiDanhGia;
        RatingBar mRating;

        ViewHolder(View itemView) {
            super(itemView);

            mTVTieuDe = (TextView) itemView.findViewById(R.id.custom_recycler_danhgia_tieude);
            mTVNoiDung = (TextView) itemView.findViewById(R.id.custom_recycler_danhgia_noidung);
            mTVNguoiDanhGia = (TextView) itemView.findViewById(R.id.custom_recycler_danhgia_nguoidang);
            mRating = (RatingBar) itemView.findViewById(R.id.custom_recycler_danhgia_rating);
        }
    }
}
