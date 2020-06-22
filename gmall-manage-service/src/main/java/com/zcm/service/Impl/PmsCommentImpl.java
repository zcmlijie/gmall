package com.zcm.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zcm.bean.PageBean;
import com.zcm.bean.PmsComment;
import com.zcm.bean.PmsProductInfo;
import com.zcm.bean.PmsReplay;
import com.zcm.dao.PmsCommentMapper;
import com.zcm.dao.PmsProductInfoMapper;
import com.zcm.dao.PmsReplayMapper;
import com.zcm.service.PmsCommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmsCommentImpl implements PmsCommentService {
  @Autowired
  PmsCommentMapper pmsCommentMapper;
  @Autowired
  PmsProductInfoMapper pmsProductInfoMapper;
  @Autowired
  PmsReplayMapper pmsReplayMapper;
  @Override
  public Integer savePmsComment(PmsComment pmsComment) {
    if(pmsComment!=null){
      pmsComment.setCreateTime(new Date());
      pmsComment.setCollectCount(0);//点赞数目
      pmsComment.setId(null);
      pmsComment.setMemberIp("1");
      pmsComment.setMemberNickName("张三");
      pmsComment.setReadCount(0);//浏览数
      pmsComment.setReplayCount(0);//评论数
      pmsComment.setShowStatus("0");//显示状态==0待审核1通过2不通过
      PmsProductInfo pmsProductInfo=pmsProductInfoMapper.selectByPrimaryKey(pmsComment.getProductId());
      pmsComment.setProductName(pmsProductInfo.getProductName());
      Integer row=pmsCommentMapper.insert(pmsComment);
      if(row>=1){
        return row;
      }
    }
    return null;
  }

  @Override
  public Integer savePmsReplay(PmsReplay pmsReplay) {
    if(pmsReplay!=null){
     pmsReplay.setCreateTime(new Date());
     Integer row=pmsReplayMapper.insert(pmsReplay);
     if(row>0){
       PmsComment pmsComment = pmsCommentMapper.selectByPrimaryKey(pmsReplay.getCommentId());
       if(pmsComment!=null){
         Integer replayCount=pmsComment.getReplayCount();
         replayCount++;
         pmsComment.setReplayCount(replayCount);
         Integer updataRow=pmsCommentMapper.updateByPrimaryKey(pmsComment);
         return updataRow;
       }

     }
    }
    return null;
  }

  @Override
  public PageBean<PmsComment> getPageBeanPmsCommentList(Integer start) {
    PageBean<PmsComment> pmsCommentPageBean=new PageBean<>();
    pmsCommentPageBean.setCurrPage(start);
    Integer size=5;
    pmsCommentPageBean.setPageSize(size);
    Integer countRow=pmsCommentMapper.PmsCountCount();
    pmsCommentPageBean.setTotalCount(countRow);
    //总页数
    double ct=countRow;
    Double num=Math.ceil(ct/size);
    pmsCommentPageBean.setTotalPage(num.intValue());
    Map<String,Object> map=new HashMap<>();
    map.put("start",(start-1)*size);
    map.put("size",size);
    List<PmsComment> pmsCommentList=pmsCommentMapper.selectAll(map);
    if(pmsCommentList!=null&& pmsCommentList.size()>0) {
      for (PmsComment ps : pmsCommentList) {
        List<PmsReplay> pmsReplayList = pmsReplayMapper.selectByCommentId(ps.getId());
        ps.setReplayList(pmsReplayList);
      }
    }
    pmsCommentPageBean.setLists(pmsCommentList);
    return pmsCommentPageBean;
  }

}
