package com.namtran.lazada.view.hienthisanpham.chitietsanpham;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.ViewPagerCTSPAdapter;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.presenter.hienthisanpham.chitietsanpham.PresenterChiTietSanPham;
import com.namtran.lazada.tools.Converter;
import com.namtran.lazada.view.hienthisanpham.chitietsanpham.fragment.FragmentChiTietSP;
import com.namtran.lazada.view.trangchu.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 5/16/2017.
 */

public class ChiTietSanPhamActivity extends AppCompatActivity implements ViewChiTietSanPham {
    private ViewPager mSlider;
    private List<Fragment> mFragment;
    private LinearLayout mDotLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        // initialize
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
                setUpDotLayout(position);
            }
        });
        mDotLayout = (LinearLayout) findViewById(R.id.chitietsanpham_layout_dot);
        mFragment = new ArrayList<>();

        // retrieve data
        int masp = getIntent().getIntExtra("MASP", -1);
        PresenterChiTietSanPham presenterChiTiet = new PresenterChiTietSanPham(this);
        presenterChiTiet.layChiTietSanPham(Action.CHI_TIET_SAN_PHAM, masp);

        setUpDotLayout(0);
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
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(sanPham.getTenSP());
            }

            // hiển thị thông tin sản phẩm
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
    private void setUpDotLayout(int position) {
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
}
