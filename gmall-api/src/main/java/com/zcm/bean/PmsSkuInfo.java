package com.zcm.bean;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PmsSkuInfo implements Serializable {
    private static final long serialVersionUID = 4372412705259747143L;
    private Integer id;

    private Integer productId;

    private Integer price;

    private String skuName;

    private String skuDesc;

    private BigDecimal weight;

    private Integer tmId;

    private Integer catalog3Id;

    private String skuDefaultImg;
    @Transient
    List<PmsSkuAttValue> pmsSkuAttValueList;
    @Transient
    List<PmsSkuImage> pmsSkuImages;
    @Transient
    List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc == null ? null : skuDesc.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getTmId() {
        return tmId;
    }

    public void setTmId(Integer tmId) {
        this.tmId = tmId;
    }

    public Integer getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Integer catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg == null ? null : skuDefaultImg.trim();
    }

    public List<PmsSkuAttValue> getPmsSkuAttValueList() {
        return pmsSkuAttValueList;
    }

    public void setPmsSkuAttValueList(List<PmsSkuAttValue> pmsSkuAttValueList) {
        this.pmsSkuAttValueList = pmsSkuAttValueList;
    }

    public List<PmsSkuImage> getPmsSkuImages() {
        return pmsSkuImages;
    }

    public void setPmsSkuImages(List<PmsSkuImage> pmsSkuImages) {
        this.pmsSkuImages = pmsSkuImages;
    }

    public List<PmsSkuSaleAttrValue> getPmsSkuSaleAttrValues() {
        return pmsSkuSaleAttrValues;
    }

    public void setPmsSkuSaleAttrValues(List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues) {
        this.pmsSkuSaleAttrValues = pmsSkuSaleAttrValues;
    }
}
