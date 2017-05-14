package com.namtran.lazada.view.trangchu.dientu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.DienTuAdapter;
import com.namtran.lazada.adapter.LogoThuongHieuLonAdapter;
import com.namtran.lazada.adapter.TopSanPhamAdapter;
import com.namtran.lazada.connection.internet.Internet;
import com.namtran.lazada.model.objectclass.DienTu;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.model.objectclass.ThuongHieu;
import com.namtran.lazada.presenter.trangchu.dientu.PresenterDienTu;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by namtr on 25/04/2017.
 */

public class FragmentDienTu extends Fragment implements ViewDienTu {
    private RecyclerView mRecyclerDienTu, mRecyclerTopTHL, mRecyclerHangMoiVe;
    private ImageView mIV1, mIV2, mIV3;
    private TextView mTV1, mTV2, mTV3, mTV4, mTV5, mTV6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dien_tu, container, false);
        mRecyclerDienTu = (RecyclerView) v.findViewById(R.id.dientu_recycler_root);
        mRecyclerTopTHL = (RecyclerView) v.findViewById(R.id.dientu_recycler_logoTHLon);
        mRecyclerHangMoiVe = (RecyclerView) v.findViewById(R.id.dientu_recycler_hangmoive);

        mIV1 = (ImageView) v.findViewById(R.id.dientu_iv_sanpham1);
        mIV2 = (ImageView) v.findViewById(R.id.dientu_iv_sanpham2);
        mIV3 = (ImageView) v.findViewById(R.id.dientu_iv_sanpham3);

        mTV1 = (TextView) v.findViewById(R.id.dientu_tv_tieude1);
        mTV2 = (TextView) v.findViewById(R.id.dientu_tv_tieude2);
        mTV3 = (TextView) v.findViewById(R.id.dientu_tv_tieude3);
        mTV4 = (TextView) v.findViewById(R.id.dientu_tv_gia1);
        mTV5 = (TextView) v.findViewById(R.id.dientu_tv_gia2);
        mTV6 = (TextView) v.findViewById(R.id.dientu_tv_gia3);

        PresenterDienTu presenterDienTu = new PresenterDienTu(this);
        if (new Internet(getContext()).isOnline()) {
            presenterDienTu.layDanhSachDienTu();
            presenterDienTu.layLogoThuongHieuLon();
            presenterDienTu.layDanhSachHangMoiVe();
        }
        return v;
    }

    @Override
    public void hienThiThuongHieuLon(List<DienTu> dienTuList) {
        if (dienTuList != null) {
            DienTuAdapter adapter = new DienTuAdapter(getContext(), dienTuList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerDienTu.setHasFixedSize(true);
            mRecyclerDienTu.setNestedScrollingEnabled(false);
            mRecyclerDienTu.setLayoutManager(layoutManager);
            mRecyclerDienTu.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void hienThiLogoThuongHieu(List<ThuongHieu> thuongHieuList) {
        if (thuongHieuList != null) {
            LogoThuongHieuLonAdapter adapter = new LogoThuongHieuLonAdapter(getContext(), thuongHieuList);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerTopTHL.setHasFixedSize(true);
            mRecyclerTopTHL.setNestedScrollingEnabled(false);
            mRecyclerTopTHL.setLayoutManager(layoutManager);
            mRecyclerTopTHL.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void hienThiDanhSachHangMoiVe(List<SanPham> sanPhamList) {
        if (sanPhamList != null) {
            TopSanPhamAdapter adapter = new TopSanPhamAdapter(getContext(), sanPhamList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerHangMoiVe.setHasFixedSize(true);
            mRecyclerHangMoiVe.setNestedScrollingEnabled(false);
            mRecyclerHangMoiVe.setLayoutManager(layoutManager);
            mRecyclerHangMoiVe.setAdapter(adapter);

            Random random = new Random();
            SanPham sanPham;
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
            int position;

            position = random.nextInt(sanPhamList.size());
            sanPham = sanPhamList.get(position);
            Picasso.with(getContext()).load(sanPham.getAnhLon()).resize(200, 200).into(mIV1);
            mTV1.setText(sanPham.getTenSP());
            mTV4.setText(format.format(sanPham.getGia()));

            position = random.nextInt(sanPhamList.size());
            sanPham = sanPhamList.get(position);
            Picasso.with(getContext()).load(sanPham.getAnhLon()).resize(200, 200).into(mIV2);
            mTV2.setText(sanPham.getTenSP());
            mTV5.setText(format.format(sanPham.getGia()));

            position = random.nextInt(sanPhamList.size());
            sanPham = sanPhamList.get(position);
            Picasso.with(getContext()).load(sanPham.getAnhLon()).resize(200, 200).into(mIV3);
            mTV3.setText(sanPham.getTenSP());
            mTV6.setText(format.format(sanPham.getGia()));
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}
