package com.zcm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcm.bean.PmsBaseCatalong1;
import com.zcm.bean.PmsBaseCatalong2;
import com.zcm.bean.PmsBaseCatalong3;
import com.zcm.service.PmsBaseCatalogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.ReferenceQueue;
import java.util.List;

@RestController
@CrossOrigin//解决跨域注解
public class CatalogController {
   @Reference
    PmsBaseCatalogService pmsBaseCatalogService;
    @RequestMapping(value = "/getPmsBaseCatalog",method = RequestMethod.POST)
    public List<PmsBaseCatalong1> getPmsBaseCatalong(){
        List<PmsBaseCatalong1> list=pmsBaseCatalogService.getPmsBaseCatalog1();
        return list;
    }
@RequestMapping(value = "/getPmsBaseCatalog2",method = RequestMethod.POST)
   public List<PmsBaseCatalong2> getPmsBaseCatalog2(Integer id2){
        List<PmsBaseCatalong2> list=pmsBaseCatalogService.getPmsBaseCatalog2(id2);
        return list;
   }
   @RequestMapping(value = "/getPmsBaseCatalog3",method =RequestMethod.POST)
   public List<PmsBaseCatalong3> getPasBaseCatalog3(Integer id3){
        List<PmsBaseCatalong3> list=pmsBaseCatalogService.getPmsBaseCatalog3(id3);
        return list;
   }
}
