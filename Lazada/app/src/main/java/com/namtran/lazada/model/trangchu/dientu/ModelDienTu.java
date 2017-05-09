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

    public List<ThuongHieu> layDanhSachThuongHieuLon() {
        List<ThuongHieu> thuongHieuList = new ArrayList<>();
        String url = TrangChuActivity.SERVER_NAME + "laydanhsachthuonghieulon.php";

        DownloadJSON downloadJSON = new DownloadJSON(url);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            JSONObject object = new JSONObject(json);
            JSONArray arrayThuongHieu = object.getJSONArray("DANHSACHTHUONGHIEU");

            int len = arrayThuongHieu.length();
            for (int i = 0; i < len; i++) {
                ThuongHieu thuongHieu = new ThuongHieu();
                JSONObject item = arrayThuongHieu.getJSONObject(i);

                thuongHieu.setMaThuongHieu(item.getInt("MATHUONGHIEU"));
                thuongHieu.setTenThuongHieu(item.getString("TENTHUONGHIEU"));
                thuongHieu.setHinhThuongHieu(item.getString("HINHLOAISPTH"));

                thuongHieuList.add(thuongHieu);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return thuongHieuList;
    }

    public List<SanPham> layDanhSachDTvaMTB() {
        List<SanPham> sanPhamList = new ArrayList<>();
        String url = TrangChuActivity.SERVER_NAME + "laydanhsachDTvaMTB.php";

        DownloadJSON downloadJSON = new DownloadJSON(url);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            JSONObject object = new JSONObject(json);
            JSONArray arraySanPham = object.getJSONArray("TOPDTVAMTB");

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
