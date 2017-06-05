package com.namtran.lazada.model.objectclass;

import java.util.List;

/**
 * Created by namtr on 26/04/2017.
 * <p>
 *     parentTypeCode = 0 là các menu to nhất ngoài cùng
 *     còn lại các menu con(parentTypeCode) của menu cha(productTypeCode) sẽ có parentTypeCode = productTypeCode
 * </p>
 */

public class ProductType {
    private int productTypeCode, parentTypeCode;
    private String productTypeName;
    private List<ProductType> childs;

    public int getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(int productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public int getParentTypeCode() {
        return parentTypeCode;
    }

    public void setParentTypeCode(int parentTypeCode) {
        this.parentTypeCode = parentTypeCode;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public List<ProductType> getChilds() {
        return childs;
    }

    public void setChilds(List<ProductType> childs) {
        this.childs = childs;
    }
}
