package com.namtran.lazadaapp.model.trangchu.xulymenu;

import android.util.Log;

import com.namtran.lazadaapp.model.objectclass.LoaiSanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
}
