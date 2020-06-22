package com.zcm.service;

import com.zcm.bean.PageBean;
import com.zcm.bean.PmsComment;
import com.zcm.bean.PmsReplay;
import sun.rmi.server.InactiveGroupException;

/**
 * 商品的评论
 */
public interface PmsCommentService {
  /**
   * 添加评论
   * @param pmsComment
   * @return
   */
  Integer savePmsComment(PmsComment pmsComment);

  /**
   * 回复
   * @param pmsReplay
   * @return
   */
  Integer savePmsReplay(PmsReplay pmsReplay);

  /**
   * 获取评论回复数据
   * @return
   */
  PageBean<PmsComment> getPageBeanPmsCommentList(Integer start);
}
