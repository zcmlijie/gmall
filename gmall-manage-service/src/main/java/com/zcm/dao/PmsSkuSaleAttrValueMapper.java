package com.zcm.dao;

import com.zcm.bean.PmsSkuSaleAttrValue;

import java.util.List;

public interface PmsSkuSaleAttrValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsSkuSaleAttrValue record);

    int insertSelective(PmsSkuSaleAttrValue record);

    PmsSkuSaleAttrValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsSkuSaleAttrValue record);

    int updateByPrimaryKey(PmsSkuSaleAttrValue record);

    List<PmsSkuSaleAttrValue> selectSkuSaleAttrValeu(Integer skuId);
}
