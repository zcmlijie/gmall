package com.zcm.dao;

import com.zcm.bean.PmsComment;

import java.util.List;
import java.util.Map;

public interface PmsCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsComment record);

    int insertSelective(PmsComment record);

    PmsComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsComment record);

    int updateByPrimaryKey(PmsComment record);

    List<PmsComment> selectAll(Map<String,Object> map);

    Integer PmsCountCount();
}
