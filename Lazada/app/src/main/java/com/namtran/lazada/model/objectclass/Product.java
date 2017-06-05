package com.namtran.lazada.model.objectclass;

import java.util.List;

/**
 * Created by namtr on 07/05/2017.
 */

public class Product {
    private int productCode;
    private String productName;
    private int price;
    private int amount;
    private String bigImageUrl;
    private String smallImageUrl;
    private String infomation;
    private int productTypeCode;
    private int brandCode;
    private int staffCode;
    private String staffName;
    private int numOfBuys;
    private byte[] image;
    private List<ProductDetail> productDetails;

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(int productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public int getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(int brandCode) {
        this.brandCode = brandCode;
    }

    public int getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(int staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getNumOfBuys() {
        return numOfBuys;
    }

    public void setNumOfBuys(int numOfBuys) {
        this.numOfBuys = numOfBuys;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getInfomation() {
        return infomation;
    }

    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }
}
