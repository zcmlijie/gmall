package com.zcm.dao;

import com.zcm.bean.PmsSkuAttValue;

import java.util.List;

public interface PmsSkuAttValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsSkuAttValue record);

    int insertSelective(PmsSkuAttValue record);

    PmsSkuAttValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsSkuAttValue record);

    int updateByPrimaryKey(PmsSkuAttValue record);

    //通过skuId 获取sku的平台属性
    List<PmsSkuAttValue> selectPmsSkuAttValueBySkuId(Integer skuId);
}
