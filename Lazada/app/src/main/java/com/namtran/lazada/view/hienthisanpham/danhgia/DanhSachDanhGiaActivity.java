package com.namtran.lazada.view.hienthisanpham.danhgia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.DanhGiaAdapter;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.DanhGia;
import com.namtran.lazada.model.objectclass.OnLoadMoreListener;
import com.namtran.lazada.model.objectclass.OnScrollRecyclerListener;
import com.namtran.lazada.presenter.hienthisanpham.danhgia.PresenterDanhGia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 5/20/2017.
 */

public class DanhSachDanhGiaActivity extends AppCompatActivity implements ViewDanhGia, OnLoadMoreListener {
    private RecyclerView mRecyclerDanhGiaList;
    private ProgressBar mProgress;
    private int maSP;
    private PresenterDanhGia mPresenterDanhGia;
    private List<DanhGia> mDanhGiaList;
    private OnScrollRecyclerListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachdanhgia);

        Toolbar toolbar = (Toolbar) findViewById(R.id.danhsachdanhgia_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        mRecyclerDanhGiaList = (RecyclerView) findViewById(R.id.danhsachdanhgia_recycler);
        mProgress = (ProgressBar) findViewById(R.id.danhsachdanhgia_progress);

        mDanhGiaList = new ArrayList<>();
        maSP = getIntent().getIntExtra("MASP", -1);
        mPresenterDanhGia = new PresenterDanhGia(this);

        showProgressBar();
        mPresenterDanhGia.layDanhSachDanhGiaTheoMaSP(Action.DANH_SACH_DANH_GIA, maSP, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ketQuaThemDanhGia(boolean result) {

    }

    @Override
    public void hienThiDanhSachDanhGia(List<DanhGia> danhGias) {
        hideProgressBar();
        if (danhGias != null && danhGias.size() > 0) {
            mDanhGiaList.addAll(danhGias);
            DanhGiaAdapter adapter = new DanhGiaAdapter(this, mDanhGiaList, 0);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerDanhGiaList.setLayoutManager(layoutManager);
            mRecyclerDanhGiaList.setAdapter(adapter);
            listener = new OnScrollRecyclerListener(this, layoutManager);
            mRecyclerDanhGiaList.addOnScrollListener(listener);
        } else {
            Toast.makeText(this, "Không có đánh giá nào", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadMore(int totalItems) {
        showProgressBar();
        mPresenterDanhGia.layDanhSachDanhGiaTheoMaSP(Action.DANH_SACH_DANH_GIA, maSP, totalItems);
        listener.setLoaded();
    }

    private void showProgressBar() {
        mProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgress.setVisibility(View.GONE);
    }
}
