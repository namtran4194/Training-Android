package com.namtran.lazada.view.trangchu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.ExpandableLVAdapter;
import com.namtran.lazada.adapter.ViewPagerAdapterHome;
import com.namtran.lazada.model.objectclass.LoaiSanPham;
import com.namtran.lazada.presenter.trangchu.xulymenu.PresenterXuLyMenu;
import com.namtran.lazada.view.dangnhap.DangNhapActivity;

import java.util.List;

public class TrangChuActivity extends AppCompatActivity implements ViewXuLyMenu {
    private Toolbar mToolbar;
    private TabLayout mTabs;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu_activity);

        init();

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mViewPager.setAdapter(new ViewPagerAdapterHome(getSupportFragmentManager()));
        mTabs.setupWithViewPager(mViewPager);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.openDescRes, R.string.closeDescRes);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        PresenterXuLyMenu xuLyMenu = new PresenterXuLyMenu(this);
        xuLyMenu.layDanhSachMenu();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        mTabs = (TabLayout) findViewById(R.id.home_tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.home_viewPager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawerLayout);
        mExpandableListView = (ExpandableListView) findViewById(R.id.home_expandableLV);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        mDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_login:
                Intent loginIntent = new Intent(this, DangNhapActivity.class);
                startActivity(loginIntent);
        }
        return true;
    }

    @Override
    public void hienThiDanhSachMenu(List<LoaiSanPham> loaiSanPhams) {
        ExpandableLVAdapter adapter = new ExpandableLVAdapter(this, loaiSanPhams);
        mExpandableListView.setAdapter(adapter);
    }
}
