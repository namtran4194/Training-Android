package com.namtran.lazada.model.objectclass;

/**
 * Created by namtr on 07/05/2017.
 */

public class Brand {
    private int brandCode;
    private int numOfBuys;
    private String brandName;
    private String brandLogo;

    public int getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(int brandCode) {
        this.brandCode = brandCode;
    }

    public int getNumOfBuys() {
        return numOfBuys;
    }

    public void setNumOfBuys(int numOfBuys) {
        this.numOfBuys = numOfBuys;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }
}
