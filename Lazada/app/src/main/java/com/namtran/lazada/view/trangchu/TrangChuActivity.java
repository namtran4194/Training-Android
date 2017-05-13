package com.namtran.lazada.view.trangchu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Visibility;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.namtran.lazada.R;
import com.namtran.lazada.adapter.ExpandableLVAdapter;
import com.namtran.lazada.adapter.ViewPagerAdapterHome;
import com.namtran.lazada.connection.internet.Internet;
import com.namtran.lazada.model.dangnhap_dangky.ModelDangNhap;
import com.namtran.lazada.model.objectclass.LoaiSanPham;
import com.namtran.lazada.presenter.trangchu.xulymenu.PresenterXuLyMenu;
import com.namtran.lazada.view.dangnhap_dangky.DangNhapVaDangKyActivity;
import com.namtran.lazada.view.dangnhap_dangky.fragment.FragmentDangNhap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TrangChuActivity extends AppCompatActivity implements ViewXuLyMenu, GoogleApiClient.OnConnectionFailedListener, AppBarLayout.OnOffsetChangedListener {
    public static final String SERVER_NAME = "http://192.168.1.227:8000/lazada/"; // phải có dấu "/" ở cuối để tự redirect vào index.php
    private static final int REQUEST_CODE_LOGIN = 0;
    private Toolbar mToolbar;
    private TabLayout mTabs;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView mExpandableListView;
    private AccessToken mFbToken;
    private GoogleSignInResult mGgToken;
    private Menu mMenu;
    private MenuItem mMenuLogin;
    private PresenterXuLyMenu mXuLyMenu;
    private ModelDangNhap mModelDangNhap;
    private GoogleApiClient mGoogleApiClient;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private LinearLayout mLayoutSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu_activity);
        Internet internet = new Internet(this);

        init();

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.openDescRes, R.string.closeDescRes);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mXuLyMenu = new PresenterXuLyMenu(this);
        mModelDangNhap = new ModelDangNhap();
        mGoogleApiClient = mModelDangNhap.layGoogleApiClient(this, this);

        if (!internet.isOnline()) {
            Toast.makeText(this, "Không có kết nối mạng!", Toast.LENGTH_LONG).show();
            return;
        }

        mViewPager.setAdapter(new ViewPagerAdapterHome(getSupportFragmentManager()));
        mTabs.setupWithViewPager(mViewPager);

        mXuLyMenu.layDanhSachMenu();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        mTabs = (TabLayout) findViewById(R.id.home_tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.home_viewPager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawerLayout);
        mExpandableListView = (ExpandableListView) findViewById(R.id.home_expandableLV);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.home_collapsingToolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.home_appBar);
        appBarLayout.addOnOffsetChangedListener(this);
        mLayoutSearch = (LinearLayout) findViewById(R.id.home_layout_search);
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
        menu.clear(); // xóa menu hiện tại
        getMenuInflater().inflate(R.menu.home_menu, menu);
        mMenuLogin = menu.findItem(R.id.menu_login);
        MenuItem menuLogout = menu.findItem(R.id.menu_logout);

        // lấy fbToken, ggToken và tenNV trong cache
        mFbToken = mXuLyMenu.layTokenNguoiDungFB();
        mGgToken = mModelDangNhap.layKetQuaDangNhapGoogle(mGoogleApiClient);
        String tenNV = mModelDangNhap.layCacheDangNhap(this);

        // lấy thông tin người dùng từ facebookToken
        if (mFbToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(mFbToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        if (object != null) {
                            String name = object.getString("name");
                            mMenuLogin.setTitle(String.valueOf("Hi, " + name));
                        }
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
        // lấy thông tin người dùng từ googleToken
        else if (mGgToken != null && mGgToken.getSignInAccount() != null) {
            String name = mGgToken.getSignInAccount().getDisplayName();
            mMenuLogin.setTitle(String.valueOf("Hi, " + name));
        }
        // tên người dùng lưu trong cache
        else if (!tenNV.equals("")) {
            mMenuLogin.setTitle(String.valueOf("Hi, " + tenNV));
        }

        if (mFbToken != null || mGgToken != null || !tenNV.equals("")) {
            // hiển thị mMenu Đăng xuất
            menuLogout.setVisible(true);
        }

        // lưu mMenu hiện tại
        this.mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_login:
                String tenNV = mModelDangNhap.layCacheDangNhap(this);
                if (mFbToken == null && mGgToken == null && tenNV.equals("")) { // chưa đăng nhập
                    Intent loginIntent = new Intent(this, DangNhapVaDangKyActivity.class);
                    startActivityForResult(loginIntent, REQUEST_CODE_LOGIN);
                }
                break;
            case R.id.menu_logout:
                // đăng xuất
                if (mFbToken != null) {
                    LoginManager.getInstance().logOut();
                } else if (mGgToken != null) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                } else {
                    // xóa tên người dùng lưu trong cache
                    mModelDangNhap.capNhatCacheDangNhap(this, "");
                }
                Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                this.onCreateOptionsMenu(mMenu);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOGIN) {
            // resultCode từ FragmentDangNhap
            if (resultCode == FragmentDangNhap.LOGIN_WITH_SOCIAL_NETWORK) {
                this.onCreateOptionsMenu(mMenu);
            } else if (resultCode == FragmentDangNhap.LOGIN_WITH_EMAIL) {
                this.onCreateOptionsMenu(mMenu);
            }
        }
    }

    @Override
    public void hienThiDanhSachMenu(List<LoaiSanPham> loaiSanPhams) {
        ExpandableLVAdapter adapter = new ExpandableLVAdapter(this, loaiSanPhams);
        mExpandableListView.setAdapter(adapter);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    // AppBarLayout gồm Toolbar, LinearLayout chứa thanh tìm kiếm và TabLayout
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        // ẩn LinearLayout chứa button tìm kiếm + camera khi scroll lên
        int height = mCollapsingToolbar.getHeight() + verticalOffset;

        // ẩn search bar
        if (height <= ViewCompat.getMinimumHeight(mCollapsingToolbar)) {
            mLayoutSearch.animate()
                    .translationY(mLayoutSearch.getHeight())
                    .alpha(0)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mLayoutSearch.setVisibility(View.INVISIBLE);
                        }
                    });
            MenuItem menuSearch = mMenu.findItem(R.id.menu_seach);
            menuSearch.setVisible(true);
        }
        // hiển thị search bar
        else { // lần đầu chạy app sẽ vào trường hợp này
            mLayoutSearch.animate()
                    .translationY(0)
                    .alpha(1)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mLayoutSearch.setVisibility(View.VISIBLE);
                        }
                    });
            // do ban đầu mMenu = null nên phải kiểm tra
            if (mMenu != null) {
                MenuItem menuSearch = mMenu.findItem(R.id.menu_seach);
                menuSearch.setVisible(false);
            }
        }
    }
}
