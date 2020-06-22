package com.zcm.dao;

import com.zcm.bean.PmsSkuInfo;

public interface PmsSkuInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsSkuInfo record);

    int insertSelective(PmsSkuInfo record);

    PmsSkuInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsSkuInfo record);

    int updateByPrimaryKey(PmsSkuInfo record);
}
