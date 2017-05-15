package com.namtran.lazada.presenter.trangchu.xulymenu;

import com.facebook.AccessToken;
import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.dangnhap_dangky.ModelDangNhap;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.LoaiSanPham;
import com.namtran.lazada.model.trangchu.xulymenu.ModelXuLyJSONMenu;
import com.namtran.lazada.view.trangchu.TrangChuActivity;
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

        Action action = Action.DANH_SACH_MENU;
        String url = TrangChuActivity.SERVER_NAME + action.getAction();
        if (!action.getAction().contains(".php"))
            url += ".php";

        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("parentCode", "0");
        attrs.add(maLoaiCha);

        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);
        downloadJSON.execute();

        try {
            String jsonData = downloadJSON.get();
            ModelXuLyJSONMenu xuLyJSONMenu = new ModelXuLyJSONMenu();
            loaiSanPhams = xuLyJSONMenu.parserJSONMenu(jsonData);
            if (loaiSanPhams != null && loaiSanPhams.size() > 0)
                viewXuLyMenu.hienThiDanhSachMenu(loaiSanPhams); // trả danh sách các loại sản phẩm cho view
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccessToken layTokenNguoiDungFB() {
        ModelDangNhap modelDangNhap = new ModelDangNhap();
        return modelDangNhap.layAccessTokenFB();
    }
}
