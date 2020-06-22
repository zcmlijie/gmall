package com.zcm.dao;

import com.zcm.bean.PmsSkuAttValue;

public interface PmsSkuAttValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsSkuAttValue record);

    int insertSelective(PmsSkuAttValue record);

    PmsSkuAttValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsSkuAttValue record);

    int updateByPrimaryKey(PmsSkuAttValue record);
}
