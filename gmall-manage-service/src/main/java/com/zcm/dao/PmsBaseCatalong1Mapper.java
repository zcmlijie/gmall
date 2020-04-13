package com.zcm.dao;

import com.zcm.bean.PmsBaseCatalong1;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PmsBaseCatalong1Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsBaseCatalong1 record);

    int insertSelective(PmsBaseCatalong1 record);

    PmsBaseCatalong1 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsBaseCatalong1 record);

    int updateByPrimaryKey(PmsBaseCatalong1 record);

    List<PmsBaseCatalong1>  getPmsBaseCatalong1();
}
