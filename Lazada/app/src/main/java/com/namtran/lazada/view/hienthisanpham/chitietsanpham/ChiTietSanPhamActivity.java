package com.namtran.lazada.view.hienthisanpham.chitietsanpham;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.ViewPagerCTSPAdapter;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.ChiTietSanPham;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.presenter.hienthisanpham.chitietsanpham.PresenterChiTietSanPham;
import com.namtran.lazada.tools.Converter;
import com.namtran.lazada.view.hienthisanpham.chitietsanpham.fragment.FragmentChiTietSP;
import com.namtran.lazada.view.hienthisanpham.danhgia.DanhGiaActivity;
import com.namtran.lazada.view.trangchu.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 5/16/2017.
 */

public class ChiTietSanPhamActivity extends AppCompatActivity implements ViewChiTietSanPham, View.OnClickListener {
    private ViewPager mSlider;
    private List<Fragment> mFragment;
    private LinearLayout mDotLayout;
    private TextView mTVTenSP, mTVGia;
    private ImageButton mButtonFavorite;
    private boolean mIsFavorite; // lưu trạng thái mButtonFavorite
    private TextView mTVNguoiBan;
    private TextView mTVThongTinChiTiet;
    private ImageButton mButtonXemThemThongTin;
    private boolean mIsExpanded; // lưu trạng thái mButtonXemThemThongTin
    private LinearLayout mLayoutThongSoKyThuat;
    private int masp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        // initialize
        init();
        mFragment = new ArrayList<>();

        // retrieve data
        int masp = getIntent().getIntExtra("MASP", -1);
        PresenterChiTietSanPham presenterChiTiet = new PresenterChiTietSanPham(this);
        presenterChiTiet.layChiTietSanPham(Action.CHI_TIET_SAN_PHAM, masp);

        setupDotLayout(0);
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.chitietsanpham_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chi tiết sản phẩm");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        mSlider = (ViewPager) findViewById(R.id.chitietsanpham_viewPager_hinhanh);
        mSlider.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setupDotLayout(position);
            }
        });
        mDotLayout = (LinearLayout) findViewById(R.id.chitietsanpham_layout_dot);
        mTVTenSP = (TextView) findViewById(R.id.chitietsanpham_tv_tensanpham);
        mTVGia = (TextView) findViewById(R.id.chitietsanpham_tv_gia);
        mButtonFavorite = (ImageButton) findViewById(R.id.chitietsanpham_btn_yeuthich);
        mButtonFavorite.setOnClickListener(this);
        mTVNguoiBan = (TextView) findViewById(R.id.chitietsanpham_tv_nguoiban);
        mTVThongTinChiTiet = (TextView) findViewById(R.id.chitietsanpham_tv_chitietsp);
        mButtonXemThemThongTin = (ImageButton) findViewById(R.id.chitietsanpham_btn_xemthem_thongtin);
        mLayoutThongSoKyThuat = (LinearLayout) findViewById(R.id.chitietsanpham_layout_thongsokythuat);
        TextView tvVietDanhGia = (TextView) findViewById(R.id.chitietsanpham_tv_danhgia);
        tvVietDanhGia.setOnClickListener(this);
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
    public void hienThiChiTietSanPham(SanPham sanPham) {
        if (sanPham != null) {
            masp = sanPham.getMaSP();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(sanPham.getTenSP());
            }

            // hiển thị thông tin sản phẩm
            mTVTenSP.setText(sanPham.getTenSP());
            mTVGia.setText(Converter.formatCurrency(sanPham.getGia()));
            mTVNguoiBan.setText(sanPham.getTenNV());

            // hiển thị chi tiết sản phẩm, mặc định cho hiển thị 100 ký tự
            String chitietsanpham = sanPham.getThongTin();
            mTVThongTinChiTiet.setText(chitietsanpham.substring(0, 100));

            // setup button xem thêm
            if (chitietsanpham.length() <= 100) mButtonXemThemThongTin.setVisibility(View.GONE);
            else {
                mButtonXemThemThongTin.setVisibility(View.VISIBLE);
                mButtonXemThemThongTin.setTag(chitietsanpham);
                mButtonXemThemThongTin.setOnClickListener(this);
            }

            setupChiTietSPLayout(sanPham.getChiTietSanPhams());
        }
    }

    @Override
    public void hienThiSlider(String... links) {
        if (links != null && links.length > 0) {
            List<Fragment> fragments = new ArrayList<>();
            for (String aLink : links) {
                FragmentChiTietSP chiTietSP = new FragmentChiTietSP();
                Bundle bundle = new Bundle();
                bundle.putString("LINKANH", TrangChuActivity.SERVER_NAME + aLink);
                chiTietSP.setArguments(bundle);
                fragments.add(chiTietSP);
            }
            ViewPagerCTSPAdapter adapter = new ViewPagerCTSPAdapter(getSupportFragmentManager(), fragments);
            mSlider.setAdapter(adapter);

            mFragment = fragments;
        }
    }

    // thêm dấu chấm biểu thị số trang cho viewpager
    private void setupDotLayout(int position) {
        mDotLayout.removeAllViews();

        for (int i = 0; i < mFragment.size(); i++) {
            TextView dot = new TextView(this);
            dot.setTextSize(30);
            dot.setText(Converter.fromHtml("&#8226;"));
            if (i == position)
                dot.setTextColor(ContextCompat.getColor(this, R.color.bgToolbar));
            else dot.setTextColor(ContextCompat.getColor(this, R.color.dotInactive));

            mDotLayout.addView(dot);
        }

    }

    private void setupChiTietSPLayout(List<ChiTietSanPham> chiTietSanPhams) {
        int len = chiTietSanPhams.size();
        for (int i = 0; i < len; i++) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhams.get(i);
            String row = chiTietSanPham.getTen() + ": " + chiTietSanPham.getGiaTri();
            TextView thongso = new TextView(this);
            thongso.setText(row);
            mLayoutThongSoKyThuat.addView(thongso);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.chitietsanpham_btn_yeuthich:
                mIsFavorite = !mIsFavorite;
                setImageResourceButton(id);
                break;
            case R.id.chitietsanpham_btn_xemthem_thongtin:
                mIsExpanded = !mIsExpanded;
                setImageResourceButton(id);
                break;
            case R.id.chitietsanpham_tv_danhgia:
                Intent danhGia = new Intent(this, DanhGiaActivity.class);
                danhGia.putExtra("MASP", masp);
                startActivity(danhGia);
                break;
        }
    }

    private void setImageResourceButton(int id) {
        switch (id) {
            case R.id.chitietsanpham_btn_yeuthich:
                if (mIsFavorite)
                    mButtonFavorite.setImageResource(R.drawable.ic_favorite_orange_24dp);
                else
                    mButtonFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                break;
            case R.id.chitietsanpham_btn_xemthem_thongtin:
                if (mIsExpanded) {
                    mButtonXemThemThongTin.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    mTVThongTinChiTiet.setText((String) mButtonXemThemThongTin.getTag());
                    mLayoutThongSoKyThuat.setVisibility(View.VISIBLE);
                } else {
                    mButtonXemThemThongTin.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    mTVThongTinChiTiet.setText(((String) mButtonXemThemThongTin.getTag()).substring(0, 100));
                    mLayoutThongSoKyThuat.setVisibility(View.GONE);
                }
                break;
        }

    }
}
