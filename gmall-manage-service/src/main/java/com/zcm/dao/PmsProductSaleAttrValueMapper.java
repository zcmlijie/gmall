package com.zcm.dao;

import com.zcm.bean.PmsProductSaleAttrValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductSaleAttrValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsProductSaleAttrValue record);

    int insertSelective(PmsProductSaleAttrValue record);

    PmsProductSaleAttrValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsProductSaleAttrValue record);

    int updateByPrimaryKey(PmsProductSaleAttrValue record);

    Integer insertBatch(List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList);

    List<PmsProductSaleAttrValue> selectPmsProductSaleAttrValue(@Param("productId") Integer productInd, @Param("saleId") Integer saleId);
}
