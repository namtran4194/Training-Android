package com.namtran.lazada.model.trangchu.dientu;

import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.objectclass.SanPham;
import com.namtran.lazada.model.objectclass.ThuongHieu;
import com.namtran.lazada.view.trangchu.TrangChuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 07/05/2017.
 */

public class ModelDienTu {

    public List<ThuongHieu> layDanhSachThuongHieuLon(String action, String parentNodeName) {
        if (!action.contains(".php"))
            action += ".php";

        List<ThuongHieu> thuongHieuList = new ArrayList<>();
        String url = TrangChuActivity.SERVER_NAME + action;

        DownloadJSON downloadJSON = new DownloadJSON(url);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            if (json == null) return null;

            JSONObject object = new JSONObject(json);
            JSONArray arrayThuongHieu = object.getJSONArray(parentNodeName);

            int len = arrayThuongHieu.length();
            for (int i = 0; i < len; i++) {
                ThuongHieu thuongHieu = new ThuongHieu();
                JSONObject item = arrayThuongHieu.getJSONObject(i);

                thuongHieu.setMaThuongHieu(item.getInt("MASP"));
                thuongHieu.setTenThuongHieu(item.getString("TENSP"));
                thuongHieu.setHinhThuongHieu(item.getString("ANHLON"));

                thuongHieuList.add(thuongHieu);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return thuongHieuList;
    }

    public List<SanPham> layDanhSachTopSanPham(String action, String parentNodeName) {
        if (!action.contains(".php"))
            action += ".php";

        List<SanPham> sanPhamList = new ArrayList<>();
        String url = TrangChuActivity.SERVER_NAME + action;

        DownloadJSON downloadJSON = new DownloadJSON(url);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            if (json == null) return null;

            JSONObject object = new JSONObject(json);
            JSONArray arraySanPham = object.getJSONArray(parentNodeName);

            int len = arraySanPham.length();
            for (int i = 0; i < len; i++) {
                SanPham sanPham = new SanPham();
                JSONObject item = arraySanPham.getJSONObject(i);

                sanPham.setMaSP(item.getInt("MASP"));
                sanPham.setTenSP(item.getString("TENSP"));
                sanPham.setGia(item.getInt("GIA"));
                sanPham.setAnhLon(item.getString("ANHLON"));

                sanPhamList.add(sanPham);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return sanPhamList;
    }
}
