package com.zcm.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcm.bean.RedPacketInfo;
import com.zcm.bean.RedPacketRecord;
import com.zcm.service.RedPacketInfoService;
import com.zcm.util.RedisUtil;
import com.zcm.util.SonwFlake;
import message.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class RedPacketInfoContrller {
  @Reference
  RedPacketInfoService redPacketInfoService;
  @Autowired
  RedisUtil redisUtil;
  @RequestMapping(value = "/addRedPacketInfo",method = RequestMethod.POST)
  @ResponseBody
  public Map<String, R> addRedPacketInfo(@RequestBody RedPacketInfo redPacket){
    Map<String,R> map=new HashMap<>();
    if(redPacket!=null){
      RedPacketInfo redPacketInfo=new RedPacketInfo();
      SonwFlake sonwFlake=new SonwFlake(2,3);
      redPacketInfo.setId(null);
      redPacketInfo.setCreateTime(new Date());
      Long redPacketId=sonwFlake.nextId();
      redPacketInfo.setRedPacketId(redPacketId);//红包id，分布式中用雪花算法
      redPacketInfo.setRemainingAmount(redPacket.getRemainingAmount());//剩余红包⾦额
      redPacketInfo.setRemainingPacket(redPacket.getRemainingPacket());//剩余红包个数
      redPacketInfo.setTotalAmount(redPacket.getTotalAmount());//红包总⾦额
      redPacketInfo.setTotalPacket(redPacket.getTotalPacket());//红包总个数
      redPacketInfo.setUid(redPacket.getUid());//新建红包⽤户的⽤户标识
      redPacketInfo.setUpdateTime(new Date());
      //往db中存放发红包的记录
      int i = redPacketInfoService.addRedPacketInfo(redPacketInfo);
      if(i>0){
       //往redis中插入两条记录
        //红包的个数和金额
        redisUtil.set(redPacketId+"_totalnum",redPacketInfo.getTotalPacket().toString(),0);
        redisUtil.set(redPacketId+"_totalAmount",redPacketInfo.getRemainingAmount().toString(),0);
        map.put("操作成功",R.ok().message("succes"));
        return map;
      }
    }
    return null;
  }
  //抢红包
  @RequestMapping(value = "/getRedPacket",method = RequestMethod.POST)
  @ResponseBody
  public Map<String,R> getRedPacket(Long redPacketId,String nikName,Integer uid){
    Map<String,R> map=new HashMap<>();
    Integer amount=0;
    String RedPakcetNum=redPacketId+"_totalnum";
    String RedPacketAmount=redPacketId+"_totalAmount";
    if(redisUtil.exists(RedPakcetNum)){
      //获取红包数量
      Integer num = Integer.valueOf(redisUtil.get(RedPakcetNum, 0));
      if(num!=null&&num>0){
        //红包数量减1
        redisUtil.decr(RedPakcetNum);
        if(redisUtil.exists(RedPacketAmount)){
           amount = Integer.valueOf(redisUtil.get(RedPacketAmount, 0));//红包金额
          //最大红包金额
          Integer maxAmount=amount/Integer.valueOf(num)*2;
          Random random=new Random();
          amount=num==1?amount:random.nextInt(maxAmount);
          //红包金额指定减值
          redisUtil.decrBy(RedPacketAmount, amount.longValue());
        }
        //更新抢红包记录表
        int i = updateRedPacketRecord(amount, nikName, uid, redPacketId);
        if(i>0){
          map.put("message",R.ok().message("抢到啦"+amount));
          return map;
        }
      }else {
        map.put("message",R.error().message("红包已经抢完了"));
        return map;
      }
    }else {
      map.put("message",R.error().message("红包不存在"));
      return map;
    }
    return null;
  }
   public int updateRedPacketRecord(Integer amount,String nickName,Integer uid,Long redPacketId){
     RedPacketRecord redPacketRecord=new RedPacketRecord();
     redPacketRecord.setAmount(amount);
     redPacketRecord.setCreateTime(new Date());
     redPacketRecord.setImgUrl("http://localhost:9090");
     redPacketRecord.setNickName(nickName);
     redPacketRecord.setUid(uid);
     redPacketRecord.setRedPacketId(redPacketId);
     redPacketRecord.setUpdateTime(new Date());
     int i = redPacketInfoService.addRedPacketRecord(redPacketRecord);
     if(i>0){
       return 1;
     }
     return 0;
   }
}
