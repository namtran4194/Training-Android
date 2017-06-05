package com.namtran.lazada.presenter.showproduct.productdetail;

import android.content.Context;

import com.namtran.lazada.model.cart.CartModel;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.showproduct.productdetail.ProductDetailModel;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.Comment;
import com.namtran.lazada.view.showproduct.productdetail.ProductDetailView;

import java.util.List;

/**
 * Created by namtr on 5/18/2017.
 */

public class ProductDetailPresenter implements IProductDetail {
    private ProductDetailView productDetailView;
    private ProductDetailModel productDetailModel;
    private CartModel cartModel;

    public ProductDetailPresenter() {
        productDetailModel = new ProductDetailModel();
        cartModel = new CartModel();
    }

    public ProductDetailPresenter(ProductDetailView viewChiTietSP) {
        this.productDetailView = viewChiTietSP;
        productDetailModel = new ProductDetailModel();
        cartModel = new CartModel();
    }

    @Override
    public void layChiTietSanPham(Action action, int maSP) {
        Product product = productDetailModel.layChiTietSanPham(action, maSP);
        String stringAnhNho = product.getSmallImageUrl();
        String[] link = stringAnhNho.split(",");
        productDetailView.hienThiChiTietSanPham(product);
        productDetailView.hienThiSlider(link);
    }

    @Override
    public void layDanhSachDanhGia(Action action, int maSP, int startIndex) {
        List<Comment> commentList = productDetailModel.layDanhSachDanhGia(action, maSP, startIndex);
        productDetailView.hienThiDanhGia(commentList);
    }

    @Override
    public void themGioHang(Context context, Product product) {
        cartModel.moKetNoi(context);
        boolean result = cartModel.themGioHang(product);
        productDetailView.ketQuaThemGiohang(result);
    }

    @Override
    public long soLuongSPCoTrongGioHang(Context context) {
        cartModel.moKetNoi(context);
        return cartModel.getRowsCount();
    }
}
