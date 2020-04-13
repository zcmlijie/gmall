package com.zcm.dao;

import com.zcm.bean.PmsProductImage;

import java.util.List;

public interface PmsProductImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsProductImage record);

    int insertSelective(PmsProductImage record);

    PmsProductImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsProductImage record);

    int updateByPrimaryKey(PmsProductImage record);

    int insertBatch(List<PmsProductImage> pmsProductImage);
}
