package com.zcm.dao;

import com.zcm.bean.PmsBaseCatalong2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsBaseCatalong2Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsBaseCatalong2 record);

    int insertSelective(PmsBaseCatalong2 record);

    PmsBaseCatalong2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsBaseCatalong2 record);

    int updateByPrimaryKey(PmsBaseCatalong2 record);

    /**
     * 获取第二分类类型
     * @param id1 第一分类id
     * @return
     */

    List<PmsBaseCatalong2> getPmsBaseCatalog2list(Integer id1);
}
