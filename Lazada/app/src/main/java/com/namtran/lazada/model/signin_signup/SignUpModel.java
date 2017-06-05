package com.namtran.lazada.model.signin_signup;

import com.namtran.lazada.connection.internet.DownloadJson;
import com.namtran.lazada.model.objectclass.Staff;
import com.namtran.lazada.view.home.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 05/05/2017.
 */

public class SignUpModel {

    public boolean dangKyThanhVIen(Staff staff) {
        List<HashMap<String, String>> attrs = new ArrayList<>();
        String url = HomeActivity.SERVER_NAME + "dangky.php";

        HashMap<String, String> tenNV = new HashMap<>();
        tenNV.put("name", staff.getStaffName());
        attrs.add(tenNV);

        HashMap<String, String> tenDangNhap = new HashMap<>();
        tenDangNhap.put("username", staff.getUsername());
        attrs.add(tenDangNhap);

        HashMap<String, String> matKhau = new HashMap<>();
        matKhau.put("password", staff.getPassword());
        attrs.add(matKhau);

        HashMap<String, String> maLoaiNV = new HashMap<>();
        maLoaiNV.put("staffCode", String.valueOf(staff.getStaffTypeCode()));
        attrs.add(maLoaiNV);

        HashMap<String, String> emailDocQuyen = new HashMap<>();
        emailDocQuyen.put("offerViaEmail", staff.getReceiveNewsViaEmail());
        attrs.add(emailDocQuyen);

        DownloadJson downloadJson = new DownloadJson(url, attrs);
        downloadJson.execute();

        try {
            String json = downloadJson.get();
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
