package com.zcm.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zcm.bean.PmsBaseAttInfo;
import com.zcm.bean.PmsBaseAttValue;
import com.zcm.dao.PmsBaseAttInfoMapper;
import com.zcm.dao.PmsBaseAttValueMapper;
import com.zcm.service.SKUService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class SKUServiceImpl implements SKUService {
    @Autowired
    PmsBaseAttInfoMapper pmsBaseAttInfoMapper;
    @Autowired
    PmsBaseAttValueMapper pmsBaseAttValueMapper;

    /**
     * sku的平台属性和属性值
     * @return
     */
    @Override
    public List<PmsBaseAttInfo> getPmsBaseAttInfo() {
       List<PmsBaseAttInfo> pmsBaseAttInfoList=pmsBaseAttInfoMapper.selectList();
       for(PmsBaseAttInfo li:pmsBaseAttInfoList){
           List<PmsBaseAttValue> pmsBaseAttValueList=pmsBaseAttValueMapper.PmsBaseAttLists(li.getId());
           li.setAttValuesList(pmsBaseAttValueList);
       }
        return pmsBaseAttInfoList;
    }
}
