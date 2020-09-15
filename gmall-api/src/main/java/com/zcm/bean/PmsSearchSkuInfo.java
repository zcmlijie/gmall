package com.zcm.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PmsSearchSkuInfo implements Serializable {
  private  String id;
  private BigDecimal price;//商品价格
  private String skuName;//sku名称
  private String skuDesc;// sku描述
  private String catalog3Id;//三级分类
  private String skuDefaultImg;//图片路径
  private BigDecimal hotScore;//热门值
  private Long productId;//商品id
  List<PmsSkuAttValue> pmsSkuAttValueList;//平台属性值和对应的列表

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getSkuName() {
    return skuName;
  }

  public void setSkuName(String skuName) {
    this.skuName = skuName;
  }

  public String getSkuDesc() {
    return skuDesc;
  }

  public void setSkuDesc(String skuDesc) {
    this.skuDesc = skuDesc;
  }

  public String getCatalog3Id() {
    return catalog3Id;
  }

  public void setCatalog3Id(String catalog3Id) {
    this.catalog3Id = catalog3Id;
  }

  public String getSkuDefaultImg() {
    return skuDefaultImg;
  }

  public void setSkuDefaultImg(String skuDefaultImg) {
    this.skuDefaultImg = skuDefaultImg;
  }

  public BigDecimal getHotScore() {
    return hotScore;
  }

  public void setHotScore(BigDecimal hotScore) {
    this.hotScore = hotScore;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public List<PmsSkuAttValue> getPmsSkuAttValueList() {
    return pmsSkuAttValueList;
  }

  public void setPmsSkuAttValueList(List<PmsSkuAttValue> pmsSkuAttValueList) {
    this.pmsSkuAttValueList = pmsSkuAttValueList;
  }
}
