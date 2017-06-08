package com.namtran.lazada.model.showproduct.comment;

import com.namtran.lazada.connection.internet.DownloadJson;
import com.namtran.lazada.model.objectclass.Action;
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
 * Created by namtr on 5/19/2017.
 */

public class CommentModel {

    // thêm đánh giá
    public boolean add(Action action, Comment comment) {
        List<HashMap<String, String>> attrs = new ArrayList<>();
        String url = HomeActivity.SERVER_NAME + action.getAction();
        if (!url.contains(".php")) url += ".php";

        HashMap<String, String> maDG = new HashMap<>();
        maDG.put("madg", comment.getCommentaryCode());
        attrs.add(maDG);

        HashMap<String, String> maSP = new HashMap<>();
        maSP.put("masp", String.valueOf(comment.getProductCode()));
        attrs.add(maSP);

        HashMap<String, String> tenThietBi = new HashMap<>();
        tenThietBi.put("tenthietbi", comment.getPhoneName());
        attrs.add(tenThietBi);

        HashMap<String, String> tieuDe = new HashMap<>();
        tieuDe.put("tieude", comment.getTitle());
        attrs.add(tieuDe);

        HashMap<String, String> noiDung = new HashMap<>();
        noiDung.put("noidung", comment.getContent());
        attrs.add(noiDung);

        HashMap<String, String> soSao = new HashMap<>();
        soSao.put("sosao", String.valueOf(comment.getNumOfStars()));
        attrs.add(soSao);

        DownloadJson downloadJson = new DownloadJson(url, attrs);
        downloadJson.execute();

        try {
            String json = downloadJson.get();
            if (json != null) {
                JSONObject object = new JSONObject(json);
                return object.getBoolean("addCommentResult");
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // lấy tất cả đánh giá
    public List<Comment> getAll(Action action, int maSP, int startIndex) {
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

                comment.setProductCode(object.getInt("GIOHANG_PRODUCT_CODE"));
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
