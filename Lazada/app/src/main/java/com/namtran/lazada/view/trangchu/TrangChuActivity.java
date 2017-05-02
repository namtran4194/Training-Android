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

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.namtran.lazada.R;
import com.namtran.lazada.adapter.ExpandableLVAdapter;
import com.namtran.lazada.adapter.ViewPagerAdapterHome;
import com.namtran.lazada.model.objectclass.LoaiSanPham;
import com.namtran.lazada.presenter.trangchu.xulymenu.PresenterXuLyMenu;
import com.namtran.lazada.view.dangnhap.DangNhapActivity;
import com.namtran.lazada.view.dangnhap.fragment.FragmentDangNhap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TrangChuActivity extends AppCompatActivity implements ViewXuLyMenu {
    private static final int REQUEST_CODE_LOGIN = 0;
    private Toolbar mToolbar;
    private TabLayout mTabs;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView mExpandableListView;
    private AccessToken token;
    private Menu menu;
    private PresenterXuLyMenu xuLyMenu;

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

        xuLyMenu = new PresenterXuLyMenu(this);
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.home_menu, menu);

        // lấy token đăng nhập fb
        token = xuLyMenu.layTokenNguoiDungFB();

        // lấy thông tin người dùng từ token nhận được
        if (token != null) {
            GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        String name = object.getString("name");
                        MenuItem item = menu.findItem(R.id.menu_login);
                        item.setTitle(String.valueOf("Hi, " + name));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            // truyền các tham số cần lấy
            Bundle parameter = new Bundle();
            parameter.putString("fields", "name");
            request.setParameters(parameter);
            request.executeAsync();
        }
        if (token != null) {
            // hiển thị menu Đăng xuất
            MenuItem item = menu.findItem(R.id.menu_logout);
            item.setVisible(true);
        }

        // lưu menu hiện tại
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_login:
                if (token == null) { // chưa đăng nhập
                    Intent loginIntent = new Intent(this, DangNhapActivity.class);
                    startActivityForResult(loginIntent, REQUEST_CODE_LOGIN);
                }
                break;
            case R.id.menu_logout:
                // đăng xuất
                LoginManager.getInstance().logOut();
                this.onCreateOptionsMenu(menu);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOGIN) {
            // resultCode từ FragmentDangNhap
            if (resultCode == FragmentDangNhap.RESULT_CODE_LOGIN) {
                this.onCreateOptionsMenu(menu);
            }
        }
    }

    @Override
    public void hienThiDanhSachMenu(List<LoaiSanPham> loaiSanPhams) {
        ExpandableLVAdapter adapter = new ExpandableLVAdapter(this, loaiSanPhams);
        mExpandableListView.setAdapter(adapter);
    }
}
