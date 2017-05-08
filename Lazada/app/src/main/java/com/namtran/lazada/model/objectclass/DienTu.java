package com.namtran.lazada.model.objectclass;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public class DienTu {
    private String hinhSanPham;
    private List<ThuongHieu> thuongHieuList;
    private List<SanPham> sanPhamList;

    public String getHinhSanPham() {
        return hinhSanPham;
    }

    public void setHinhSanPham(String hinhSanPham) {
        this.hinhSanPham = hinhSanPham;
    }

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
}
