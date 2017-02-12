package com.example.demomvp.XuLyDangNhap;

/**
 * Created by namtran on 12/02/2017.
 */

public class PresenterXuLyDangNhap implements PresenterInterfaceXuLyDangNhap {
    ViewXuLyDangNhap xuLyDangNhap;

    public PresenterXuLyDangNhap(ViewXuLyDangNhap xuLyDangNhap){
        this.xuLyDangNhap = xuLyDangNhap;
    }
    @Override
    public void kiemTraDangNhap(String tenDangNhap, String matKhau) {
        // goi toi Model lay du lieu

        if (tenDangNhap.equals("namtran") && matKhau.equals("123")){
            // tra cho View dang nhap thanh cong
            xuLyDangNhap.dangNhapThanhCong();
        }else {
            // tra cho View dang nhap that bai
            xuLyDangNhap.dangNhapThatBai();
        }
    }
}
