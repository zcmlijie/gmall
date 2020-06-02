package com.zcm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcm.bean.PmsBaseAttInfo;
import com.zcm.bean.PmsProductImage;
import com.zcm.bean.PmsProductSaleAttr;
import com.zcm.bean.PmsSkuImage;
import com.zcm.fastdfs.FastDFSClient;
import com.zcm.fastdfs.FastDFSFile;
import com.zcm.service.SKUService;

import message.R;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class SKUController {
    @Reference
    SKUService skuService;

    /**
     * 在sku中获取平台的销售属性字典列表
     *
     * @return
     */
    @RequestMapping(value = "/getPms", method = RequestMethod.POST)
    public R getPmsBaseAttInfo() {
        try {
            List<PmsBaseAttInfo> pmsBaseAttInfoList = skuService.getPmsBaseAttInfo();
            return R.ok().data("data", pmsBaseAttInfoList).message("sku平台属性");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value = "/getPmsProductSaleAtt",method = RequestMethod.POST)
    public Map<String,R> getPmsProcductSaleAtt(Integer productId){
        Map<String,R> map=new HashMap<String, R>();
        List<PmsProductSaleAttr> pmsProductSaleAttrs=skuService.getPmsProductSaleAtt(productId);
        map.put("data",R.ok().data("data",pmsProductSaleAttrs).message("sku销售属性"));
        return map;
    }
    /**
     * 上传sku图片，并设置默认主图
     */
    @RequestMapping(value = "/saveSkuImage",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,R> saveSkuImage(@RequestParam(name = "productImgId") Integer productImgId ,@RequestParam(name = "id",required =false) Integer id ,@RequestParam("file") MultipartFile multipartFile) throws IOException {
        Map<String,R> map=new HashMap<>();
        PmsSkuImage pmsSkuImage1=saveFile(multipartFile);
        pmsSkuImage1.setProductImgId(productImgId);
        pmsSkuImage1.setId(id);
        skuService.savePmsSkuImage(pmsSkuImage1);
        map.put("data",R.ok().message("上传成功"));
        return map;
    }


    public PmsSkuImage saveFile(MultipartFile multipartFile) throws IOException {
        String path="";
        PmsSkuImage pmsSkuImage=new PmsSkuImage();
        String[] fileAbsolutePath={};
        //上传图片的路径名称
        String fileName=multipartFile.getOriginalFilename();
        pmsSkuImage.setImgName(fileName);
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream=multipartFile.getInputStream();
        if(inputStream!=null){
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            //上传图片到fastdgs服务器
            fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
        } catch (Exception e) {
            e.printStackTrace();
        }
        //上传图片的路径
        if (fileAbsolutePath!=null) {
           path=FastDFSClient.getTrackerUrl()+fileAbsolutePath[0]+ "/"+fileAbsolutePath[1];
        }
        pmsSkuImage.setImgUrl(path);
        return pmsSkuImage;
    }
}
