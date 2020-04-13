package com.zcm.dao;

import com.zcm.bean.PmsBaseAttInfo;
import com.zcm.bean.PmsBaseAttValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsBaseAttValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsBaseAttValue record);

    int insertSelective(PmsBaseAttValue record);

    PmsBaseAttValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsBaseAttValue record);

    int updateByPrimaryKey(PmsBaseAttValue record);

    Integer insertAllValue( List<PmsBaseAttValue> attValue);

    Integer updateAllValue(List<PmsBaseAttValue> attValue);

    List<PmsBaseAttValue> PmsBaseAttLists(Integer attId);

    List<PmsBaseAttValue> selectByAttId(List<Integer> attId);
}
