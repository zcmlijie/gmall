package com.zcm.manage;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GamllManageWebApplicationTests {
    @Test
    public void  contextLoads() throws IOException, MyException {
        String file=this.getClass().getResource("/tracker.conf").getFile();
        ClientGlobal.init(file);
        TrackerClient trackerClient=new TrackerClient();
        //获取一个trackerserver实例
        TrackerServer trackerServer=trackerClient.getTrackerServer();
        //通过tracker获取storage实例 
        StorageClient storageClient=new StorageClient(trackerServer,null);
        String orginalFilename="/Users/sunjing/Desktop/WechatIMG540.jpeg";
        String url="http://172.16.111.128/";
        String [] upload_file=storageClient.upload_file(orginalFilename,"jpeg",null);
        StringBuilder stringBuilder=new StringBuilder(url);
        for(int i=0;i<upload_file.length;i++){
            String s=upload_file[i]+"/";
            stringBuilder.append(s);
        }

        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        System.out.println("s="+stringBuilder);
    }

}
