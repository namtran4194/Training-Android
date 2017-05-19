package com.namtran.lazada.model.hienthisanpham.danhgia;

import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.DanhGia;
import com.namtran.lazada.view.trangchu.TrangChuActivity;

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
}
