package com.zcm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcm.bean.PmsBaseAttInfo;
import com.zcm.service.SKUService;

import message.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SKUController {
    @Reference
    SKUService skuService;
    @RequestMapping(value = "/getPms",method = RequestMethod.POST)
    public R getPmsBaseAttInfo(){
        List<PmsBaseAttInfo> pmsBaseAttInfoList=skuService.getPmsBaseAttInfo();
        return R.ok().data("data",pmsBaseAttInfoList).message("sku平台属性");
    }

}
