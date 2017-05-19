package com.namtran.lazada.model.objectclass;

/**
 * Created by namtr on 10/05/2017.
 */

public enum Action {
    DANH_SACH_MENU("danhsachmenu", "DANHSACHMENU"),
    THUONG_HIEU_LON("thuonghieulon", "DANHSACHTHUONGHIEU"),
    TOP_DT_VA_MTB("topDTvaMTB", "TOPDTVAMTB"),
    PHU_KIEN("phukien", "DANHSACHPHUKIEN"),
    TIEN_ICH("tienich", "DANHSACHTIENICH"),
    TOP_PHU_KIEN("topphukien", "TOPPHUKIEN"),
    TOP_TIEN_ICH("toptienich", "TOPTIENICH"),
    LOGO_THUONG_HIEU("logothuonghieulon", "LOGOTHUONGHIEU"),
    HANG_MOI_VE("hangmoive", "HANGMOIVE"),
    SAN_PHAM_THEO_MA_LOAI("danhsachsanpham_MALOAISP", "DANHSACHSANPHAM"),
    SAN_PHAM_THEO_THUONG_HIEU("danhsachsanpham_MATHUONGHIEU", "DANHSACHSANPHAMTH"),
    CHI_TIET_SAN_PHAM("chitietsanpham", "CHITIETSANPHAM"),
    THEM_DANH_GIA("themdanhgia", "");

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
