package com.zcm.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zcm.bean.*;
import com.zcm.dao.*;
import com.zcm.service.SKUService;
import message.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.server.InactiveGroupException;

import java.util.List;
@Service
public class SKUServiceImpl implements SKUService {
    @Autowired
    PmsBaseAttInfoMapper pmsBaseAttInfoMapper;
    @Autowired
    PmsBaseAttValueMapper pmsBaseAttValueMapper;
    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    PmsSkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Autowired
    PmsSkuAttValueMapper pmsSkuAttValueMapper;
    /**
     * sku的平台属性和属性值
     * @return
     */
    @Override
    public List<PmsBaseAttInfo> getPmsBaseAttInfo() {
       List<PmsBaseAttInfo> pmsBaseAttInfoList=pmsBaseAttInfoMapper.selectList();
       if(pmsBaseAttInfoList!=null){
           for(PmsBaseAttInfo li:pmsBaseAttInfoList){
               List<PmsBaseAttValue> pmsBaseAttValueList=pmsBaseAttValueMapper.PmsBaseAttLists(li.getId());
               li.setAttValuesList(pmsBaseAttValueList);
           }
           return pmsBaseAttInfoList;
       }else {
           throw new ServiceException();
       }
       }

    /**
     * 获取商品的销售属性
     * @param productId
     * @return
     */
    @Override
    public List<PmsProductSaleAttr> getPmsProductSaleAtt(Integer productId) {
        if(productId!=null){
            List<PmsProductSaleAttr> pmsProductSaleAttrs=
                    pmsProductSaleAttrMapper.slectPmsProductSaleAttr(productId);
            if(pmsProductSaleAttrs!=null){
                for(PmsProductSaleAttr ps:pmsProductSaleAttrs){
                    List<PmsProductSaleAttrValue> pmsProductSaleAttrValues=
                            pmsProductSaleAttrValueMapper.selectPmsProductSaleAttrValue(productId,ps.getId());
                    ps.setPmsProductSaleAttrValueList(pmsProductSaleAttrValues);
                }
            }
            return pmsProductSaleAttrs;
        }
        return null;
    }

    /**
     * 上传sku图片
     * @param pmsSkuImage
     */
    @Override
    public void savePmsSkuImage(PmsSkuImage pmsSkuImage) {
     if(pmsSkuImage!=null){
         if(pmsSkuImage.getId()==null){
             pmsSkuImage.setIsDefault("0");
             Integer row=pmsSkuImageMapper.insert(pmsSkuImage);
         }else {
             Integer countRow=pmsSkuImageMapper.slectConut();
             if(countRow>=1){
               List<PmsSkuImage> pmsSkuImageList=pmsSkuImageMapper.selectByisDefault("1");
               if(pmsSkuImageList!=null && pmsSkuImageList.size()>0){
                   for (PmsSkuImage pmsSkuImage1:pmsSkuImageList){
                       pmsSkuImage1.setIsDefault("0");
                       Integer row=pmsSkuImageMapper.updateByPrimaryKey(pmsSkuImage1);
                   }
               }
                 pmsSkuImage.setIsDefault("1");
                 Integer row=pmsSkuImageMapper.updateByPrimaryKey(pmsSkuImage);
             }else {
                 pmsSkuImage.setIsDefault("1");
                 Integer row=pmsSkuImageMapper.updateByPrimaryKey(pmsSkuImage);
             }
         }

     }
    }

    /**
     * 保存sku数据
     * @param pmsSkuInfo
     */
    @Override
    @Transactional
    public void svaePmsSkuInfo(PmsSkuInfo pmsSkuInfo) {
        if(pmsSkuInfo!=null){
           if(pmsSkuInfo.getId()!=null){
             //修改
            Integer row=pmsSkuInfoMapper.updateByPrimaryKey(pmsSkuInfo);
           } else {
              //保存
               Integer row=pmsSkuInfoMapper.insert(pmsSkuInfo);
               if(row>0){
                   Integer skuId=pmsSkuInfo.getId();
                   List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues=pmsSkuInfo.getPmsSkuSaleAttrValues();
                   if(pmsSkuSaleAttrValues!=null&&pmsSkuSaleAttrValues.size()>0){
                       for(PmsSkuSaleAttrValue pmsSkuSaleAttrValue:pmsSkuSaleAttrValues){
                           pmsSkuSaleAttrValue.setSkuId(skuId);
                           Integer pmsSkuSaleAttrValueRow=skuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
                       }
                   }
                  List<PmsSkuAttValue> pmsSkuAttValues=pmsSkuInfo.getPmsSkuAttValueList();
                   if(pmsSkuAttValues!=null&&pmsSkuAttValues.size()>0){
                       for(PmsSkuAttValue pmsSkuAttValue:pmsSkuAttValues){
                           pmsSkuAttValue.setSkuId(skuId);
                           Integer pmsSkuAttValueRow=pmsSkuAttValueMapper.insert(pmsSkuAttValue);
                       }
                   }

                   List<PmsSkuImage> pmsSkuImageList=pmsSkuInfo.getPmsSkuImages();
                   if(pmsSkuImageList!=null&& pmsSkuImageList.size()>0){
                       for(PmsSkuImage pmsSkuImage:pmsSkuImageList){
                           pmsSkuImage.setSkuId(skuId);
                           Integer pmsSkuImageRow=pmsSkuImageMapper.insert(pmsSkuImage);
                       }
                   }

               }
           }
        }

    }

}
