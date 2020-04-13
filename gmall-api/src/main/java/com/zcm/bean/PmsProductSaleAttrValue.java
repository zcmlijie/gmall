package com.zcm.bean;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;

public class PmsProductSaleAttrValue implements Serializable {
    private static final long serialVersionUID = 3034205457295705268L;
    private Integer id;

    private Integer productId;

    private Integer saleAttrId;

    private String saleAttrValueName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(Integer saleAttrId) {
        this.saleAttrId = saleAttrId;
    }

    public String getSaleAttrValueName() {
        return saleAttrValueName;
    }

    public void setSaleAttrValueName(String saleAttrValueName) {
        this.saleAttrValueName = saleAttrValueName == null ? null : saleAttrValueName.trim();
    }
}
