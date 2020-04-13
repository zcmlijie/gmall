package com.zcm.dao;

import com.zcm.bean.PmsProductSaleAttr;

import java.util.List;

public interface PmsProductSaleAttrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsProductSaleAttr record);

    int insertSelective(PmsProductSaleAttr record);

    PmsProductSaleAttr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsProductSaleAttr record);

    int updateByPrimaryKey(PmsProductSaleAttr record);

    Integer insertBatch(List<PmsProductSaleAttr> pmsProductSaleAttr);
}
