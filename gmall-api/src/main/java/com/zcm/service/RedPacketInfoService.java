package com.zcm.service;

import com.zcm.bean.RedPacketInfo;
import com.zcm.bean.RedPacketRecord;

public interface RedPacketInfoService {
  //发红包
  int addRedPacketInfo(RedPacketInfo redPacketInfo);
  //更新抢红包记录
  int addRedPacketRecord(RedPacketRecord redPacketRecord);
}
