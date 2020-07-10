package com.zcm.manage;


import com.zcm.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageServiceApplicationTests {
    @Autowired
    RedisUtil redisUtil;
    @Test
   public void contextLoads() {
        redisUtil.set("20182018","这是一条测试数据", 0);
        //Long resExpire = redisUtil.expire("20182018", 60, 0);//设置key过期时间
        //System.out.println("resExpire="+resExpire);
        String res = redisUtil.get("20182018", 0);
    }

}
