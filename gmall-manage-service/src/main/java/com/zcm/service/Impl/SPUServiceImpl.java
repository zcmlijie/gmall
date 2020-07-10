package com.zcm.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zcm.bean.*;
import com.zcm.dao.PmsProductImageMapper;
import com.zcm.dao.PmsProductInfoMapper;
import com.zcm.dao.PmsProductSaleAttrMapper;
import com.zcm.dao.PmsProductSaleAttrValueMapper;
import com.zcm.service.SPUServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SPUServiceImpl implements SPUServcie {
   @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;
   @Autowired
    PmsProductImageMapper pmsProductImageMapper;
   @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
   @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Override
    public PageBean<PmsProductInfo> getPmsProductInfos(String name, Integer id, Integer curr) {
        PageBean pageBean=new PageBean();
        pageBean.setCurrPage(curr);
        int size=2;
        pageBean.setPageSize(size);
        Integer count=pmsProductInfoMapper.PageBeanCount(name,id);
        pageBean.setTotalCount(count);
        //总页数
        double ct=count;
        Double num=Math.ceil(ct/size);
        pageBean.setTotalPage(num.intValue());
        HashMap<String,Object> map=new HashMap<>();
        map.put("start",(curr-1)*size);
        map.put("size",size);
        map.put("name",name);
        map.put("id",id);
        List<PmsProductInfo> lsit=pmsProductInfoMapper.getPmsProductPageBean(map);
        for(PmsProductInfo ps:lsit){
            ps.setPmsProductSaleAttrList
                    (pmsProductSaleAttrMapper.slectPmsProductSaleAttr(ps.getId()));
            for(PmsProductSaleAttr pa:ps.getPmsProductSaleAttrList()){
                pa.setPmsProductSaleAttrValueList(pmsProductSaleAttrValueMapper.
                        selectPmsProductSaleAttrValue(ps.getId(),pa.getId()));
            }
        }
        pageBean.setLists(lsit);
        return pageBean;
    }

    @Override
    public List<PmsBaseSaleAtt> getPmsSaleAtt() {
       List<PmsBaseSaleAtt> pmsBaseSaleAtt=pmsProductInfoMapper.selctAll();
        return pmsBaseSaleAtt;
    }

    @Override
    public Integer savaListAll(PmsBaseAttInfo pmsBaseAttInfo) {

        return null;
    }

    @Override
    public Integer savaBatch(List<PmsProductImage> pmsProductImage) {
        Integer row=pmsProductImageMapper.insertBatch(pmsProductImage);
        return row;
    }

    /**
     * 添加spu
     * @param pmsProductInfo
     * @return
     */
    @Override
    @Transactional
    public Integer savePmsProcuct(PmsProductInfo pmsProductInfo) {
        if(pmsProductInfo!=null){
            Integer pId=0;
            Integer pmsProductInfoRow=pmsProductInfoMapper.insert(pmsProductInfo);
            if(pmsProductInfoRow>0){
                pId=pmsProductInfo.getId();

            }
            //获取spu销售属性
            List<PmsProductSaleAttr> pmsProductSaleAttrs=pmsProductInfo.getPmsProductSaleAttrList();
            for (PmsProductSaleAttr pm:pmsProductSaleAttrs){
                pm.setProductId(pId);
            }
            Integer pmsProductSaleAttrRow=pmsProductSaleAttrMapper.insertBatch(pmsProductSaleAttrs);

            if(pmsProductSaleAttrRow>0){
                for(PmsProductSaleAttr ps:pmsProductSaleAttrs){
                    //获取spu销售属性值
                    List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList=ps.getPmsProductSaleAttrValueList();
                    for(PmsProductSaleAttrValue pv:pmsProductSaleAttrValueList){
                        pv.setSaleAttrId(ps.getId());
                        pv.setProductId(ps.getProductId());
                    }
                    Integer pmsProductSaleAttrValueRow=pmsProductSaleAttrValueMapper.insertBatch(pmsProductSaleAttrValueList);

                }
            }

        }

        return null;
    }

    @Override
    public List<PmsProductSaleAttr> getPmsProductSaleAttrBySkuIdAndProductId(Integer skuId, Integer productId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrs=pmsProductSaleAttrMapper.selectPmsProductSaleAttrBySkuIdAndProductId(skuId, productId);
        return pmsProductSaleAttrs;
    }
}
