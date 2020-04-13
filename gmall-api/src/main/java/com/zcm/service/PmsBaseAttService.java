package com.zcm.service;

import com.zcm.bean.PageBean;
import com.zcm.bean.PmsBaseAttInfo;

import java.util.List;

public interface PmsBaseAttService {
    List<PmsBaseAttInfo> getPmsBaseAtt(Integer id3);
    void savePmsBaseAtt(PmsBaseAttInfo pmsBaseAttInfo);
    PageBean<PmsBaseAttInfo> getPmsBaseAttPageBean(Integer curr);
}
