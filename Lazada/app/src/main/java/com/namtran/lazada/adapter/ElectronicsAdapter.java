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
import com.namtran.lazada.model.objectclass.Electronics;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public class ElectronicsAdapter extends RecyclerView.Adapter<ElectronicsAdapter.ViewHolder> {
    private Context context;
    private List<Electronics> electronicsList;

    public ElectronicsAdapter(Context context, List<Electronics> electronicsList) {
        this.context = context;
        this.electronicsList = electronicsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_recycler_electronics, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Electronics electronics = electronicsList.get(position);
        // tạo adapter cho thương hiệu lớn, top điện thoại và máy tính bảng
        TopBrandAdapter thlAdapter = new TopBrandAdapter(context, electronics.getBrandList(), electronics.isQueryToBrand());
        TopProductAdapter mtbAdapter = new TopProductAdapter(context, R.layout.custom_recycler_electronics_gridview_top_product, electronics.getProductList());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.HORIZONTAL, false);
        holder.mRecyclerBrand.setLayoutManager(layoutManager);
        holder.mRecyclerBrand.setNestedScrollingEnabled(false); // để khi scrollup thì toolbar sẽ scrollup
        holder.mRecyclerBrand.setAdapter(thlAdapter);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.mRecyclerProduct.setLayoutManager(layoutManager);
        holder.mRecyclerProduct.setNestedScrollingEnabled(false);
        holder.mRecyclerProduct.setAdapter(mtbAdapter);

        holder.mTVTitleForBrand.setText(electronics.getTitleForBrandList());
        holder.mTVTitleForProduct.setText(electronics.getTitleForProductList());
    }

    @Override
    public int getItemCount() {
        return electronicsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIVDiscount;
        RecyclerView mRecyclerBrand;
        RecyclerView mRecyclerProduct;
        TextView mTVTitleForBrand;
        TextView mTVTitleForProduct;

        ViewHolder(View itemView) {
            super(itemView);
            mIVDiscount = (ImageView) itemView.findViewById(R.id.dientu_iv_khuyenmai);
            mRecyclerBrand = (RecyclerView) itemView.findViewById(R.id.dientu_recycler_thuonghieulon);
            mRecyclerProduct = (RecyclerView) itemView.findViewById(R.id.dientu_recycler_topsanpham);
            mTVTitleForBrand = (TextView) itemView.findViewById(R.id.dientu_tv_tieude);
            mTVTitleForProduct = (TextView) itemView.findViewById(R.id.dientu_tv_tieudetop);
        }
    }
}
