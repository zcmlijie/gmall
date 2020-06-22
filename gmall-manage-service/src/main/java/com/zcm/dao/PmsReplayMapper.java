package com.zcm.dao;

import com.zcm.bean.PmsReplay;

import java.util.List;

public interface PmsReplayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsReplay record);

    int insertSelective(PmsReplay record);

    PmsReplay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsReplay record);

    int updateByPrimaryKey(PmsReplay record);

    List<PmsReplay> selectByCommentId(Integer commentId);
}
