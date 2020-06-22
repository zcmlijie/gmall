package com.zcm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcm.bean.*;
import com.zcm.fastdfs.FastDFSClient;
import com.zcm.fastdfs.FastDFSFile;
import com.zcm.service.PmsCommentService;
import message.R;
import message.ResultCodeEnum;
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
public class CommentController {
  @Reference
  PmsCommentService pmsCommentService;

  /**
   * 添加评论
   * @param
   * @return
   */
  @RequestMapping(value = "/savaPmsComment",method = RequestMethod.POST)
  @ResponseBody
  public Map<String, R> savePmsComment(@RequestParam (name = "productId") Integer productId,@RequestParam(name = "content")String content,@RequestParam("file") MultipartFile[] file) throws IOException {
    Map<String,R> map=new HashMap<>();
    if(productId!=null && content !=null && !"".equals(content)){
      PmsComment pmsComment=new PmsComment();
      String path=saveFileAll(file);
      pmsComment.setPics(path);
      pmsComment.setProductId(productId);
      pmsComment.setContent(content);
      Integer row=pmsCommentService.savePmsComment(pmsComment);
      if(row>0){
        map.put("data",R.ok().message("添加成功"));
        return map;
      }else {
        map.put("data",R.error());
        return map;
      }
    }
    map.put("data",R.setResult(ResultCodeEnum.UNKNOWN_ERROR));
    return map;
  }

  /**
   * 添加回复
   * @param
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/savePmsReplay",method = RequestMethod.POST)
  @ResponseBody
public Map<String,R> savePmsReplay (@RequestBody PmsReplay pmsReplay){
    Map<String,R> map=new HashMap<>();
  if(pmsReplay!=null){
    Integer row=pmsCommentService.savePmsReplay(pmsReplay);
    if(row!=null && row>0){
      map.put("data",R.ok().message("回复成功"));
      return map;
    }else {
      map.put("data",R.error());
      return map;
    }
  }
  map.put("data",R.setResult(ResultCodeEnum.UNKNOWN_ERROR));
  return map;
}

  /**
   *获取评论，回复列表
   * @return
   */
  @RequestMapping(value = "/getPmsCommentList",method = RequestMethod.POST)
  @ResponseBody
 public Map<String ,R> getPmsCommentList(@RequestParam(name = "start") Integer start){
    Map<String,R> map=new HashMap<>();
    PageBean<PmsComment> pmsCommentPageBean=pmsCommentService.getPageBeanPmsCommentList(start);
    if(pmsCommentPageBean!=null){
      map.put("data",R.ok().data("data",pmsCommentPageBean));
      return map;
    }else {
      map.put("data",R.error());
    }
    map.put("data",R.setResult(ResultCodeEnum.UNKNOWN_ERROR));
    return map;
 }
  public String saveFileAll(MultipartFile[] multipartFiles) throws IOException {
    List<String[]> fileAbsolutePath=new ArrayList<>();
    List<FastDFSFile> list=new ArrayList<FastDFSFile>();
    String pathUrl="";
    for(MultipartFile multipartFile:multipartFiles){
      FastDFSFile fastDFSFile=new FastDFSFile();
      //获取上传图片的名称
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
      list.add(fastDFSFile);
    }
    try {
      fileAbsolutePath = FastDFSClient.uploadList(list);  //上传到fastdfa服务器
    } catch (Exception e) {
      //在命令行打印异常信息在程序中出错的位置及原因
      e.printStackTrace();
    }
        /*if (fileAbsolutePath==null) {
            logger.error("upload file failed,please upload again!");
        }*/
    //获取上传图片的路径
    for(int i=0;i<fileAbsolutePath.size();i++){
      pathUrl+=FastDFSClient.getTrackerUrl()+fileAbsolutePath.get(i)[0]+ "/"+fileAbsolutePath.get(i)[1];
      pathUrl+=",";
    }
    return pathUrl.substring(0,pathUrl.length()-1);
  }
}
