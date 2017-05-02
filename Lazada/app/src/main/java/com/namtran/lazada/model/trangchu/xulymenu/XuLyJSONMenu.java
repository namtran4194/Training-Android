package com.namtran.lazada.model.trangchu.xulymenu;

import android.util.Log;

import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.objectclass.LoaiSanPham;

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
    private static final String TAG = "XuLyJSONMenu";

    public List<LoaiSanPham> parserJSONMenu(String jsonData) {
        List<LoaiSanPham> datas = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONArray("LOAISANPHAM");
            Log.d(TAG, array.toString()); // Logging
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
            e.printStackTrace();
        }
        return datas;
    }

    public List<LoaiSanPham> getTypeOfProductById(int parentId) {
        List<LoaiSanPham> loaiSanPhams = new ArrayList<>();
        List<HashMap<String, String>> attrs = new ArrayList<>();

        String url = "http://192.168.43.24:8000/lazada/loaisanpham.php";
        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("maloaicha", String.valueOf(parentId));
        attrs.add(maLoaiCha);
        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);
        downloadJSON.execute();
        try {
            String jsonData = downloadJSON.get();
            XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();
            loaiSanPhams = xuLyJSONMenu.parserJSONMenu(jsonData);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return loaiSanPhams;
    }
}
