package com.zcm.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zcm.bean.PmsBaseCatalong1;
import com.zcm.bean.PmsBaseCatalong2;
import com.zcm.bean.PmsBaseCatalong3;
import com.zcm.dao.PmsBaseCatalong1Mapper;
import com.zcm.dao.PmsBaseCatalong2Mapper;
import com.zcm.dao.PmsBaseCatalong3Mapper;
import com.zcm.service.PmsBaseCatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PmsBaseCatalogImpl implements PmsBaseCatalogService {
   @Autowired
    PmsBaseCatalong1Mapper pmsBaseCatalong1Mapper;
   @Autowired
    PmsBaseCatalong2Mapper pmsBaseCatalong2Mapper;
   @Autowired
    PmsBaseCatalong3Mapper pmsBaseCatalong3Mapper;
    @Override
    public List<PmsBaseCatalong1> getPmsBaseCatalog1() {
      List<PmsBaseCatalong1> list=pmsBaseCatalong1Mapper.getPmsBaseCatalong1();
        return list;
    }

    @Override
    public List<PmsBaseCatalong2> getPmsBaseCatalog2(Integer id2) {
        List<PmsBaseCatalong2> list=pmsBaseCatalong2Mapper.getPmsBaseCatalog2list(id2);
        return list;
    }

    @Override
    public List<PmsBaseCatalong3> getPmsBaseCatalog3(Integer id3) {
        List<PmsBaseCatalong3> list=pmsBaseCatalong3Mapper.getPmsBaseCatalogs3List(id3);
        return list;
    }
}
