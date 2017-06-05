package com.namtran.lazada.model.showproduct.productdetail;

import com.namtran.lazada.connection.internet.DownloadJson;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.objectclass.ProductDetail;
import com.namtran.lazada.model.objectclass.Comment;
import com.namtran.lazada.view.home.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 5/18/2017.
 */

public class ProductDetailModel {

    public Product layChiTietSanPham(Action action, int maSP) {
        Product product = new Product();
        String url = HomeActivity.SERVER_NAME + action.getAction();

        if (!url.contains(".php"))
            url += ".php";

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr = new HashMap<>();
        attr.put("masp", String.valueOf(maSP));
        attrs.add(attr);

        DownloadJson downloadJson = new DownloadJson(url, attrs);
        downloadJson.execute();

        try {
            String json = downloadJson.get();
            if (json == null) return null;

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(action.getNodeName());

            for (int k = 0; k < array.length(); k++) {
                JSONObject object = array.getJSONObject(k);
                product.setProductCode(object.getInt("MASP"));
                product.setProductName(object.getString("TENSP"));
                product.setPrice(object.getInt("GIA"));
                product.setBigImageUrl(object.getString("ANHLON"));
                product.setSmallImageUrl(object.getString("ANHNHO"));
                product.setProductTypeCode(object.getInt("MALOAISP"));
                product.setInfomation(object.getString("THONGTIN"));
                product.setBrandCode(object.getInt("MATHUONGHIEU"));
                product.setStaffCode(object.getInt("MANV"));
                product.setStaffName(object.getString("TENNV"));
                product.setNumOfBuys(object.getInt("LUOTMUA"));

                List<ProductDetail> productDetails = new ArrayList<>();
                JSONArray thongsokythuat = object.getJSONArray("THONGSOKYTHUAT");
                int count = thongsokythuat.length();
                for (int i = 0; i < count; i++) {
                    JSONObject chitiet = thongsokythuat.getJSONObject(i);
                    for (int j = 0; j < chitiet.names().length(); j++) {
                        ProductDetail productDetail = new ProductDetail();
                        String tenChiTiet = chitiet.names().getString(j);
                        productDetail.setKey(tenChiTiet);
                        productDetail.setValue(chitiet.getString(tenChiTiet));
                        productDetails.add(productDetail);
                    }
                }
                product.setProductDetails(productDetails);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return product;
    }

    public List<Comment> layDanhSachDanhGia(Action action, int maSP, int startIndex) {
        List<Comment> commentList = new ArrayList<>();
        String url = HomeActivity.SERVER_NAME + action.getAction();
        if (!url.contains(".php")) url += ".php";

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr1 = new HashMap<>();
        attr1.put("masp", String.valueOf(maSP));
        attrs.add(attr1);

        HashMap<String, String> attr2 = new HashMap<>();
        attr2.put("startIndex", String.valueOf(startIndex));
        attrs.add(attr2);

        DownloadJson downloadJson = new DownloadJson(url, attrs);
        downloadJson.execute();

        try {
            String json = downloadJson.get();
            if (json == null) return null;

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(action.getNodeName());

            int len = array.length();
            for (int i = 0; i < len; i++) {
                Comment comment = new Comment();
                JSONObject object = array.getJSONObject(i);

                comment.setProductCode(object.getInt("MASP"));
                String tieuDe = object.getString("TIEUDE");
                comment.setTitle(tieuDe.substring(0, 1).toUpperCase() + tieuDe.substring(1));
                comment.setContent(object.getString("NOIDUNG"));
                comment.setPhoneName(object.getString("TENTHIETBI"));
                comment.setNumOfStars(object.getInt("SOSAO"));
                comment.setCommentaryCode("MADG");
                comment.setDate(object.getString("NGAYDANHGIA"));

                commentList.add(comment);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return commentList;
    }
}
