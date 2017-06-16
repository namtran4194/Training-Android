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
    public void getProductDetail(Action action, int maSP) {
        Product product = productDetailModel.getProductDetail(action, maSP);
        String stringAnhNho = product.getSmallImageUrl();
        String[] link = stringAnhNho.split(",");
        productDetailView.showProductDetail(product);
        productDetailView.showPicturePreview(link);
    }

    @Override
    public void getComments(Action action, int maSP, int startIndex) {
        List<Comment> commentList = productDetailModel.getAllComments(action, maSP, startIndex);
        productDetailView.showComments(commentList);
    }

    @Override
    public void addToCart(Context context, Product product) {
        cartModel.openConnection(context);
        boolean result = cartModel.add(product);
        productDetailView.addToCartResult(result);
    }

    @Override
    public long numOfProductsInCart(Context context) {
        cartModel.openConnection(context);
        return cartModel.size();
    }
}
