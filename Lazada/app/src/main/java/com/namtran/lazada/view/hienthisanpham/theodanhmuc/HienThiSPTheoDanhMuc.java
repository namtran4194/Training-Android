package com.namtran.lazada.view.hienthisanpham.theodanhmuc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.TopSanPhamAdapter;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.presenter.hienthisanpham.theodanhmuc.PresenterHTSPTheoDanhMuc;

import java.util.List;

/**
 * Created by namtr on 5/14/2017.
 */

public class HienThiSPTheoDanhMuc extends AppCompatActivity implements ViewHienThiSPTheoDanhMuc, View.OnClickListener {
    private RecyclerView mRecyclerSanPham;
    private ImageButton mButtonTypeView;
    private TopSanPhamAdapter mVerticalAdapter, mGridViewAdapter;
    private boolean gridview = true, isDataNull = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hien_thi_sp_theo_danh_muc_activity);

        Intent intent = getIntent();
        int code = intent.getIntExtra("MALOAI", -1);
        String productName = intent.getStringExtra("TENLOAI");
        boolean check = intent.getBooleanExtra("CHECK", false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.htsptdm_toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(productName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        mRecyclerSanPham = (RecyclerView) findViewById(R.id.htsptdm_recycler_sanpham);
        mButtonTypeView = (ImageButton) findViewById(R.id.htsptdm_btn_kieuxem);
        mButtonTypeView.setOnClickListener(this);
//        FrameLayout layoutTypeView = (FrameLayout) findViewById(R.id.htsptdm_frameLayout_kieuxem);
//        layoutTypeView.setOnClickListener(this);

        PresenterHTSPTheoDanhMuc danhMuc = new PresenterHTSPTheoDanhMuc(this);
        danhMuc.layDanhSachSanPham(code, check);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
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
    public void hienThiDanhSachSanPham(List<SanPham> sanPhamList) {
        if (sanPhamList != null) {
            isDataNull = false;
            // initial adapter
            mGridViewAdapter = new TopSanPhamAdapter(this, R.layout.custom_recycler_dientu_gridview_topsp, sanPhamList);
            mVerticalAdapter = new TopSanPhamAdapter(this, R.layout.custom_recycler_dientu_vertical_topsp, sanPhamList);

            mRecyclerSanPham.setLayoutManager(getLayoutManager());
            mRecyclerSanPham.setAdapter(getAdapter());
        } else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (gridview) {
            return new GridLayoutManager(this, 2);
        } else {
            return new LinearLayoutManager(this);
        }
    }

    private TopSanPhamAdapter getAdapter() {
        if (gridview) {
            return mGridViewAdapter;
        } else {
            return mVerticalAdapter;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.htsptdm_btn_kieuxem:
                gridview = !gridview;
                if (!isDataNull) {
                    mRecyclerSanPham.setLayoutManager(getLayoutManager());
                    mRecyclerSanPham.setAdapter(getAdapter());
                }
                changeButtonImage();
                break;
        }
    }

    private void changeButtonImage() {
        if (gridview) {
            mButtonTypeView.setImageResource(R.drawable.ic_view_list_black_24dp);
        } else {
            mButtonTypeView.setImageResource(R.drawable.ic_view_module_black_24dp);
        }
    }
}
