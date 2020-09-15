package com.zcm.dao;

import com.zcm.bean.RedPacketRecord;

public interface RedPacketRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RedPacketRecord record);

    int insertSelective(RedPacketRecord record);

    RedPacketRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RedPacketRecord record);

    int updateByPrimaryKey(RedPacketRecord record);
}
