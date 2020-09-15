package com.zcm.service.Impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zcm.bean.*;
import com.zcm.dao.*;
import com.zcm.service.SKUService;
import com.zcm.util.RedisUtil;
import message.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.server.InactiveGroupException;

import java.util.List;
import java.util.Map;

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
    @Autowired
    RedisUtil redisUtil;
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
   private static  final String GETSKUINFO002="getSkuInfo002";
    @Override
    public PmsSkuInfo getSkuInfoBySkuId(Integer skuId) {
        String json=redisUtil.get(GETSKUINFO002,0);
        if(json!=null){
           if(!StringUtils.isEmpty(json)) {
               PmsSkuInfo pmsSkuInfo=JSON.parseObject(json,PmsSkuInfo.class);
               return pmsSkuInfo;
           }
        }else {
            if(skuId!=null){
                PmsSkuInfo pmsSkuInfo=pmsSkuInfoMapper.selectByPrimaryKey(skuId);
                //图片集合
                if(pmsSkuInfo!=null){
                    List<PmsSkuImage> pmsSkuImageList=pmsSkuImageMapper.selectSkuImageBySkuId(skuId);
                    pmsSkuInfo.setPmsSkuImages(pmsSkuImageList);
                    //sku的销售属性
                    List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues=
                            skuSaleAttrValueMapper.selectSkuSaleAttrValeu(skuId);
                    pmsSkuInfo.setPmsSkuSaleAttrValues(pmsSkuSaleAttrValues);
                    //sku的平台属性
                    List<PmsSkuAttValue> pmsSkuAttValues=
                            pmsSkuAttValueMapper.selectPmsSkuAttValueBySkuId(skuId);
                    pmsSkuInfo.setPmsSkuAttValueList(pmsSkuAttValues);
                }
                String strJson=JSON.toJSONString(pmsSkuInfo);
                redisUtil.set(GETSKUINFO002,strJson,0);
                return pmsSkuInfo;
            }
        }

        return null;
    }
    private static final String GETSKUINFO001="getSkuInfo001";
    @Override
    public List<PmsSkuInfo> getSkuInfoByProductId(Integer productId) {
        Boolean exists=redisUtil.exists(GETSKUINFO001);
        if(exists){
            String json=redisUtil.get(GETSKUINFO001,0);
            if(json!=null){
                //内存中存在值
                if(!StringUtils.isEmpty(json)){
                    List<PmsSkuInfo> pmsSkuInfos = JSON.parseArray(json,PmsSkuInfo.class); //直接用这个方法 然后就能把他转成曾经存入的样子 就是一个list的格式
                    return pmsSkuInfos;
                }
            }

        }else {
            //内存中不存在值，查询硬盘中的值
            if(productId!=null){
                List<PmsSkuInfo> pmsSkuInfoList=pmsSkuInfoMapper.selectPmsSkuSaleAttrBySpuId(productId);
                String strJson = JSON.toJSONString(pmsSkuInfoList);
                redisUtil.set(GETSKUINFO001,strJson,0);
                return pmsSkuInfoList;
            }
        }

        return null;
    }
    private static final String GETSALEATTR001="getSaleAttr001";
    @Override
    public List<Map<String, String>> getSaleAttrName(Integer productId) {
        String json=redisUtil.get(GETSALEATTR001,0);
        if(json!=null){
            if(!StringUtils.isEmpty(json)){
                List<Map<String,String>> mapList=JSON.parseObject(json, new TypeReference<List<Map<String, String>>>() {});
                return mapList;
            }
        }else {
            if(productId!=null){
                List<Map<String,String>> mapList=pmsSkuInfoMapper.selectPmsSkuSaleAttrName(productId);
                String strjson=JSON.toJSONString(mapList);
                redisUtil.set(GETSALEATTR001,strjson,0);
                return mapList;
            }
        }

        return null;
    }
private static final String SKUINFOALL="skuInfo_0001";
    @Override
    public List<PmsSkuInfo> getskuAll() {
        String json=redisUtil.get(SKUINFOALL,0);
        if(json!=null){
            if(StringUtils.isNotEmpty(json)){
               List<PmsSkuInfo> list=JSON.parseArray(json,PmsSkuInfo.class);
               return list;
            }
        }else {
            List<PmsSkuInfo> pmsSkuInfoList = pmsSkuInfoMapper.selectSkuInfoAll();
            if(pmsSkuInfoList!=null&&pmsSkuInfoList.size()>0) {
                for (PmsSkuInfo skuInfo : pmsSkuInfoList) {
                    List<PmsSkuAttValue> pmsSkuAttValues = pmsSkuAttValueMapper.selectPmsSkuAttValueBySkuId(skuInfo.getId());
                    skuInfo.setPmsSkuAttValueList(pmsSkuAttValues);
                    List<PmsSkuImage> pmsSkuImageList = pmsSkuImageMapper.selectSkuImageBySkuId(skuInfo.getId());
                    skuInfo.setPmsSkuImages(pmsSkuImageList);
                    List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = skuSaleAttrValueMapper.selectSkuSaleAttrValeu(skuInfo.getId());
                    skuInfo.setPmsSkuSaleAttrValues(pmsSkuSaleAttrValues);
                }
            }
            String jsonStr=JSON.toJSONString(pmsSkuInfoList);
            redisUtil.set(SKUINFOALL,jsonStr,0);
            return pmsSkuInfoList;
        }
        return null;
    }

}
