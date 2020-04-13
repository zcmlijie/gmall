package com.zcm.dao;

import com.zcm.bean.PmsBaseAttInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PmsBaseAttInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsBaseAttInfo record);

    int insertSelective(PmsBaseAttInfo record);

    PmsBaseAttInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsBaseAttInfo record);

    int updateByPrimaryKey(PmsBaseAttInfo record);

    List<PmsBaseAttInfo> getPmsBaseAtt(Integer id3);

    List<PmsBaseAttInfo> selectAllPageBean(Map<String,Object> map);
    List<PmsBaseAttInfo> selectList();
    Integer count();

}
