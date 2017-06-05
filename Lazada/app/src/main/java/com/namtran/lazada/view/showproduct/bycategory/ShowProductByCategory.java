package com.namtran.lazada.view.showproduct.bycategory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.namtran.lazada.adapter.TopProductAdapter;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.signin_signup.SignInModel;
import com.namtran.lazada.model.objectclass.OnLoadMoreListener;
import com.namtran.lazada.model.objectclass.OnScrollRecyclerListener;
import com.namtran.lazada.presenter.showproduct.bycategory.ShowProductByCategoryPresenter;
import com.namtran.lazada.presenter.showproduct.productdetail.ProductDetailPresenter;
import com.namtran.lazada.presenter.home.handlingmenu.HandlingMenuPresenter;
import com.namtran.lazada.view.signin_signup.SignInAndSignUpActivity;
import com.namtran.lazada.view.home.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by namtr on 5/14/2017.
 */

public class ShowProductByCategory extends AppCompatActivity implements ShowProductByCategoryView, View.OnClickListener, OnLoadMoreListener, GoogleApiClient.OnConnectionFailedListener {
    public static final int REQUEST_CODE_CART = 9;
    private RecyclerView mRecyclerProduct;
    private ImageButton mButtonTypeView;
    private RecyclerView.LayoutManager mLinearLM, mGridLM;
    private TopProductAdapter mVerticalAdapter, mGridViewAdapter;
    private boolean mIsGridview = true;
    private boolean mIsDataNull = true; // true nếu productList = null hoặc size() = 0
    private ShowProductByCategoryPresenter byCategoryPresenter;
    private int mTypeCode;
    private boolean isQueryToBrand;
    private List<Product> productList;
    private OnScrollRecyclerListener mOnScrollListener;
    private ProductDetailPresenter productDetailPresenter;
    private TextView mTVNumOfProductsInCart;
    private HandlingMenuPresenter handlingMenuPresenter;
    private AccessToken mFbToken;
    private GoogleSignInResult mGgToken;
    private Menu mMenu;
    private MenuItem mLoginItem;
    private SignInModel mSignInModel;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_by_category);

        // lấy MALOAISP / MATHUONGHIEU, biến check để xác định mã truyền sang là cái nào và tên loại danh mục sản phẩm
        Intent intent = getIntent();
        mTypeCode = intent.getIntExtra("MALOAI", -1);
        isQueryToBrand = intent.getBooleanExtra("CHECK", false);
        String productName = intent.getStringExtra("TENLOAI");

        Toolbar toolbar = (Toolbar) findViewById(R.id.htsptdm_toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(productName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        handlingMenuPresenter = new HandlingMenuPresenter();
        mSignInModel = new SignInModel();
        mGoogleApiClient = mSignInModel.layGoogleApiClient(this, this);

        // sự kiện scroll RecyclerView
        mOnScrollListener = new OnScrollRecyclerListener(this);

        // 2 loại adapter cho RecyclerView
        mGridLM = new GridLayoutManager(this, 2);
        mLinearLM = new LinearLayoutManager(this);

        mRecyclerProduct = (RecyclerView) findViewById(R.id.htsptdm_recycler_sanpham);
        mButtonTypeView = (ImageButton) findViewById(R.id.htsptdm_btn_kieuxem);
        mButtonTypeView.setOnClickListener(this);

        byCategoryPresenter = new ShowProductByCategoryPresenter(this);
        byCategoryPresenter.layDanhSachSanPham(mTypeCode, isQueryToBrand);
        productDetailPresenter = new ProductDetailPresenter();

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        mLoginItem = menu.findItem(R.id.menu_login);
        MenuItem item = menu.findItem(R.id.menu_cart);
        View actionLayout = item.getActionView();
        mTVNumOfProductsInCart = (TextView) actionLayout.findViewById(R.id.item_cart_tv_numberOfItems);

        capNhatGioHang();
        capNhatMenu(menu);
        // lưu menu hiện tại
        this.mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
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
        mFbToken = handlingMenuPresenter.layTokenNguoiDungFB();
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
            mTVNumOfProductsInCart.setVisibility(View.GONE);
        else
            mTVNumOfProductsInCart.setVisibility(View.VISIBLE);
        mTVNumOfProductsInCart.setText(String.valueOf(soLuong));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CART) {
            capNhatGioHang();
        }
    }

    @Override
    public void hienThiDanhSachSanPham(List<Product> productList) {
        if (productList != null) {
            this.productList = productList;
            mIsDataNull = false;
            // khởi tạo adapter
            mGridViewAdapter = new TopProductAdapter(this, R.layout.custom_recycler_electronics_gridview_top_product, this.productList);
            mVerticalAdapter = new TopProductAdapter(this, R.layout.custom_recycler_electronics_vertical_top_product, this.productList);

            mRecyclerProduct.setLayoutManager(getLayoutManager());
            mRecyclerProduct.setAdapter(getAdapter());

            mOnScrollListener.setmLayoutManager(getLayoutManager());
            mRecyclerProduct.addOnScrollListener(mOnScrollListener);
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

    private TopProductAdapter getAdapter() {
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
                    mRecyclerProduct.setLayoutManager(getLayoutManager());
                    mRecyclerProduct.setAdapter(getAdapter());
                    // tạo mới Listener để truyền LayoutManager khác, cần xóa Listener hiện tại của View
                    mOnScrollListener.setmLayoutManager(getLayoutManager());
                    mRecyclerProduct.clearOnScrollListeners();
                    mRecyclerProduct.addOnScrollListener(mOnScrollListener);
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
        productList.add(null);
        mRecyclerProduct.post(new Runnable() {
            @Override
            public void run() {
                getAdapter().notifyItemInserted(productList.size() - 1);
                // xóa loading item
                productList.remove(productList.size() - 1);
                getAdapter().notifyItemRemoved(productList.size());

                final List<Product> productList = byCategoryPresenter.loadMore(mTypeCode, isQueryToBrand, totalItems);
                if (productList != null && productList.size() > 0) {
                    ShowProductByCategory.this.productList.addAll(productList);
                    getAdapter().notifyDataSetChanged();
                    mOnScrollListener.setLoaded();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
    }
}
