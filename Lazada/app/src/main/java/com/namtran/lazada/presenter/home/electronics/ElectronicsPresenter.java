package com.namtran.lazada.presenter.home.electronics;

import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.Electronics;
import com.namtran.lazada.model.objectclass.Product;
import com.namtran.lazada.model.objectclass.Brand;
import com.namtran.lazada.model.home.electronics.ElectronicsModel;
import com.namtran.lazada.view.home.electronics.ElectronicsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public class ElectronicsPresenter implements IElectronics {
    private ElectronicsView electronicsView;
    private ElectronicsModel electronicsModel;

    public ElectronicsPresenter(ElectronicsView electronicsView) {
        this.electronicsView = electronicsView;
        this.electronicsModel = new ElectronicsModel();
    }

    @Override
    public void layDanhSachDienTu() {
        boolean haveAllData = true;
        List<Electronics> electronicsList = new ArrayList<>();
        Electronics electronics;

        List<Brand> brandList = electronicsModel.layDanhSachThuongHieuLon(Action.BRAND_LIST.getAction(), Action.BRAND_LIST.getNodeName());
        List<Product> topDTVaMTBList = electronicsModel.layDanhSachTopSanPham(Action.TOP_PHONE_TABLET.getAction(), Action.TOP_PHONE_TABLET.getNodeName());
        if (brandList == null || topDTVaMTBList == null) haveAllData = false;
        else {
            electronics = new Electronics();
            electronics.setBrandList(brandList);
            electronics.setProductList(topDTVaMTBList);
            electronics.setTitleForBrandList("Danh sách thương hiệu lớn");
            electronics.setTitleForProductList("Top điện thoại & máy tính bảng");
            electronics.setQueryToBrand(true);
            electronicsList.add(electronics);

            List<Brand> phuKienList = electronicsModel.layDanhSachThuongHieuLon(Action.ACCESSORIES.getAction(), Action.ACCESSORIES.getNodeName());
            List<Product> topPhuKienList = electronicsModel.layDanhSachTopSanPham(Action.TOP_ACCESSORIES.getAction(), Action.TOP_ACCESSORIES.getNodeName());
            if (phuKienList == null || topPhuKienList == null) haveAllData = false;
            else {
                electronics = new Electronics();
                electronics.setBrandList(phuKienList);
                electronics.setProductList(topPhuKienList);
                electronics.setTitleForBrandList("Danh sách phụ kiện");
                electronics.setTitleForProductList("Top phụ kiện");
                electronics.setQueryToBrand(false);
                electronicsList.add(electronics);

                List<Brand> tienIchList = electronicsModel.layDanhSachThuongHieuLon(Action.UTILITIES.getAction(), Action.UTILITIES.getNodeName());
                List<Product> topTienIchList = electronicsModel.layDanhSachTopSanPham(Action.TOP_ULTILITIES.getAction(), Action.TOP_ULTILITIES.getNodeName());
                if (tienIchList == null || topTienIchList == null) haveAllData = false;
                else {
                    electronics = new Electronics();
                    electronics.setBrandList(tienIchList);
                    electronics.setProductList(topTienIchList);
                    electronics.setTitleForBrandList("Danh sách tiện ích");
                    electronics.setTitleForProductList("Top tiện ích");
                    electronics.setQueryToBrand(false);
                    electronicsList.add(electronics);
                }
            }
        }

        if (haveAllData) electronicsView.hienThiThuongHieuLon(electronicsList);
    }

    @Override
    public void layLogoThuongHieuLon() {
        List<Brand> brandList = electronicsModel.layDanhSachThuongHieuLon(Action.BRAND_LOGO.getAction(), Action.BRAND_LOGO.getNodeName());
        electronicsView.hienThiLogoThuongHieu(brandList);
    }

    @Override
    public void layDanhSachHangMoiVe() {
        List<Product> productList = electronicsModel.layDanhSachTopSanPham(Action.NEW_PRODUCT.getAction(), Action.NEW_PRODUCT.getNodeName());
        electronicsView.hienThiDanhSachHangMoiVe(productList);
    }
}
