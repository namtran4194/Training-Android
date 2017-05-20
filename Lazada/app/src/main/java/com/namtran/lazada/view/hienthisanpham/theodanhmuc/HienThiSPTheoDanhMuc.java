package com.namtran.lazada.view.hienthisanpham.theodanhmuc;

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
import com.namtran.lazada.adapter.TopSanPhamAdapter;
import com.namtran.lazada.model.dangnhap_dangky.ModelDangNhap;
import com.namtran.lazada.model.objectclass.OnLoadMoreListener;
import com.namtran.lazada.model.objectclass.OnScrollRecyclerListener;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.presenter.hienthisanpham.chitietsanpham.PresenterChiTietSanPham;
import com.namtran.lazada.presenter.hienthisanpham.theodanhmuc.PresenterHTSPTheoDanhMuc;
import com.namtran.lazada.presenter.trangchu.xulymenu.PresenterXuLyMenu;
import com.namtran.lazada.view.dangnhap_dangky.DangNhapVaDangKyActivity;
import com.namtran.lazada.view.trangchu.TrangChuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by namtr on 5/14/2017.
 */

public class HienThiSPTheoDanhMuc extends AppCompatActivity implements ViewHienThiSPTheoDanhMuc, View.OnClickListener, OnLoadMoreListener, GoogleApiClient.OnConnectionFailedListener {
    public static final int REQUEST_CODE_GIO_HANG = 9;
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
    private PresenterChiTietSanPham mPresenterChiTietSanPham;
    private TextView mTVSoLuongSPTrongGioHang;
    private PresenterXuLyMenu mPresenterXuLyMenu;
    private AccessToken mFbToken;
    private GoogleSignInResult mGgToken;
    private Menu mMenu;
    private MenuItem mLoginItem;
    private ModelDangNhap mModelDangNhap;
    private GoogleApiClient mGoogleApiClient;

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

        mPresenterXuLyMenu = new PresenterXuLyMenu();
        mModelDangNhap = new ModelDangNhap();
        mGoogleApiClient = mModelDangNhap.layGoogleApiClient(this, this);

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
        mPresenterChiTietSanPham = new PresenterChiTietSanPham();

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        mLoginItem = menu.findItem(R.id.menu_login);
        MenuItem item = menu.findItem(R.id.menu_cart);
        View actionLayout = item.getActionView();
        mTVSoLuongSPTrongGioHang = (TextView) actionLayout.findViewById(R.id.item_cart_tv_numberOfItems);

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
                String tenNV = mModelDangNhap.layCacheDangNhap(this);
                if (mFbToken == null && mGgToken == null && tenNV.equals("")) { // chưa đăng nhập
                    Intent loginIntent = new Intent(this, DangNhapVaDangKyActivity.class);
                    startActivityForResult(loginIntent, TrangChuActivity.REQUEST_CODE_LOGIN);
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

        String tenNV = mModelDangNhap.layCacheDangNhap(this);
        // nếu đã đăng nhập thì hiển thị menu đăng xuất
        if (mFbToken != null || mGgToken != null || !tenNV.equals("")) {
            logoutItem.setVisible(true);
        } else {
            logoutItem.setVisible(false);
        }
    }

    // kiểm tra xem có đăng nhập bằng fb ko, nếu có thì hiển thị tên người dùng ra menu
    private void kiemTraDangNhapFB() {
        mFbToken = mPresenterXuLyMenu.layTokenNguoiDungFB();
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
        mGgToken = mModelDangNhap.layKetQuaDangNhapGoogle(mGoogleApiClient);
        if (mGgToken != null && mGgToken.getSignInAccount() != null) {
            String name = mGgToken.getSignInAccount().getDisplayName();
            mLoginItem.setTitle(String.valueOf("Hi, " + name));
        }
    }

    // kiểm tra xem có đăng nhập bằng email ko, nếu có thì hiển thị tên người dùng ra menu
    private void kiemTraDangNhapEmail() {
        String tenNV = mModelDangNhap.layCacheDangNhap(this);
        if (!tenNV.equals("")) {
            mLoginItem.setTitle(String.valueOf("Hi, " + tenNV));
        }
    }

    private void capNhatGioHang() {
        long soLuong = mPresenterChiTietSanPham.soLuongSPCoTrongGioHang(this);
        if (soLuong == 0)
            mTVSoLuongSPTrongGioHang.setVisibility(View.GONE);
        else
            mTVSoLuongSPTrongGioHang.setVisibility(View.VISIBLE);
        mTVSoLuongSPTrongGioHang.setText(String.valueOf(soLuong));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GIO_HANG) {
            capNhatGioHang();
        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
    }
}
