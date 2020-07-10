package com.zcm.service;

import com.zcm.bean.PmsBaseAttInfo;
import com.zcm.bean.PmsProductSaleAttr;
import com.zcm.bean.PmsSkuImage;
import com.zcm.bean.PmsSkuInfo;

import java.util.List;
import java.util.Map;

public interface SKUService {
    List<PmsBaseAttInfo> getPmsBaseAttInfo();

    List<PmsProductSaleAttr> getPmsProductSaleAtt(Integer productId);

    /**
     * 上传sku图片
     * @param pmsSkuImage
     */
    void savePmsSkuImage(PmsSkuImage pmsSkuImage);

    /**
     * 保存sku数据
     * @param pmsSkuInfo
     */
    void svaePmsSkuInfo(PmsSkuInfo pmsSkuInfo);

    /**
     * 根据skuid获取sku详情信息
     * @param skuId
     * @return
     */
    PmsSkuInfo getSkuInfoBySkuId(Integer skuId);

    List<PmsSkuInfo> getSkuInfoByProductId(Integer productId);

    List<Map<String,String>> getSaleAttrName(Integer productId);

}
