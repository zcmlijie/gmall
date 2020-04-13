package com.zcm.bean;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public class PmsProductSaleAttr implements Serializable {
    private static final long serialVersionUID = -5108537998639071504L;
    private Integer id;

    private Integer productId;

    private Integer saleAttrId;

    private String saleAttrName;
    @Transient
    private List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList;

    public List<PmsProductSaleAttrValue> getPmsProductSaleAttrValueList() {
        return pmsProductSaleAttrValueList;
    }

    public void setPmsProductSaleAttrValueList(List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList) {
        this.pmsProductSaleAttrValueList = pmsProductSaleAttrValueList;
    }

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

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = saleAttrName == null ? null : saleAttrName.trim();
    }
}
