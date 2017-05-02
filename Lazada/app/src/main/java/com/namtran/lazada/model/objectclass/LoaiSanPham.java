package com.namtran.lazada.model.objectclass;

import java.util.List;

/**
 * Created by namtr on 26/04/2017.
 */

public class LoaiSanPham {
    private int maLoaiSP, maLoaiCha;
    private String tenLoaiSP;
    private List<LoaiSanPham> childs;

    public int getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(int maLoaiSP) {
        this.maLoaiSP = maLoaiSP;
    }

    public int getMaLoaiCha() {
        return maLoaiCha;
    }

    public void setMaLoaiCha(int maLoaiCha) {
        this.maLoaiCha = maLoaiCha;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public List<LoaiSanPham> getChilds() {
        return childs;
    }

    public void setChilds(List<LoaiSanPham> childs) {
        this.childs = childs;
    }
}
