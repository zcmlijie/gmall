package com.zcm.controller;

import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.dubbo.config.annotation.Reference;
import com.zcm.bean.PageBean;
import com.zcm.bean.PmsBaseSaleAtt;
import com.zcm.bean.PmsProductImage;
import com.zcm.bean.PmsProductInfo;
import com.zcm.fastdfs.FastDFSClient;
import com.zcm.fastdfs.FastDFSFile;
import com.zcm.service.SPUServcie;
import message.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static message.ResultCodeEnum.SUCCESS;

@RestController
public class SPUController {
    @Reference
    SPUServcie spuServcie;
    @RequestMapping(value = "/pageBean",method = RequestMethod.POST)
    //如果参数类型支持null,则是false@RequestBody(required=false)
    public R getPageBean(String name, Integer id, Integer curr){
        PageBean<PmsProductInfo> pageBean=spuServcie.getPmsProductInfos(name,id,curr);
        return R.ok().data("pageBean",pageBean).message("查询成功");
    }

    /**
     * 获取平台的销售属性
     * @return
     */
    @RequestMapping(value = "/getPmsBaseSalatAtt",method = RequestMethod.POST)
    public R getPmsBaseSalatAtt(){
        List<PmsBaseSaleAtt> pmsBaseSaleAtt=spuServcie.getPmsSaleAtt();
        return R.ok().data("data",pmsBaseSaleAtt).message("平台的销售属性");
    }

    /**
     * 上传图片到fastdfs服务器上去
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public R singleFileUpload(@RequestParam("file") MultipartFile[] file) throws IOException {
        List<PmsProductImage> listPath=saveFileAll(file);
        spuServcie.savaBatch(listPath);
        return R.setResult(SUCCESS).data("path",listPath);
    }

    /**
     * 添加商品的spu
     * @param pmsProductInfo
     * @return
     */
    @RequestMapping(value = "/savaSPU",method = RequestMethod.POST)
    public R SaveSPU(@RequestBody PmsProductInfo pmsProductInfo){
        Integer row=spuServcie.savePmsProcuct(pmsProductInfo);
        return R.setResult(SUCCESS).data("row",row);
    }

    public List<PmsProductImage> saveFileAll(MultipartFile [] multipartFiles) throws IOException {
        List<String[]> fileAbsolutePath=new ArrayList<>();
        List<String> pathList=new ArrayList<>();
        List<FastDFSFile> list=new ArrayList<FastDFSFile>();
        List<PmsProductImage> listImage=new ArrayList<>();
        for(MultipartFile multipartFile:multipartFiles){
            FastDFSFile fastDFSFile=new FastDFSFile();
            PmsProductImage pmsProductImage=new PmsProductImage();
            String fileName=multipartFile.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            byte[] file_buff = null;
            InputStream inputStream=multipartFile.getInputStream();
            if(inputStream!=null){
                int len1 = inputStream.available();
                file_buff = new byte[len1];
                inputStream.read(file_buff);
            }
            inputStream.close();
            fastDFSFile.setName(fileName);
            fastDFSFile.setExt(ext);
            fastDFSFile.setContent(file_buff);
            //获取上传图片的名称
            pmsProductImage.setImgName(fileName);
            listImage.add(pmsProductImage);
            list.add(fastDFSFile);
        }
        try {
            fileAbsolutePath = FastDFSClient.uploadList(list);  //upload to fastdfs
        } catch (Exception e) {
            //在命令行打印异常信息在程序中出错的位置及原因
           e.printStackTrace();
        }
        /*if (fileAbsolutePath==null) {
            logger.error("upload file failed,please upload again!");
        }*/
        //获取上传图片的路径
        for(int i=0;i<fileAbsolutePath.size();i++){
            String path=FastDFSClient.getTrackerUrl()+fileAbsolutePath.get(i)[0]+ "/"+fileAbsolutePath.get(i)[1];
            listImage.get(i).setImgUrl(path);
        }
        return listImage;
    }
}
