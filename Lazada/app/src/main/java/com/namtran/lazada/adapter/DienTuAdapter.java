package com.namtran.lazada.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.DienTu;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public class DienTuAdapter extends RecyclerView.Adapter<DienTuAdapter.ViewHolder> {
    private Context context;
    private List<DienTu> dienTuList;

    public DienTuAdapter(Context context, List<DienTu> dienTuList) {
        this.context = context;
        this.dienTuList = dienTuList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_recycler_dientu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DienTu dienTu = dienTuList.get(position);
        // tạo adapter cho thương hiệu lớn, top điện thoại và máy tính bảng
        ThuongHieuLonAdapter thlAdapter = new ThuongHieuLonAdapter(context, dienTu.getThuongHieuList());
        TopSPAdapter mtbAdapter = new TopSPAdapter(context, dienTu.getSanPhamList());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.HORIZONTAL, false);
        holder.mRecyclerThuongHieuLon.setLayoutManager(layoutManager);
        holder.mRecyclerThuongHieuLon.setNestedScrollingEnabled(false); // để khi scrollup thì toolbar sẽ scrollup
        holder.mRecyclerThuongHieuLon.setAdapter(thlAdapter);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.mRecyclerTopSP.setLayoutManager(layoutManager);
        holder.mRecyclerTopSP.setNestedScrollingEnabled(false);
        holder.mRecyclerTopSP.setAdapter(mtbAdapter);

        holder.mTVTieuDe.setText(dienTu.getTenTieuDe());
        holder.mTVTieuDeTop.setText(dienTu.getTenTieuDeTop());
    }

    @Override
    public int getItemCount() {
        if (dienTuList == null) return 0;
        return dienTuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIVKhuyenMai;
        RecyclerView mRecyclerThuongHieuLon, mRecyclerTopSP;
        TextView mTVTieuDe, mTVTieuDeTop;

        ViewHolder(View itemView) {
            super(itemView);
            mIVKhuyenMai = (ImageView) itemView.findViewById(R.id.dientu_iv_khuyenmai);
            mRecyclerThuongHieuLon = (RecyclerView) itemView.findViewById(R.id.dientu_recycler_thuonghieulon);
            mRecyclerTopSP = (RecyclerView) itemView.findViewById(R.id.dientu_recycler_topsp);
            mTVTieuDe = (TextView) itemView.findViewById(R.id.dientu_tv_tieude);
            mTVTieuDeTop = (TextView) itemView.findViewById(R.id.dientu_tv_tieudetop);
        }
    }
}
