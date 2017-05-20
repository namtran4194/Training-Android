package com.namtran.lazada.model.hienthisanpham.danhgia;

import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.DanhGia;
import com.namtran.lazada.view.trangchu.TrangChuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 5/19/2017.
 */

public class ModelDanhGia {

    public boolean themDanhGia(Action action, DanhGia danhGia) {
        List<HashMap<String, String>> attrs = new ArrayList<>();
        String url = TrangChuActivity.SERVER_NAME + action.getAction();
        if (!url.contains(".php")) url += ".php";

        HashMap<String, String> maDG = new HashMap<>();
        maDG.put("madg", danhGia.getMaDG());
        attrs.add(maDG);

        HashMap<String, String> maSP = new HashMap<>();
        maSP.put("masp", String.valueOf(danhGia.getMaSP()));
        attrs.add(maSP);

        HashMap<String, String> tenThietBi = new HashMap<>();
        tenThietBi.put("tenthietbi", danhGia.getTenThietBi());
        attrs.add(tenThietBi);

        HashMap<String, String> tieuDe = new HashMap<>();
        tieuDe.put("tieude", danhGia.getTieuDe());
        attrs.add(tieuDe);

        HashMap<String, String> noiDung = new HashMap<>();
        noiDung.put("noidung", danhGia.getNoiDung());
        attrs.add(noiDung);

        HashMap<String, String> soSao = new HashMap<>();
        soSao.put("sosao", String.valueOf(danhGia.getSoSao()));
        attrs.add(soSao);

        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            if (json != null) {
                JSONObject object = new JSONObject(json);
                return object.getBoolean("result");
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public List<DanhGia> layDanhSachDanhGia(Action action, int maSP, int startIndex) {
        List<DanhGia> danhGiaList = new ArrayList<>();
        String url = TrangChuActivity.SERVER_NAME + action.getAction();
        if (!url.contains(".php")) url += ".php";

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr1 = new HashMap<>();
        attr1.put("masp", String.valueOf(maSP));
        attrs.add(attr1);

        HashMap<String, String> attr2 = new HashMap<>();
        attr2.put("startIndex", String.valueOf(startIndex));
        attrs.add(attr2);

        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            if (json == null) return null;

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(action.getNodeName());

            int len = array.length();
            for (int i = 0; i < len; i++) {
                DanhGia danhGia = new DanhGia();
                JSONObject object = array.getJSONObject(i);

                danhGia.setMaSP(object.getInt("GIOHANG_MASP"));
                String tieuDe = object.getString("TIEUDE");
                danhGia.setTieuDe(tieuDe.substring(0, 1).toUpperCase() + tieuDe.substring(1));
                danhGia.setNoiDung(object.getString("NOIDUNG"));
                danhGia.setTenThietBi(object.getString("TENTHIETBI"));
                danhGia.setSoSao(object.getInt("SOSAO"));
                danhGia.setMaDG("MADG");
                danhGia.setNgayDanhGia(object.getString("NGAYDANHGIA"));

                danhGiaList.add(danhGia);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return danhGiaList;
    }
}
