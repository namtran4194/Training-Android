package com.namtran.lazada.model.objectclass;

/**
 * Created by namtr on 10/05/2017.
 */

public enum Action {
    THUONG_HIEU_LON_LIST("laydanhsachthuonghieulon", "DANHSACHTHUONGHIEU"),
    TOP_DT_VA_MTB_LIST("laydanhsachtopDTvaMTB", "TOPDTVAMTB"),
    PHU_KIEN_LIST("laydanhsachphukien", "DANHSACHPHUKIEN"),
    TIEN_ICH_LIST("laydanhsachtienich", "DANHSACHTIENICH"),
    TOP_PHU_KIEN_LIST("laydanhsachtopphukien", "TOPPHUKIEN"),
    TOP_TIEN_ICH_LIST("laydanhsachtoptienich", "TOPTIENICH");

    private String action;
    private String parentNodeName;

    Action(String action, String parentNodeName) {
        this.action = action;
        this.parentNodeName = parentNodeName;
    }

    public String getAction() {
        return action;
    }

    public String getParentNodeName() {
        return parentNodeName;
    }

    @Override
    public String toString() {
        return action;
    }
}
