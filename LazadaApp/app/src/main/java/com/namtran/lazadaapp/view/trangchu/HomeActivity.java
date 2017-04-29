package com.namtran.lazadaapp.view.trangchu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.namtran.lazadaapp.R;
import com.namtran.lazadaapp.adapter.ViewPagerAdapter;
import com.namtran.lazadaapp.model.objectclass.LoaiSanPham;
import com.namtran.lazadaapp.presenter.trangchu.xulymenu.PresenterXuLyMenu;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements ViewXuLyMenu {
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        init();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewPager);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDescRes, R.string.closeDescRes);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        PresenterXuLyMenu xuLyMenu = new PresenterXuLyMenu(this);
        xuLyMenu.layDanhSachMenu();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        return true;
    }

    @Override
    public void hienThiDanhSachMenu(List<LoaiSanPham> loaiSanPhams) {
        Toast.makeText(this, "HomeActivity: " + loaiSanPhams.size(), Toast.LENGTH_SHORT).show();
    }
}
