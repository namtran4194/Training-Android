package com.namtran.lazada.view.trangchu.dientu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.DienTuAdapter;
import com.namtran.lazada.model.objectclass.DienTu;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.model.objectclass.ThuongHieu;
import com.namtran.lazada.presenter.trangchu.dientu.PresenterDienTu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 25/04/2017.
 */

public class FragmentDienTu extends Fragment implements ViewDienTu {
    private RecyclerView mRecyclerDienTu;
    private PresenterDienTu presenterDienTu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dien_tu, container, false);
        mRecyclerDienTu = (RecyclerView) v.findViewById(R.id.dientu_recyclerRoot);
        presenterDienTu = new PresenterDienTu(this);
        presenterDienTu.layDanhSachDienTu();
        return v;
    }

    @Override
    public void hienThiThuongHieuLon(List<DienTu> dienTuList) {
        if (dienTuList != null) {
            DienTuAdapter adapter = new DienTuAdapter(getContext(), dienTuList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//            mRecyclerDienTu.setHasFixedSize(true);
            mRecyclerDienTu.setLayoutManager(layoutManager);
            mRecyclerDienTu.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}
