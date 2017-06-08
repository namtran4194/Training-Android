package com.namtran.lazada.view.home;

import com.namtran.lazada.model.objectclass.ProductType;

import java.util.List;

/**
 * Created by namtr on 26/04/2017.
 */

public interface HandlingMenuView {

    // hiển thị menu
    void showMenu(List<ProductType> productTypes);
}
