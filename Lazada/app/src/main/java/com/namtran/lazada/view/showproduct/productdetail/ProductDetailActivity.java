package com.namtran.lazada.view.showproduct.productdetail;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.namtran.lazada.adapter.CommentAdapter;
import com.namtran.lazada.adapter.PagerProductDetailAdapter;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.signin_signup.SignInModel;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.ProductDetail;
import com.namtran.lazada.model.objectclass.Comment;
import com.namtran.lazada.presenter.showproduct.productdetail.ProductDetailPresenter;
import com.namtran.lazada.presenter.home.handlingmenu.HandlingMenuPresenter;
import com.namtran.lazada.tools.Converter;
import com.namtran.lazada.view.signin_signup.SignInAndSignUpActivity;
import com.namtran.lazada.view.showproduct.productdetail.fragment.ProductDetailFragment;
import com.namtran.lazada.view.showproduct.comment.CommentListActivity;
import com.namtran.lazada.view.showproduct.comment.AddCommentActivity;
import com.namtran.lazada.view.home.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 5/16/2017.
 */

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailView, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private ViewPager mSlider;
    private List<Fragment> mFragment;
    private LinearLayout mDotLayout;
    private TextView mTVProductName, mTVPrice;
    private ImageButton mButtonFavorite;
    private boolean mIsFavorite; // lưu trạng thái mButtonFavorite
    private TextView mTVSeller;
    private TextView mTVDetailInfo;
    private ImageButton mButtonSeeMore;
    private boolean mIsExpanded; // lưu trạng thái mButtonSeeMore
    private LinearLayout mLayoutSpecifications;
    private Product mProductInCart;
    private int productCode;
    private RecyclerView mRecyclerComment;
    private RatingBar mRating;
    private ProductDetailPresenter productDetailPresenter;
    private TextView mTVNumOfProductInCart;
    private HandlingMenuPresenter mHandlingMenuPresenter;
    private AccessToken mFbToken;
    private GoogleSignInResult mGgToken;
    private Menu mMenu;
    private MenuItem mLoginItem;
    private SignInModel mSignInModel;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // initialize
        init();
        mFragment = new ArrayList<>();

        mHandlingMenuPresenter = new HandlingMenuPresenter();
        mSignInModel = new SignInModel();
        mGoogleApiClient = mSignInModel.getApi(this, this);

        // retrieve data
        productCode = getIntent().getIntExtra("MASP", -1);
        productDetailPresenter = new ProductDetailPresenter(this);
        productDetailPresenter.getProductDetail(Action.PRODUCT_DETAIL, productCode);
        productDetailPresenter.getComments(Action.COMMENTARY_LIST, productCode, 0);

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
        mTVProductName = (TextView) findViewById(R.id.chitietsanpham_tv_tensanpham);
        mTVPrice = (TextView) findViewById(R.id.chitietsanpham_tv_gia);
        mButtonFavorite = (ImageButton) findViewById(R.id.chitietsanpham_btn_yeuthich);
        mButtonFavorite.setOnClickListener(this);
        mTVSeller = (TextView) findViewById(R.id.chitietsanpham_tv_nguoiban);
        mTVDetailInfo = (TextView) findViewById(R.id.chitietsanpham_tv_chitietsp);
        mButtonSeeMore = (ImageButton) findViewById(R.id.chitietsanpham_btn_xemthem_thongtin);
        mLayoutSpecifications = (LinearLayout) findViewById(R.id.chitietsanpham_layout_thongsokythuat);

        TextView tvVietDanhGia = (TextView) findViewById(R.id.chitietsanpham_tv_danhgia);
        tvVietDanhGia.setOnClickListener(this);
        mRecyclerComment = (RecyclerView) findViewById(R.id.chitietsanpham_recycler_danhgia);
        Button btnXemTatCaDanhGia = (Button) findViewById(R.id.chitietsanpham_btn_xemtatca_danhgia);
        btnXemTatCaDanhGia.setOnClickListener(this);
        mRating = (RatingBar) findViewById(R.id.chitietsanpham_ratingbar_danhgia);

        ImageButton btnThemGioHang = (ImageButton) findViewById(R.id.chitietsanpham_btn_giohang);
        btnThemGioHang.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        mLoginItem = menu.findItem(R.id.menu_login);
        MenuItem item = menu.findItem(R.id.menu_cart);
        View actionLayout = item.getActionView();
        mTVNumOfProductInCart = (TextView) actionLayout.findViewById(R.id.item_cart_tv_numberOfItems);

        updateCartStatus();
        updateMenu(menu);
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
                String tenNV = mSignInModel.getLoginCache(this);
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
                    mSignInModel.updateLoginCache(this, "");
                }
                Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                updateMenu(mMenu);
                break;
        }
        return true;
    }

    private void updateMenu(Menu menu) {
        MenuItem logoutItem = menu.findItem(R.id.menu_logout);

        checkingFacebookLogin();
        checkingGoogleLogin();
        checkingEmailLogin();

        String tenNV = mSignInModel.getLoginCache(this);
        // nếu đã đăng nhập thì hiển thị menu đăng xuất
        if (mFbToken != null || mGgToken != null || !tenNV.equals("")) {
            logoutItem.setVisible(true);
        } else {
            logoutItem.setVisible(false);
        }
    }

    // kiểm tra xem có đăng nhập bằng fb ko, nếu có thì hiển thị tên người dùng ra menu
    private void checkingFacebookLogin() {
        mFbToken = mHandlingMenuPresenter.getFacebookAccessToken();
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
    private void checkingGoogleLogin() {
        mGgToken = mSignInModel.getResult(mGoogleApiClient);
        if (mGgToken != null && mGgToken.getSignInAccount() != null) {
            String name = mGgToken.getSignInAccount().getDisplayName();
            mLoginItem.setTitle(String.valueOf("Hi, " + name));
        }
    }

    // kiểm tra xem có đăng nhập bằng email ko, nếu có thì hiển thị tên người dùng ra menu
    private void checkingEmailLogin() {
        String tenNV = mSignInModel.getLoginCache(this);
        if (!tenNV.equals("")) {
            mLoginItem.setTitle(String.valueOf("Hi, " + tenNV));
        }
    }

    private void updateCartStatus() {
        long soLuong = productDetailPresenter.numOfProductsInCart(this);
        if (soLuong == 0)
            mTVNumOfProductInCart.setVisibility(View.GONE);
        else
            mTVNumOfProductInCart.setVisibility(View.VISIBLE);
        mTVNumOfProductInCart.setText(String.valueOf(soLuong));
    }

    @Override
    public void showProductDetail(Product product) {
        if (product != null) {
            mProductInCart = product; // lưu sản phẩm hiện tại để xử lý khi thêm vào giỏ hàng
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(product.getProductName());
            }

            // hiển thị thông tin sản phẩm
            mTVProductName.setText(product.getProductName());
            mTVPrice.setText(Converter.formatCurrency(product.getPrice()));
            mTVSeller.setText(product.getStaffName());

            // hiển thị chi tiết sản phẩm, mặc định cho hiển thị 100 ký tự
            String chitietsanpham = product.getInfomation();
            mTVDetailInfo.setText(chitietsanpham.substring(0, 100));

            // setup button xem thêm
            if (chitietsanpham.length() <= 100) mButtonSeeMore.setVisibility(View.GONE);
            else {
                mButtonSeeMore.setVisibility(View.VISIBLE);
                mButtonSeeMore.setTag(chitietsanpham);
                mButtonSeeMore.setOnClickListener(this);
            }

            setupProductDetailLayout(product.getProductDetails());
        }
    }

    @Override
    public void showPicturePreview(String... links) {
        if (links != null && links.length > 0) {
            List<Fragment> fragments = new ArrayList<>();
            for (String aLink : links) {
                ProductDetailFragment chiTietSP = new ProductDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("LINKANH", HomeActivity.SERVER_NAME + aLink);
                chiTietSP.setArguments(bundle);
                fragments.add(chiTietSP);
            }
            PagerProductDetailAdapter adapter = new PagerProductDetailAdapter(getSupportFragmentManager(), fragments);
            mSlider.setAdapter(adapter);

            mFragment = fragments;
        }
    }

    @Override
    public void showComments(List<Comment> commentList) {
        if (commentList != null && commentList.size() > 0) {
            CommentAdapter adapter = new CommentAdapter(this, commentList, 3);
            mRecyclerComment.setNestedScrollingEnabled(false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerComment.setLayoutManager(layoutManager);
            mRecyclerComment.setAdapter(adapter);

            // tính toán số sao cho ratingbar
            float rating = 0.0f;
            for (int i = 0; i < commentList.size(); i++) {
                rating += commentList.get(i).getNumOfStars();
            }
            rating /= commentList.size();
            mRating.setRating(rating);
        }
    }

    @Override
    public void addToCartResult(boolean result) {
        if (result) {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            // cập nhật lại số sản phẩm trong giỏ hàng của trên menu
            long soLuong = productDetailPresenter.numOfProductsInCart(this);
            if (soLuong == 0)
                mTVNumOfProductInCart.setVisibility(View.GONE);
            else
                mTVNumOfProductInCart.setVisibility(View.VISIBLE);
            mTVNumOfProductInCart.setText(String.valueOf(soLuong));
        } else Toast.makeText(this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
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

    private void setupProductDetailLayout(List<ProductDetail> productDetails) {
        int len = productDetails.size();
        for (int i = 0; i < len; i++) {
            ProductDetail productDetail = productDetails.get(i);
            String row = productDetail.getKey() + ": " + productDetail.getValue();
            TextView thongso = new TextView(this);
            thongso.setText(row);
            mLayoutSpecifications.addView(thongso);
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
                Intent danhGia = new Intent(this, AddCommentActivity.class);
                danhGia.putExtra("MASP", productCode);
                startActivity(danhGia);
                break;
            case R.id.chitietsanpham_btn_xemtatca_danhgia:
                Intent danhSachDanhGia = new Intent(this, CommentListActivity.class);
                danhSachDanhGia.putExtra("MASP", productCode);
                startActivity(danhSachDanhGia);
                break;
            case R.id.chitietsanpham_btn_giohang:
                Fragment fragment = mFragment.get(mSlider.getCurrentItem());
                View view = fragment.getView();
                BitmapDrawable drawable;
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.fragment_chitietsanpham_hinhanh);
                    drawable = (BitmapDrawable) imageView.getDrawable();
                } else {
                    // lấy hình mặc định
                    drawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.ic_image_black_24dp);
                }
                byte[] hinhAnh = Converter.drawableToByteArray(drawable);
                mProductInCart.setImage(hinhAnh);
                productDetailPresenter.addToCart(this, mProductInCart);
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
                    mButtonSeeMore.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    mTVDetailInfo.setText((String) mButtonSeeMore.getTag());
                    mLayoutSpecifications.setVisibility(View.VISIBLE);
                } else {
                    mButtonSeeMore.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    mTVDetailInfo.setText(((String) mButtonSeeMore.getTag()).substring(0, 100));
                    mLayoutSpecifications.setVisibility(View.GONE);
                }
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
    }
}
