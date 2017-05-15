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
import com.namtran.lazada.model.objectclass.OnLoadMoreListener;
import com.namtran.lazada.model.objectclass.OnScrollRecyclerListener;
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
    private boolean mIsGridview = true;
    private boolean mIsDataNull = true; // true nếu mSanPhams = null hoặc size() = 0
    private PresenterHTSPTheoDanhMuc mPresenterDanhMuc;
    private int mTypeCode;
    private boolean mLoadThuongHieu;
    private List<SanPham> mSanPhams;
    private OnScrollRecyclerListener mOnScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_sp_theo_danh_muc);

        // lấy MALOAISP / MATHUONGHIEU, biến check để xác định mã truyền sang là cái nào và tên loại danh mục sản phẩm
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

        // sự kiện scroll RecyclerView
        mOnScrollListener = new OnScrollRecyclerListener(this);

        // 2 loại adapter cho RecyclerView
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
            mSanPhams = sanPhamList;
            mIsDataNull = false;
            // khởi tạo adapter
            mGridViewAdapter = new TopSanPhamAdapter(this, R.layout.custom_recycler_dientu_gridview_topsp, mSanPhams);
            mVerticalAdapter = new TopSanPhamAdapter(this, R.layout.custom_recycler_dientu_vertical_topsp, mSanPhams);

            mRecyclerSanPham.setLayoutManager(getLayoutManager());
            mRecyclerSanPham.setAdapter(getAdapter());

            mOnScrollListener.setmLayoutManager(getLayoutManager());
            mRecyclerSanPham.addOnScrollListener(mOnScrollListener);
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
                    mOnScrollListener.setmLayoutManager(getLayoutManager());
                    mRecyclerSanPham.clearOnScrollListeners();
                    mRecyclerSanPham.addOnScrollListener(mOnScrollListener);
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
        // thêm item = null để hiển ProgressBar
        mSanPhams.add(null);
        mRecyclerSanPham.post(new Runnable() {
            @Override
            public void run() {
                getAdapter().notifyItemInserted(mSanPhams.size() - 1);
                // xóa loading item
                mSanPhams.remove(mSanPhams.size() - 1);
                getAdapter().notifyItemRemoved(mSanPhams.size());

                final List<SanPham> sanPhamList = mPresenterDanhMuc.loadMore(mTypeCode, mLoadThuongHieu, totalItems);
                if (sanPhamList != null && sanPhamList.size() > 0) {
                    mSanPhams.addAll(sanPhamList);
                    getAdapter().notifyDataSetChanged();
                    mOnScrollListener.setLoaded();
                }
            }
        });
    }
}
