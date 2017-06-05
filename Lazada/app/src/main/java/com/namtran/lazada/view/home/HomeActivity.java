package com.namtran.lazada.view.home;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.namtran.lazada.adapter.ExpandableListViewAdapter;
import com.namtran.lazada.adapter.PagerHomeAdapter;
import com.namtran.lazada.connection.internet.Internet;
import com.namtran.lazada.model.signin_signup.SignInModel;
import com.namtran.lazada.model.objectclass.ProductType;
import com.namtran.lazada.presenter.showproduct.productdetail.ProductDetailPresenter;
import com.namtran.lazada.presenter.home.handlingmenu.HandlingMenuPresenter;
import com.namtran.lazada.view.signin_signup.SignInAndSignUpActivity;
import com.namtran.lazada.view.signin_signup.fragment.SignInFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HandlingMenuView, GoogleApiClient.OnConnectionFailedListener, AppBarLayout.OnOffsetChangedListener {
    public static final String SERVER_NAME = "http://192.168.1.227:8000/lazada/";
    public static final int REQUEST_CODE_LOGIN = 0;
    public static final int REQUEST_CODE_CART = 9;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView mExpandableListView;
    private HandlingMenuPresenter mHandlingMenuPresenter;
    private TextView mTVNumOfProductInCart;
    private AccessToken mFbToken;
    private GoogleSignInResult mGgToken;
    private Menu mMenu;
    private MenuItem mLoginItem;
    private SignInModel mSignInModel;
    private GoogleApiClient mGoogleApiClient;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private LinearLayout mLayoutSearch;
    private ProductDetailPresenter productDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.openDescRes, R.string.closeDescRes);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mHandlingMenuPresenter = new HandlingMenuPresenter(this);
        mSignInModel = new SignInModel();
        mGoogleApiClient = mSignInModel.layGoogleApiClient(this, this);

        Internet internet = new Internet(this);
        if (!internet.isOnline()) {
            Toast.makeText(this, "Không có kết nối mạng!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!internet.isServerReachable()) {
            Toast.makeText(this, "Không kết nối được đến server!", Toast.LENGTH_LONG).show();
            return;
        }

        mViewPager.setAdapter(new PagerHomeAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        mHandlingMenuPresenter.layDanhSachMenu();
        productDetailPresenter = new ProductDetailPresenter();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.home_tabLayout);
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
        getMenuInflater().inflate(R.menu.home_menu, menu);
        mLoginItem = menu.findItem(R.id.menu_login);
        MenuItem item = menu.findItem(R.id.menu_cart);
        View actionLayout = item.getActionView();
        mTVNumOfProductInCart = (TextView) actionLayout.findViewById(R.id.item_cart_tv_numberOfItems);

        capNhatGioHang();
        capNhatMenu(menu);
        // lưu menu hiện tại
        this.mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_login:
                String tenNV = mSignInModel.layCacheDangNhap(this);
                if (mFbToken == null && mGgToken == null && tenNV.equals("")) { // chưa đăng nhập
                    Intent loginIntent = new Intent(this, SignInAndSignUpActivity.class);
                    startActivityForResult(loginIntent, HomeActivity.REQUEST_CODE_LOGIN);
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
                    mSignInModel.capNhatCacheDangNhap(this, "");
                }
                Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                capNhatMenu(mMenu);
                break;
        }
        return true;
    }

    private void capNhatMenu(Menu menu) {
        MenuItem logoutItem = menu.findItem(R.id.menu_logout);

        kiemTraDangNhapFB();
        kiemTraDangNhapGG();
        kiemTraDangNhapEmail();

        String tenNV = mSignInModel.layCacheDangNhap(this);
        // nếu đã đăng nhập thì hiển thị menu đăng xuất
        if (mFbToken != null || mGgToken != null || !tenNV.equals("")) {
            logoutItem.setVisible(true);
        } else {
            logoutItem.setVisible(false);
        }
    }

    // kiểm tra xem có đăng nhập bằng fb ko, nếu có thì hiển thị tên người dùng ra menu
    private void kiemTraDangNhapFB() {
        mFbToken = mHandlingMenuPresenter.layTokenNguoiDungFB();
        // lấy thông tin người dùng từ facebookToken
        if (mFbToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(mFbToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        if (object != null) {
                            String name = object.getString("name");
                            mLoginItem.setTitle(String.valueOf("Hi, " + name));
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
    }

    // kiểm tra xem có đăng nhập bằng gg ko, nếu có thì hiển thị tên người dùng ra menu
    private void kiemTraDangNhapGG() {
        mGgToken = mSignInModel.layKetQuaDangNhapGoogle(mGoogleApiClient);
        if (mGgToken != null && mGgToken.getSignInAccount() != null) {
            String name = mGgToken.getSignInAccount().getDisplayName();
            mLoginItem.setTitle(String.valueOf("Hi, " + name));
        }
    }

    // kiểm tra xem có đăng nhập bằng email ko, nếu có thì hiển thị tên người dùng ra menu
    private void kiemTraDangNhapEmail() {
        String tenNV = mSignInModel.layCacheDangNhap(this);
        if (!tenNV.equals("")) {
            mLoginItem.setTitle(String.valueOf("Hi, " + tenNV));
        }
    }

    private void capNhatGioHang() {
        long soLuong = productDetailPresenter.soLuongSPCoTrongGioHang(this);
        if (soLuong == 0)
            mTVNumOfProductInCart.setVisibility(View.GONE);
        else
            mTVNumOfProductInCart.setVisibility(View.VISIBLE);
        mTVNumOfProductInCart.setText(String.valueOf(soLuong));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOGIN) {
            // resultCode từ SignInFragment
            if (resultCode == SignInFragment.LOGIN_WITH_SOCIAL_NETWORK) {
                capNhatMenu(mMenu);
            } else if (resultCode == SignInFragment.LOGIN_WITH_EMAIL) {
                capNhatMenu(mMenu);
            }
        } else if (requestCode == REQUEST_CODE_CART) {
            capNhatGioHang();
        }
    }

    @Override
    public void hienThiDanhSachMenu(List<ProductType> productTypes) {
        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(this, productTypes);
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
