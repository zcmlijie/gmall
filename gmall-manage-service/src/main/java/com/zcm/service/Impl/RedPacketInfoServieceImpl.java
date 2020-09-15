package com.zcm.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zcm.bean.RedPacketInfo;
import com.zcm.bean.RedPacketRecord;
import com.zcm.dao.RedPacketInfoMapper;
import com.zcm.dao.RedPacketRecordMapper;
import com.zcm.service.RedPacketInfoService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RedPacketInfoServieceImpl implements RedPacketInfoService {
  @Autowired
  RedPacketInfoMapper redPacketInfoMapper;
  @Autowired
  RedPacketRecordMapper redPacketRecordMapper;
  @Override
  public int addRedPacketInfo(RedPacketInfo redPacketInfo) {
    if(redPacketInfo!=null){
     int row= redPacketInfoMapper.insert(redPacketInfo);
     if(row>0){
       return 1;
     }
    }
    return 0;
  }

  @Override
  public int addRedPacketRecord(RedPacketRecord redPacketRecord) {
    if(redPacketRecord!=null){
      int row=redPacketRecordMapper.insert(redPacketRecord);
      if(row>0){
        return 1;
      }
    }
    return 0;
  }

}
