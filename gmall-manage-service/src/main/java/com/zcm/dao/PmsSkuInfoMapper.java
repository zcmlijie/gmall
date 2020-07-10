package com.zcm.dao;

import com.zcm.bean.PmsSkuInfo;

import java.util.List;
import java.util.Map;

public interface PmsSkuInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsSkuInfo record);

    int insertSelective(PmsSkuInfo record);

    PmsSkuInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsSkuInfo record);

    int updateByPrimaryKey(PmsSkuInfo record);

    List<PmsSkuInfo> selectPmsSkuSaleAttrBySpuId(Integer productId);

    /**
     * 查询商品的销售属性名称和id
     * @param productId
     * @return
     */
    List<Map<String,String>> selectPmsSkuSaleAttrName(Integer productId);
}
