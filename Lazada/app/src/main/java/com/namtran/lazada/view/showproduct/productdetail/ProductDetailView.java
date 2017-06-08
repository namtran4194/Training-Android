package com.namtran.lazada.view.showproduct.productdetail;

import com.namtran.lazada.model.objectclass.Comment;
import com.namtran.lazada.model.objectclass.Product;

import java.util.List;

/**
 * Created by namtr on 5/18/2017.
 */

public interface ProductDetailView {

    // hiển thị chi tiết sản phẩm
    void showProductDetail(Product product);

    // hiển thị các hình ảnh xem trước của sản phẩm
    void showPicturePreview(String... link);

    // hiển thị danh sách đánh giá
    void showComments(List<Comment> commentList);

    // kết quả thêm vào giỏ hàng
    void addToCartResult(boolean result);
}
