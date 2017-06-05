package com.namtran.lazada.model.objectclass;

/**
 * Created by namtr on 10/05/2017.
 */

public enum Action {
    MENU_LIST("danhsachmenu", "DANHSACHMENU"),
    BRAND_LIST("thuonghieulon", "DANHSACHTHUONGHIEU"),
    TOP_PHONE_TABLET("topDTvaMTB", "TOPDTVAMTB"),
    ACCESSORIES("phukien", "DANHSACHPHUKIEN"),
    UTILITIES("tienich", "DANHSACHTIENICH"),
    TOP_ACCESSORIES("topphukien", "TOPPHUKIEN"),
    TOP_ULTILITIES("toptienich", "TOPTIENICH"),
    BRAND_LOGO("logothuonghieulon", "LOGOTHUONGHIEU"),
    NEW_PRODUCT("hangmoive", "HANGMOIVE"),
    PRODUCT_LIST_BY_PTYPE_CODE("danhsachsanpham_MALOAISP", "DANHSACHSANPHAM"),
    PRODUCT_LIST_BY_BRAND_CODE("danhsachsanpham_MATHUONGHIEU", "DANHSACHSANPHAMTH"),
    PRODUCT_DETAIL("chitietsanpham", "CHITIETSANPHAM"),
    ADD_COMMENTARY("themdanhgia", ""),
    COMMENTARY_LIST("danhgiatheomasp", "DANHSACHDANHGIA");

    private String action;
    private String parentNodeName;

    Action(String action, String parentNodeName) {
        this.action = action;
        this.parentNodeName = parentNodeName;
    }

    public String getAction() {
        return action;
    }

    public String getNodeName() {
        return parentNodeName;
    }

    @Override
    public String toString() {
        return action;
    }
}
