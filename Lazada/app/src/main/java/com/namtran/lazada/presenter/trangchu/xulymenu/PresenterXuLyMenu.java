package com.namtran.lazada.presenter.trangchu.xulymenu;

import com.facebook.AccessToken;
import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.dangnhap.XuLyDangNhap;
import com.namtran.lazada.model.objectclass.LoaiSanPham;
import com.namtran.lazada.model.trangchu.xulymenu.XuLyJSONMenu;
import com.namtran.lazada.view.trangchu.ViewXuLyMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 26/04/2017.
 */

public class PresenterXuLyMenu implements IPresenterXuLyMenu {
    private ViewXuLyMenu viewXuLyMenu;

    public PresenterXuLyMenu(ViewXuLyMenu viewXuLyMenu) {
        this.viewXuLyMenu = viewXuLyMenu;
    }

    @Override
    public void layDanhSachMenu() {
        List<LoaiSanPham> loaiSanPhams;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        /* GET method
        String url = "http://192.168.43.23:8000/lazada/loaisanpham.php?maloaicha=1";
        DownloadJSON downloadJSON = new DownloadJSON(url);
         End GET*/

        // POST method
        String url = "http://192.168.1.227:8000/lazada/loaisanpham.php";
        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("maloaicha", "0");
        attrs.add(maLoaiCha);
        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);
        // End POST

        downloadJSON.execute();
        try {
            String jsonData = downloadJSON.get();
            XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();
            loaiSanPhams = xuLyJSONMenu.parserJSONMenu(jsonData);
            viewXuLyMenu.hienThiDanhSachMenu(loaiSanPhams); // trả danh sách các loại sản phẩm cho view
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccessToken layTokenNguoiDungFB() {
        XuLyDangNhap xuLyDangNhap = new XuLyDangNhap();
        return xuLyDangNhap.getCurrentFBAccessToken();
    }
}
