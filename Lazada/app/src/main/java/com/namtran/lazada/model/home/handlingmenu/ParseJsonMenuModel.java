package com.namtran.lazada.model.home.handlingmenu;

import com.namtran.lazada.connection.internet.DownloadJson;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.ProductType;
import com.namtran.lazada.view.home.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 26/04/2017.
 */

public class ParseJsonMenuModel {

    // lấy danh sách các loại sản phẩm từ chuỗi JSON
    public List<ProductType> parserJSONMenu(String jsonData) {
        if (jsonData == null) return null;

        List<ProductType> datas = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            Action action = Action.MENU_LIST;
            JSONArray array = jsonObject.getJSONArray(action.getNodeName());
            int count = array.length();
            for (int i = 0; i < count; i++) {
                JSONObject value = array.getJSONObject(i);

                ProductType productType = new ProductType();
                productType.setProductTypeCode(Integer.parseInt(value.getString("MALOAISP")));
                productType.setParentTypeCode(Integer.parseInt(value.getString("MALOAI_CHA")));
                productType.setProductTypeName(value.getString("TENLOAISP"));

                datas.add(productType);
            }
        } catch (JSONException e) {
            return null;
        }
        return datas;
    }

    // lấy danh sách các loại sản phẩm theo mã loại cha
    public List<ProductType> getTypeOfProductById(int parentId) {
        List<ProductType> productTypes = new ArrayList<>();
        List<HashMap<String, String>> attrs = new ArrayList<>(); // các tham số truyền theo request

        Action action = Action.MENU_LIST;
        String url = HomeActivity.SERVER_NAME + action.getAction();
        if (!action.getAction().contains(".php"))
            url += ".php";

        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("parentCode", String.valueOf(parentId));
        attrs.add(maLoaiCha);
        DownloadJson downloadJson = new DownloadJson(url, attrs);

        downloadJson.execute();
        try {
            String json = downloadJson.get();
            if (json != null)
                productTypes = parserJSONMenu(json);
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
        return productTypes;
    }
}
