package com.namtran.lazada.model.home.electronics;

import com.namtran.lazada.connection.internet.DownloadJson;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.objectclass.Brand;
import com.namtran.lazada.view.home.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 07/05/2017.
 */

public class ElectronicsModel {

    public List<Brand> getBrandList(String action, String parentNodeName) {
        if (!action.contains(".php"))
            action += ".php";

        List<Brand> brandList = new ArrayList<>();
        String url = HomeActivity.SERVER_NAME + action;

        DownloadJson downloadJson = new DownloadJson(url);
        downloadJson.execute();

        try {
            String json = downloadJson.get();
            if (json == null) return null;

            JSONObject object = new JSONObject(json);
            JSONArray arrayThuongHieu = object.getJSONArray(parentNodeName);

            int len = arrayThuongHieu.length();
            for (int i = 0; i < len; i++) {
                Brand brand = new Brand();
                JSONObject item = arrayThuongHieu.getJSONObject(i);

                brand.setBrandCode(item.getInt("MASP"));
                brand.setBrandName(item.getString("TENSP"));
                brand.setBrandLogo(item.getString("ANHLON"));

                brandList.add(brand);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return brandList;
    }

    public List<Product> getProducts(String action, String parentNodeName) {
        if (!action.contains(".php"))
            action += ".php";

        List<Product> productList = new ArrayList<>();
        String url = HomeActivity.SERVER_NAME + action;

        DownloadJson downloadJson = new DownloadJson(url);
        downloadJson.execute();

        try {
            String json = downloadJson.get();
            if (json == null) return null;

            JSONObject object = new JSONObject(json);
            JSONArray arraySanPham = object.getJSONArray(parentNodeName);

            int len = arraySanPham.length();
            for (int i = 0; i < len; i++) {
                Product product = new Product();
                JSONObject item = arraySanPham.getJSONObject(i);

                product.setProductCode(item.getInt("MASP"));
                product.setProductName(item.getString("TENSP"));
                product.setPrice(item.getInt("GIA"));
                product.setBigImageUrl(item.getString("ANHLON"));

                productList.add(product);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return productList;
    }
}
