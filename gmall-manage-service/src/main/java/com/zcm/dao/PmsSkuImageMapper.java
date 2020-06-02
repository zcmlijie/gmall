package com.zcm.dao;

import com.zcm.bean.PmsSkuImage;

import java.util.List;

public interface PmsSkuImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsSkuImage record);

    int insertSelective(PmsSkuImage record);

    PmsSkuImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsSkuImage record);

    int updateByPrimaryKey(PmsSkuImage record);
    /**
     * 查询默认图片的数量
     */
    Integer slectConut();
    /**
     * 根据图片是否默认查询数据
     */
    List<PmsSkuImage> selectByisDefault(String isDefault);

}
