package com.zcm.dao;

import com.zcm.bean.PmsBaseCatalong3;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsBaseCatalong3Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsBaseCatalong3 record);

    int insertSelective(PmsBaseCatalong3 record);

    PmsBaseCatalong3 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsBaseCatalong3 record);

    int updateByPrimaryKey(PmsBaseCatalong3 record);

    /**
     * 获取第三类类型商品
     * @param id2 二类型商品id
     * @return
     */
    List<PmsBaseCatalong3> getPmsBaseCatalogs3List(Integer id2);
}
