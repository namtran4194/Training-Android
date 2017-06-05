package com.namtran.lazada.presenter.showproduct.bycategory;

import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.showproduct.bycategory.ShowProductByCategoryModel;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.view.showproduct.bycategory.ShowProductByCategoryView;

import java.util.List;

/**
 * Created by namtr on 5/14/2017.
 */

public class ShowProductByCategoryPresenter implements IShowProductByCategory {
    private ShowProductByCategoryView byCategoryView;
    private ShowProductByCategoryModel byCategoryModel;

    public ShowProductByCategoryPresenter(ShowProductByCategoryView byCategoryView) {
        this.byCategoryView = byCategoryView;
        byCategoryModel = new ShowProductByCategoryModel();
    }

    @Override
    public void layDanhSachSanPham(int typeCode, boolean loadThuongHieu) {
        List<Product> productList = loadMore(typeCode, loadThuongHieu, 0);
        byCategoryView.hienThiDanhSachSanPham(productList);
    }

    public List<Product> loadMore(int typeCode, boolean loadThuongHieu, int startIndex) {
        List<Product> productList;
        Action action;

        if (loadThuongHieu) {
            action = Action.PRODUCT_LIST_BY_BRAND_CODE;
            productList = byCategoryModel.layDanhSachSanPhamTheoMaThuongHieu(action, typeCode, startIndex);
        } else {
            action = Action.PRODUCT_LIST_BY_PTYPE_CODE;
            productList = byCategoryModel.layDanhSachSanPhamTheoMaLoai(action, typeCode, startIndex);
        }
//        Log.d("kiemtra", productList.size() + "");
        return productList;
    }
}
