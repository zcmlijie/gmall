package com.zcm.service;

import com.zcm.bean.PmsBaseAttInfo;
import com.zcm.bean.PmsProductSaleAttr;
import com.zcm.bean.PmsSkuImage;

import java.util.List;

public interface SKUService {
    List<PmsBaseAttInfo> getPmsBaseAttInfo();

    List<PmsProductSaleAttr> getPmsProductSaleAtt(Integer productId);

    /**
     * 上传sku图片
     * @param pmsSkuImage
     */
    void savePmsSkuImage(PmsSkuImage pmsSkuImage);
}
