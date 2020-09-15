package com.zcm.conf;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private int port;

  @Value("${spring.redis.timeout}")
  private int timeout;

  @Value("${spring.redis.jedis.pool.max-idle}")
  private int maxIdle;

  @Value("${spring.redis.jedis.pool.max-wait}")
  private long maxWaitMillis;

  @Value("${spring.redis.password}")
  private String password;

  @Value("${spring.redis.block-when-exhausted}")
  private boolean  blockWhenExhausted;

  //redisson配置
  @Bean
  public RedissonClient redissonClient() {
    Config config = new Config();
    //单节点
    config.useSingleServer().setAddress("redis://" + host + ":" + port);
    if (StringUtils.isEmpty(password)) {
      config.useSingleServer().setPassword(null);
    } else {
      config.useSingleServer().setPassword(password);
    }
    //添加主从配置
// config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});
    // 集群模式配置 setScanInterval()扫描间隔时间，单位是毫秒, //可以用"rediss://"来启用SSL连接
// config.useClusterServers().setScanInterval(2000).addNodeAddress("redis://127.0.0.1:7000", "redis://127.0.0.1:7001").addNodeAddress("redis://127.0.0.1:7002");
    return Redisson.create(config);
  }
}
