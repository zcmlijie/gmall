package com.zcm.service.Impl;

import com.zcm.service.DistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
//@DependsOn("redissonClient")
public class DistributedLockerServiceImpl implements DistributedLocker {

  private RedissonClient redissonClient;

  public RedissonClient getRedissonClient(){
    if(redissonClient==null){
      redissonClient= SpringContextHolder.getBean(RedissonClient.class);
    }
    return redissonClient;
  }
  @Override
  public RLock lock(String lockKey) {
    redissonClient = getRedissonClient();
    RLock lock = redissonClient.getLock(lockKey);
    lock.lock();
    return lock;
  }

  @Override
  public RLock lock(String lockKey, int leaseTime) {
    redissonClient = getRedissonClient();
    RLock lock = redissonClient.getLock(lockKey);
    lock.lock(leaseTime, TimeUnit.SECONDS);
    return lock;
  }

  @Override
  public RLock lock(String lockKey, TimeUnit unit ,int timeout) {
    redissonClient = getRedissonClient();
    RLock lock = redissonClient.getLock(lockKey);
    lock.lock(timeout, unit);
    return lock;
  }

  @Override
  public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
    if(redissonClient==null){
      redissonClient= SpringContextHolder.getBean(RedissonClient.class);
    }
    RLock lock = redissonClient.getLock(lockKey);
    try {
      return lock.tryLock(waitTime, leaseTime, unit);
    } catch (InterruptedException e) {
      return false;
    }
  }

  @Override
  public void unlock(String lockKey) {
    redissonClient = getRedissonClient();
    RLock lock = redissonClient.getLock(lockKey);
    lock.unlock();
  }

  @Override
  public void unlock(RLock lock) {
    lock.unlock();
  }

}
