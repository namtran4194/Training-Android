package com.namtran.lazada.model.hienthisanpham.theodanhmuc;

import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.view.trangchu.TrangChuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 5/14/2017.
 */

public class ModelHTSPTheoDanhMuc {

    public List<SanPham> layDanhSachSanPhamTheoMaThuongHieu(Action action, int brandCode, int startIndex) {
        String url = TrangChuActivity.SERVER_NAME + action.getAction();
        if (!action.getAction().contains(".php"))
            url += ".php";

        List<SanPham> sanPhamList = new ArrayList<>();
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("brandCode", String.valueOf(brandCode));
        attrs.add(maLoaiCha);

        HashMap<String, String> index = new HashMap<>();
        index.put("startIndex", String.valueOf(startIndex));
        attrs.add(index);

        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            if (json == null) return null;

            JSONObject object = new JSONObject(json);
            JSONArray arraySanPham = object.getJSONArray(action.getNodeName());

            int len = arraySanPham.length();
            for (int i = 0; i < len; i++) {
                SanPham sanPham = new SanPham();
                JSONObject item = arraySanPham.getJSONObject(i);

                sanPham.setMaSP(item.getInt("MASP"));
                sanPham.setTenSP(item.getString("TENSP"));
                sanPham.setGia(item.getInt("GIA"));
                sanPham.setAnhLon(item.getString("ANHLON"));
                sanPham.setAnhNho(item.getString("ANHNHO"));

                sanPhamList.add(sanPham);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return sanPhamList;
    }

    public List<SanPham> layDanhSachSanPhamTheoMaLoai(Action action, int productCode, int startIndex) {
        String url = TrangChuActivity.SERVER_NAME + action.getAction();
        if (!action.getAction().contains(".php"))
            url += ".php";

        List<SanPham> sanPhamList = new ArrayList<>();
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("productCode", String.valueOf(productCode));
        attrs.add(maLoaiCha);

        HashMap<String, String> index = new HashMap<>();
        index.put("startIndex", String.valueOf(startIndex));
        attrs.add(index);

        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            if (json == null) return null;

            JSONObject object = new JSONObject(json);
            JSONArray arraySanPham = object.getJSONArray(action.getNodeName());

            int len = arraySanPham.length();
            for (int i = 0; i < len; i++) {
                SanPham sanPham = new SanPham();
                JSONObject item = arraySanPham.getJSONObject(i);

                sanPham.setMaSP(item.getInt("MASP"));
                sanPham.setTenSP(item.getString("TENSP"));
                sanPham.setGia(item.getInt("GIA"));
                sanPham.setAnhLon(item.getString("ANHLON"));
                sanPham.setAnhNho(item.getString("ANHNHO"));

                sanPhamList.add(sanPham);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return sanPhamList;
    }

}
