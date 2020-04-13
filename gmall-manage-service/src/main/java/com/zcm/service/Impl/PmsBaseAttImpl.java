package com.zcm.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zcm.bean.PageBean;
import com.zcm.bean.PmsBaseAttInfo;
import com.zcm.bean.PmsBaseAttValue;
import com.zcm.dao.PmsBaseAttInfoMapper;
import com.zcm.dao.PmsBaseAttValueMapper;
import com.zcm.service.PmsBaseAttService;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmsBaseAttImpl implements PmsBaseAttService {
    @Autowired
    PmsBaseAttInfoMapper pmsBaseAttInfoMapper;
    @Autowired
    PmsBaseAttValueMapper pmsBaseAttValueMapper;
    @Override
    public List<PmsBaseAttInfo> getPmsBaseAtt(Integer id3) {
        List<PmsBaseAttInfo> list=pmsBaseAttInfoMapper.getPmsBaseAtt(id3);
        return list;
    }

    @Override
   @Transactional
    public void savePmsBaseAtt(PmsBaseAttInfo pmsBaseAttInfo) {
      if(StringUtils.isBlank(pmsBaseAttInfo.getId().toString())){
          //判断传入id是否为空：是
          PmsBaseAttInfo pmsBaseAttInfo1=new PmsBaseAttInfo();
          pmsBaseAttInfo1.setAttrName(pmsBaseAttInfo.getAttrName());
          pmsBaseAttInfo1.setCatalog3Id(pmsBaseAttInfo.getCatalog3Id());
          pmsBaseAttInfo1.setIsEnabled(null);
          Integer cont=pmsBaseAttInfoMapper.insert(pmsBaseAttInfo1);
          Integer infoId=pmsBaseAttInfo1.getId();
          List<PmsBaseAttValue> list=pmsBaseAttInfo.getAttValuesList();
          for(PmsBaseAttValue id:list){
              id.setAttrId(infoId);
          }
          Integer row=pmsBaseAttValueMapper.insertAllValue(list);

      }else {
          PmsBaseAttInfo pmsBaseAttInfo1=new PmsBaseAttInfo();
          pmsBaseAttInfo1.setId(pmsBaseAttInfo.getId());
          pmsBaseAttInfo1.setAttrName(pmsBaseAttInfo.getAttrName());
          Integer row=pmsBaseAttInfoMapper.updateByPrimaryKey(pmsBaseAttInfo1);
          //根据attid查询
          //List<PmsBaseAttValue> pmsValue=pmsBaseAttValueMapper.PmsBaseAttLists(pmsBaseAttInfo.getId());
          List<PmsBaseAttValue> list=pmsBaseAttInfo.getAttValuesList();
          Integer cont=pmsBaseAttValueMapper.updateAllValue(list);
      }
    }

    /**
     * 分页查询
     * @param curr
     * @return
     */
    @Override
    public PageBean<PmsBaseAttInfo> getPmsBaseAttPageBean(Integer curr) {
        PageBean pageBean=new PageBean();
        PmsBaseAttInfo pmsBaseAttInfo=new PmsBaseAttInfo();
        pageBean.setCurrPage(curr);
        int size=3;
        pageBean.setPageSize(size);
        //总条数
        Integer count=pmsBaseAttInfoMapper.count();
        pageBean.setTotalCount(count);
        //总页数
        double ct=count;
        Double row=Math.ceil(ct/size);
        pageBean.setTotalPage(row.intValue());
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("start",(curr-1)*size);
        map.put("size",size);
        List<PmsBaseAttInfo> list=pmsBaseAttInfoMapper.selectAllPageBean(map);
        //List listId=new ArrayList();
        for (PmsBaseAttInfo li:list){
            List<PmsBaseAttValue> listValue=pmsBaseAttValueMapper.PmsBaseAttLists(li.getId());
            li.setAttValuesList(listValue);
        }
        pageBean.setLists(list);
        return pageBean;
    }
}
