package com.namtran.lazada.model.showproduct.bycategory;

import com.namtran.lazada.connection.internet.DownloadJson;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.view.home.HomeActivity;

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

public class ShowProductByCategoryModel {

    public List<Product> layDanhSachSanPhamTheoMaThuongHieu(Action action, int brandCode, int startIndex) {
        String url = HomeActivity.SERVER_NAME + action.getAction();
        if (!url.contains(".php"))
            url += ".php";

        List<Product> productList = new ArrayList<>();
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("brandCode", String.valueOf(brandCode));
        attrs.add(maLoaiCha);

        HashMap<String, String> index = new HashMap<>();
        index.put("startIndex", String.valueOf(startIndex));
        attrs.add(index);

        DownloadJson downloadJson = new DownloadJson(url, attrs);
        downloadJson.execute();

        try {
            String json = downloadJson.get();
            if (json == null) return null;

            JSONObject object = new JSONObject(json);
            JSONArray arraySanPham = object.getJSONArray(action.getNodeName());

            int len = arraySanPham.length();
            for (int i = 0; i < len; i++) {
                Product product = new Product();
                JSONObject item = arraySanPham.getJSONObject(i);

                product.setProductCode(item.getInt("MASP"));
                product.setProductName(item.getString("TENSP"));
                product.setPrice(item.getInt("GIA"));
                product.setBigImageUrl(item.getString("ANHLON"));
                product.setSmallImageUrl(item.getString("ANHNHO"));

                productList.add(product);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return productList;
    }

    public List<Product> layDanhSachSanPhamTheoMaLoai(Action action, int productCode, int startIndex) {
        String url = HomeActivity.SERVER_NAME + action.getAction();
        if (!url.contains(".php"))
            url += ".php";

        List<Product> productList = new ArrayList<>();
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("productCode", String.valueOf(productCode));
        attrs.add(maLoaiCha);

        HashMap<String, String> index = new HashMap<>();
        index.put("startIndex", String.valueOf(startIndex));
        attrs.add(index);

        DownloadJson downloadJson = new DownloadJson(url, attrs);
        downloadJson.execute();

        try {
            String json = downloadJson.get();
            if (json == null) return null;

            JSONObject object = new JSONObject(json);
            JSONArray arraySanPham = object.getJSONArray(action.getNodeName());

            int len = arraySanPham.length();
            for (int i = 0; i < len; i++) {
                Product product = new Product();
                JSONObject item = arraySanPham.getJSONObject(i);

                product.setProductCode(item.getInt("MASP"));
                product.setProductName(item.getString("TENSP"));
                product.setPrice(item.getInt("GIA"));
                product.setBigImageUrl(item.getString("ANHLON"));
                product.setSmallImageUrl(item.getString("ANHNHO"));

                productList.add(product);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return productList;
    }

}
