package com.namtran.lazada.view.showproduct.productdetail;

import com.namtran.lazada.model.objectclass.Comment;
import com.namtran.lazada.model.objectclass.Product;

import java.util.List;

/**
 * Created by namtr on 5/18/2017.
 */

public interface ProductDetailView {
    void hienThiChiTietSanPham(Product product);

    void hienThiSlider(String... link);

    void hienThiDanhGia(List<Comment> commentList);

    void ketQuaThemGiohang(boolean result);
}
