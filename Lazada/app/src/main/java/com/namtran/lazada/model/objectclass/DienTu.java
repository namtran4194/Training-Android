package com.namtran.lazada.model.objectclass;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public class DienTu {
    private List<ThuongHieu> thuongHieuList;
    private List<SanPham> sanPhamList;
    private String tenTieuDe;
    private String tenTieuDeTop;
    private boolean queryToThuongHieu;

    public List<ThuongHieu> getThuongHieuList() {
        return thuongHieuList;
    }

    public void setThuongHieuList(List<ThuongHieu> thuongHieuList) {
        this.thuongHieuList = thuongHieuList;
    }

    public List<SanPham> getSanPhamList() {
        return sanPhamList;
    }

    public void setSanPhamList(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    public String getTenTieuDe() {
        return tenTieuDe;
    }

    public void setTenTieuDe(String tenTieuDe) {
        this.tenTieuDe = tenTieuDe;
    }

    public String getTenTieuDeTop() {
        return tenTieuDeTop;
    }

    public void setTenTieuDeTop(String tenTieuDeTop) {
        this.tenTieuDeTop = tenTieuDeTop;
    }

    public boolean isQueryToThuongHieu() {
        return queryToThuongHieu;
    }

    public void setQueryToThuongHieu(boolean queryToThuongHieu) {
        this.queryToThuongHieu = queryToThuongHieu;
    }
}
