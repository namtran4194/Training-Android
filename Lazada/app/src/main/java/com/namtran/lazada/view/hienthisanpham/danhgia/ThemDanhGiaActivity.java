package com.namtran.lazada.view.hienthisanpham.danhgia;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.DanhGia;
import com.namtran.lazada.presenter.hienthisanpham.danhgia.PresenterDanhGia;

import java.util.List;

/**
 * Created by namtr on 5/19/2017.
 */

public class ThemDanhGiaActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener, View.OnClickListener, ViewDanhGia {
    private TextInputEditText mETTieuDe, mETNoiDung;
    private Button mButtonDongY;
    private int soSao;
    private int masp;
    private PresenterDanhGia presenterDanhGia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themdanhgia);

        init();
        masp = getIntent().getIntExtra("MASP", -1);
        presenterDanhGia = new PresenterDanhGia(this);
    }

    private void init() {
        mETTieuDe = (TextInputEditText) findViewById(R.id.danhgia_et_tieude);
        mETNoiDung = (TextInputEditText) findViewById(R.id.danhgia_et_noidung);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.danhgia_rb_sosao);
        ratingBar.setOnRatingBarChangeListener(this);
        mButtonDongY = (Button) findViewById(R.id.danhgia_btn_dongy);
        mButtonDongY.setOnClickListener(this);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        soSao = (int) rating;
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonDongY) {
            thucHienThemDG();
        }
    }

    private void thucHienThemDG() {
        DanhGia danhGia = new DanhGia();
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String madg = manager.getDeviceId();
        String tenThietBi = Build.MODEL;

        String tieuDe = mETTieuDe.getText().toString().trim();
        String noiDung = mETNoiDung.getText().toString().trim();

        View vTieuDe = (View) mETTieuDe.getParentForAccessibility();
        View vNoiDung = (View) mETNoiDung.getParentForAccessibility();

        boolean isValid = true;
        if (tieuDe.length() > 0) {
            if (vTieuDe instanceof TextInputLayout)
                ((TextInputLayout) vTieuDe).setError("");
        } else {
            isValid = false;
            if (vTieuDe instanceof TextInputLayout)
                ((TextInputLayout) vTieuDe).setError("Bạn chưa nhập tiêu đề");
            else Toast.makeText(this, "Bạn chưa nhập tiêu đề", Toast.LENGTH_SHORT).show();
        }

        if (noiDung.length() > 0) {
            if (vNoiDung instanceof TextInputLayout)
                ((TextInputLayout) vNoiDung).setError("");
        } else {
            isValid = false;
            if (vNoiDung instanceof TextInputLayout)
                ((TextInputLayout) vNoiDung).setError("Bạn chưa nhập nội dung");
            else Toast.makeText(this, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
        }

        if (isValid) {
            danhGia.setMaDG(madg);
            danhGia.setMaSP(masp);
            danhGia.setTenThietBi(tenThietBi);
            danhGia.setTieuDe(tieuDe);
            danhGia.setNoiDung(noiDung);
            danhGia.setSoSao(soSao);
            presenterDanhGia.themDanhGia(danhGia);
        }
    }

    @Override
    public void ketQuaThemDanhGia(boolean result) {
        if (result) {
            Toast.makeText(this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else
            Toast.makeText(this, "Đánh giá thất bại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hienThiDanhSachDanhGia(List<DanhGia> danhGias) {

    }
}
