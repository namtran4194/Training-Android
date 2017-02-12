package com.example.demomvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demomvp.XuLyDangNhap.ViewXuLyDangNhap;
import com.example.demomvp.XuLyDangNhap.PresenterXuLyDangNhap;

public class MainActivity extends AppCompatActivity implements ViewXuLyDangNhap {

    EditText etTenDangNhap, etMatKhau;
    Button btnDongY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTenDangNhap = (EditText) findViewById(R.id.etTenDangNhap);
        etMatKhau = (EditText) findViewById(R.id.etMatKhau);
        btnDongY = (Button) findViewById(R.id.btnDongY);

        final PresenterXuLyDangNhap xuLyDangNhap = new PresenterXuLyDangNhap(this);

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDangNhap.kiemTraDangNhap(etTenDangNhap.getText().toString(), etMatKhau.getText().toString());
            }
        });
    }

    @Override
    public void dangNhapThanhCong() {
        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dangNhapThatBai() {
        Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
    }
}
