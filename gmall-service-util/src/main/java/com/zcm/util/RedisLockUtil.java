package com.zcm.util;

import com.zcm.service.DistributedLocker;
import com.zcm.service.Impl.SpringContextHolder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class RedisLockUtil {
  private static DistributedLocker distributedLocker=
    SpringContextHolder.getBean(DistributedLocker.class);

  @Autowired
  private RedissonClient redissonClient;

  /**
   * 加锁
   * @param lockKey
   * @return
   */
  public static RLock lock(String lockKey) {
    return distributedLocker.lock(lockKey);
  }

  /**
   * 释放锁
   * @param lockKey
   */
  public static void unlock(String lockKey) {
    distributedLocker.unlock(lockKey);
  }

  /**
   * 释放锁
   * @param lock
   */
  public static void unlock(RLock lock) {
    distributedLocker.unlock(lock);
  }

  /**
   * 带超时的锁
   * @param lockKey
   * @param timeout 超时时间   单位：秒
   */
  public static RLock lock(String lockKey, int timeout) {
    return distributedLocker.lock(lockKey, timeout);
  }

  /**
   * 带超时的锁
   * @param lockKey
   * @param unit 时间单位
   * @param timeout 超时时间
   */
  public static RLock lock(String lockKey, int timeout,TimeUnit unit ) {
    return distributedLocker.lock(lockKey, unit, timeout);
  }

  /**
   * 尝试获取锁
   * @param lockKey
   * @param waitTime 最多等待时间
   * @param leaseTime 上锁后自动释放锁时间
   * @return
   */
  public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
    return distributedLocker.tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
  }

  /**
   * 尝试获取锁
   * @param lockKey
   * @param unit 时间单位
   * @param waitTime 最多等待时间
   * @param leaseTime 上锁后自动释放锁时间
   * @return
   */
  public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
    return distributedLocker.tryLock(lockKey, unit, waitTime, leaseTime);
  }



}
