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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.TopSanPhamAdapter;
import com.namtran.lazada.model.objectclass.OnLoadMoreListener;
import com.namtran.lazada.model.objectclass.LoadMoreRecycler;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.presenter.hienthisanpham.theodanhmuc.PresenterHTSPTheoDanhMuc;

import java.util.List;

/**
 * Created by namtr on 5/14/2017.
 */

public class HienThiSPTheoDanhMuc extends AppCompatActivity implements ViewHienThiSPTheoDanhMuc, View.OnClickListener, OnLoadMoreListener {
    private RecyclerView mRecyclerSanPham;
    private ImageButton mButtonTypeView;
    private RecyclerView.LayoutManager mLinearLM, mGridLM;
    private TopSanPhamAdapter mVerticalAdapter, mGridViewAdapter;
    private boolean mIsGridview = true, mIsDataNull = true;
    private PresenterHTSPTheoDanhMuc mPresenterDanhMuc;
    private int mTypeCode;
    private boolean mLoadThuongHieu;
    private boolean mIsLoading;
    private List<SanPham> sanPhamList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_sp_theo_danh_muc);

        Intent intent = getIntent();
        mTypeCode = intent.getIntExtra("MALOAI", -1);
        mLoadThuongHieu = intent.getBooleanExtra("CHECK", false);
        String productName = intent.getStringExtra("TENLOAI");

        Toolbar toolbar = (Toolbar) findViewById(R.id.htsptdm_toolBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(productName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        mGridLM = new GridLayoutManager(this, 2);
        mLinearLM = new LinearLayoutManager(this);

        mRecyclerSanPham = (RecyclerView) findViewById(R.id.htsptdm_recycler_sanpham);
        mButtonTypeView = (ImageButton) findViewById(R.id.htsptdm_btn_kieuxem);
        mButtonTypeView.setOnClickListener(this);

        mPresenterDanhMuc = new PresenterHTSPTheoDanhMuc(this);
        mPresenterDanhMuc.layDanhSachSanPham(mTypeCode, mLoadThuongHieu);
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
            this.sanPhamList = sanPhamList;
            mIsDataNull = false;
            // initial adapter
            mGridViewAdapter = new TopSanPhamAdapter(this, R.layout.custom_recycler_dientu_gridview_topsp, this.sanPhamList);
            mVerticalAdapter = new TopSanPhamAdapter(this, R.layout.custom_recycler_dientu_vertical_topsp, this.sanPhamList);

            mRecyclerSanPham.setLayoutManager(getLayoutManager());
            mRecyclerSanPham.setAdapter(getAdapter());

            mRecyclerSanPham.addOnScrollListener(new LoadMoreRecycler(getLayoutManager(), this));
        } else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (mIsGridview) {
            return mGridLM;
        } else {
            return mLinearLM;
        }
    }

    private TopSanPhamAdapter getAdapter() {
        if (mIsGridview) {
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
                mIsGridview = !mIsGridview;
                if (!mIsDataNull) {
                    mRecyclerSanPham.setLayoutManager(getLayoutManager());
                    mRecyclerSanPham.setAdapter(getAdapter());
                    // tạo mới Listener để truyền LayoutManager khác, cần xóa Listener hiện tại của View
                    mRecyclerSanPham.clearOnScrollListeners();
                    mRecyclerSanPham.addOnScrollListener(new LoadMoreRecycler(getLayoutManager(), this));
                }
                changeButtonImage();
                break;
        }
    }

    private void changeButtonImage() {
        if (mIsGridview) {
            mButtonTypeView.setImageResource(R.drawable.ic_view_list_black_24dp);
        } else {
            mButtonTypeView.setImageResource(R.drawable.ic_view_module_black_24dp);
        }
    }

    @Override
    public void onLoadMore(final int totalItems) {
        if (!mIsLoading) {
            mIsLoading = true;

            final List<SanPham> sanPhamList = mPresenterDanhMuc.loadMore(mTypeCode, mLoadThuongHieu, totalItems);
            if (sanPhamList != null && sanPhamList.size() > 0) {
                this.sanPhamList.addAll(sanPhamList);
                mRecyclerSanPham.post(new Runnable() {
                    @Override
                    public void run() {
                        getAdapter().notifyItemRangeInserted(totalItems - 1, sanPhamList.size());
                    }
                });
                mIsLoading = false;
            }

        }
    }
}
