package com.namtran.lazada.model.trangchu.dientu;

import com.namtran.lazada.connection.internet.DownloadJSON;
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

    public List<ThuongHieu> layDanhSachThuongHieuLon() {
        List<ThuongHieu> thuongHieuList = new ArrayList<>();
        String url = TrangChuActivity.SERVER_NAME + "laydanhsachthuonghieulon.php";

        DownloadJSON downloadJSON = new DownloadJSON(url);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            JSONObject object = new JSONObject(json);
            JSONArray arrayThuongHieu = object.getJSONArray("DANHSACHTHUONGHIEU");
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return thuongHieuList;
    }
}
