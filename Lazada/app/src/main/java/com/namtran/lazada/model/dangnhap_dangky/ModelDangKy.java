package com.namtran.lazada.model.dangnhap_dangky;

import com.namtran.lazada.connection.internet.DownloadJSON;
import com.namtran.lazada.model.objectclass.NhanVien;
import com.namtran.lazada.view.trangchu.TrangChuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 05/05/2017.
 */

public class ModelDangKy {
    public boolean dangKyThanhVIen(NhanVien nhanVien) {
        List<HashMap<String, String>> attrs = new ArrayList<>();
        String url = TrangChuActivity.SERVER_NAME + "dangky.php";

        HashMap<String, String> tenNV = new HashMap<>();
        tenNV.put("name", nhanVien.getTenNV());
        attrs.add(tenNV);

        HashMap<String, String> tenDangNhap = new HashMap<>();
        tenDangNhap.put("username", nhanVien.getTenDangNhap());
        attrs.add(tenDangNhap);

        HashMap<String, String> matKhau = new HashMap<>();
        matKhau.put("password", nhanVien.getMatKhau());
        attrs.add(matKhau);

        HashMap<String, String> maLoaiNV = new HashMap<>();
        maLoaiNV.put("staffCode", String.valueOf(nhanVien.getMaLoaiNV()));
        attrs.add(maLoaiNV);

        HashMap<String, String> emailDocQuyen = new HashMap<>();
        emailDocQuyen.put("offerViaEmail", nhanVien.getEmailDocQuyen());
        attrs.add(emailDocQuyen);

        DownloadJSON downloadJSON = new DownloadJSON(url, attrs);
        downloadJSON.execute();

        try {
            String json = downloadJSON.get();
            if (json != null) {
                JSONObject object = new JSONObject(json);
                return object.getBoolean("result");
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            return false;
        }
        return false;
    }
}
