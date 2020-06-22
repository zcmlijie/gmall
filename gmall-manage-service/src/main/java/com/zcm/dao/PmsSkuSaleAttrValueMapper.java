package com.zcm.dao;

import com.zcm.bean.PmsSkuSaleAttrValue;

public interface PmsSkuSaleAttrValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsSkuSaleAttrValue record);

    int insertSelective(PmsSkuSaleAttrValue record);

    PmsSkuSaleAttrValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsSkuSaleAttrValue record);

    int updateByPrimaryKey(PmsSkuSaleAttrValue record);
}
