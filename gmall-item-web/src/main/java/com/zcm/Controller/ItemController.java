package com.zcm.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.zcm.bean.PmsProductSaleAttr;
import com.zcm.bean.PmsSkuInfo;
import com.zcm.bean.PmsSkuSaleAttrValue;
import com.zcm.service.SKUService;
import com.zcm.service.SPUServcie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller

public class ItemController {
    @Reference
    SKUService skuService;
    @Reference
    SPUServcie spuServcie;
    @RequestMapping("/{skuId}.html")
    public String item(@PathVariable("skuId") String skuId, Model model){
        PmsSkuInfo pmsSkuInfo = skuService.getSkuInfoBySkuId(Integer.parseInt(skuId));
        ModelMap modelMap = new ModelMap();
        Map<String,Object> map=new HashMap<String,Object>();
        if (pmsSkuInfo != null) {
            List<PmsProductSaleAttr> pmsProductSaleAttrs = spuServcie.
                    getPmsProductSaleAttrBySkuIdAndProductId(Integer.parseInt(skuId), pmsSkuInfo.getProductId());
            List<PmsSkuInfo> pmsSkuInfoList = skuService.getSkuInfoByProductId(pmsSkuInfo.getProductId());
            for(PmsSkuInfo pmsSku:pmsSkuInfoList){
                List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues=pmsSku.getPmsSkuSaleAttrValues();
                String k="";
                String v="";
                for(PmsSkuSaleAttrValue ps:pmsSkuSaleAttrValues){
                  k+=ps.getSaleAttrValueId();
                  k+="|";
                }
                v=pmsSku.getId().toString();
                map.put(k,v);
            }
            JSONObject json = new JSONObject(map);
            List<Map<String,String>> list=skuService.getSaleAttrName(pmsSkuInfo.getProductId());
            String saleAttrName="";
            for(Map<String,String> ma:list){
                saleAttrName+=ma.get("sale_attr_name");
                saleAttrName+=",";
            }
            saleAttrName=saleAttrName.substring(0,saleAttrName.length()-1);
            modelMap.put("map",map);
            modelMap.put("saleAttrName",saleAttrName);
            modelMap.put("data", pmsSkuInfo);
            modelMap.put("data1", pmsProductSaleAttrs);
            modelMap.put("code", 200);
        } else {
            modelMap.put("data", null);
            modelMap.put("code", -1);
        }
        return "shangpinxiangqing";
    }
    @RequestMapping(name = "/index")
    public String index(ModelMap modelMap){
        List<String> lsit=new ArrayList<String>();
        for (int i=0;i<5;i++){
            lsit.add("循环的值:"+i);
        }
        modelMap.put("check",0);
        modelMap.put("list",lsit);
        modelMap.put("hello","helloWorld");
        return "index";
    }
}
