package com.namtran.lazada.model.objectclass;

import java.util.List;

/**
 * Created by namtr on 08/05/2017.
 */

public class Electronics {
    private List<Brand> brandList;
    private List<Product> productList;
    private String titleForBrandList;
    private String titleForProductList;
    private boolean isQueryToBrand;

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getTitleForBrandList() {
        return titleForBrandList;
    }

    public void setTitleForBrandList(String titleForBrandList) {
        this.titleForBrandList = titleForBrandList;
    }

    public String getTitleForProductList() {
        return titleForProductList;
    }

    public void setTitleForProductList(String titleForProductList) {
        this.titleForProductList = titleForProductList;
    }

    public boolean isQueryToBrand() {
        return isQueryToBrand;
    }

    public void setQueryToBrand(boolean queryToBrand) {
        this.isQueryToBrand = queryToBrand;
    }
}
