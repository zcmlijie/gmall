package com.zcm.dao;

import com.zcm.bean.RedPacketInfo;

public interface RedPacketInfoMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 发红包
     * @param record
     * @return
     */
    int insert(RedPacketInfo record);

    int insertSelective(RedPacketInfo record);

    RedPacketInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RedPacketInfo record);

    int updateByPrimaryKey(RedPacketInfo record);
}
