package com.namtran.lazada.presenter.home.handlingmenu;

import com.facebook.AccessToken;
import com.namtran.lazada.connection.internet.DownloadJson;
import com.namtran.lazada.model.signin_signup.SignInModel;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.ProductType;
import com.namtran.lazada.model.home.handlingmenu.JsonParser;
import com.namtran.lazada.view.home.HomeActivity;
import com.namtran.lazada.view.home.HandlingMenuView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 26/04/2017.
 */

public class HandlingMenuPresenter implements IHandlingMenu {
    private HandlingMenuView handlingMenuView;

    public HandlingMenuPresenter() {
    }

    public HandlingMenuPresenter(HandlingMenuView handlingMenuView) {
        this.handlingMenuView = handlingMenuView;
    }

    @Override
    public void getMenu() {
        List<ProductType> productTypes;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        Action action = Action.MENU_LIST;
        String url = HomeActivity.SERVER_NAME + action.getAction();
        if (!action.getAction().contains(".php"))
            url += ".php";

        HashMap<String, String> maLoaiCha = new HashMap<>();
        maLoaiCha.put("parentCode", "0");
        attrs.add(maLoaiCha);

        DownloadJson downloadJson = new DownloadJson(url, attrs);
        downloadJson.execute();

        try {
            String jsonData = downloadJson.get();
            JsonParser xuLyJSONMenu = new JsonParser();
            productTypes = xuLyJSONMenu.parseJsonMenu(jsonData);
            if (productTypes != null && productTypes.size() > 0)
                handlingMenuView.showMenu(productTypes); // trả danh sách các loại sản phẩm cho view
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccessToken getFacebookAccessToken() {
        SignInModel signInModel = new SignInModel();
        return signInModel.layAccessTokenFB();
    }
}
