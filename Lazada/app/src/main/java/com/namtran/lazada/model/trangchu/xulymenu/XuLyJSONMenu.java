package com.namtran.lazada.model.trangchu.xulymenu;

import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.objectclass.LoaiSanPham;
import com.namtran.lazada.view.trangchu.TrangChuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 26/04/2017.
 */

public class XuLyJSONMenu {
    // lấy danh sách các loại sản phẩm từ chuỗi JSON
    public List<LoaiSanPham> parserJSONMenu(String jsonData) {
        List<LoaiSanPham> datas = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONArray("LOAISANPHAM");
            int count = array.length();
            for (int i = 0; i < count; i++) {
                JSONObject value = array.getJSONObject(i);

                LoaiSanPham loaiSanPham = new LoaiSanPham();
                loaiSanPham.setMaLoaiSP(Integer.parseInt(value.getString("MALOAISP")));
                loaiSanPham.setMaLoaiCha(Integer.parseInt(value.getString("MALOAI_CHA")));
                loaiSanPham.setTenLoaiSP(value.getString("TENLOAISP"));

                datas.add(loaiSanPham);
            }
        } catch (JSONException e) {
            return null;
        }
        return datas;
    }

    // lấy danh sách các loại sản phẩm theo mã loại cha
    public List<LoaiSanPham> getTypeOfProductById(int parentId) {
        List<LoaiSanPham> loaiSanPhams;
        List<HashMap<String, String>> attrs = new ArrayList<>(); // các tham số truyền theo request

        // POST method
        String url = TrangChuActivity.SERVER_NAME + "danhsachmenu.php";

//        HashMap<String, String> function = new HashMap<>();
//        function.put("function", "getListMenu");
//        attrs.add(function);

        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("parentCode", String.valueOf(parentId));
        attrs.add(maLoaiCha);
        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);
        // End POST

        downloadJSON.execute();
        try {
            String jsonData = downloadJSON.get();
            XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();
            loaiSanPhams = xuLyJSONMenu.parserJSONMenu(jsonData);
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
        return loaiSanPhams;
    }
}
