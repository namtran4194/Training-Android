package com.namtran.lazada.model.trangchu.xulymenu;

import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.objectclass.Action;
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

public class ModelXuLyJSONMenu {
    // lấy danh sách các loại sản phẩm từ chuỗi JSON
    public List<LoaiSanPham> parserJSONMenu(String jsonData) {
        if (jsonData == null) return null;

        List<LoaiSanPham> datas = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            Action action = Action.DANH_SACH_MENU;
            JSONArray array = jsonObject.getJSONArray(action.getNodeName());
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
        List<LoaiSanPham> loaiSanPhams = new ArrayList<>();
        List<HashMap<String, String>> attrs = new ArrayList<>(); // các tham số truyền theo request

        Action action = Action.DANH_SACH_MENU;
        String url = TrangChuActivity.SERVER_NAME + action.getAction();
        if (!action.getAction().contains(".php"))
            url += ".php";

        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("parentCode", String.valueOf(parentId));
        attrs.add(maLoaiCha);
        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);

        downloadJSON.execute();
        try {
            String json = downloadJSON.get();
            if (json != null)
                loaiSanPhams = parserJSONMenu(json);
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
        return loaiSanPhams;
    }
}
