package com.zcm.dao;

import com.zcm.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductSaleAttrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsProductSaleAttr record);

    int insertSelective(PmsProductSaleAttr record);

    PmsProductSaleAttr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsProductSaleAttr record);

    int updateByPrimaryKey(PmsProductSaleAttr record);

    Integer insertBatch(List<PmsProductSaleAttr> pmsProductSaleAttr);

    List<PmsProductSaleAttr> slectPmsProductSaleAttr(Integer productId);

    List<PmsProductSaleAttr> selectPmsProductSaleAttrBySkuIdAndProductId(@Param("skuId") Integer skuId, @Param("productId") Integer productId);
}
