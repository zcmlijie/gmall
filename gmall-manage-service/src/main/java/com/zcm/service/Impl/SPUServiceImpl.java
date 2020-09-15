package com.zcm.service.Impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.zcm.bean.*;
import com.zcm.dao.PmsProductImageMapper;
import com.zcm.dao.PmsProductInfoMapper;
import com.zcm.dao.PmsProductSaleAttrMapper;
import com.zcm.dao.PmsProductSaleAttrValueMapper;
import com.zcm.service.SPUServcie;
import com.zcm.util.RedisLockUtil;
import com.zcm.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sun.misc.UUEncoder;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class SPUServiceImpl implements SPUServcie {
    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    PmsProductImageMapper pmsProductImageMapper;
    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private JedisPool jedisPool;
    @Override
    public PageBean<PmsProductInfo> getPmsProductInfos(String name, Integer id, Integer curr) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrPage(curr);
        int size = 2;
        pageBean.setPageSize(size);
        Integer count = pmsProductInfoMapper.PageBeanCount(name, id);
        pageBean.setTotalCount(count);
        //总页数
        double ct = count;
        Double num = Math.ceil(ct / size);
        pageBean.setTotalPage(num.intValue());
        HashMap<String, Object> map = new HashMap<>();
        map.put("start", (curr - 1) * size);
        map.put("size", size);
        map.put("name", name);
        map.put("id", id);
        List<PmsProductInfo> lsit = pmsProductInfoMapper.getPmsProductPageBean(map);
        for (PmsProductInfo ps : lsit) {
            ps.setPmsProductSaleAttrList
                    (pmsProductSaleAttrMapper.slectPmsProductSaleAttr(ps.getId()));
            for (PmsProductSaleAttr pa : ps.getPmsProductSaleAttrList()) {
                pa.setPmsProductSaleAttrValueList(pmsProductSaleAttrValueMapper.
                        selectPmsProductSaleAttrValue(ps.getId(), pa.getId()));
            }
        }
        pageBean.setLists(lsit);
        return pageBean;
    }

    @Override
    public List<PmsBaseSaleAtt> getPmsSaleAtt() {
        List<PmsBaseSaleAtt> pmsBaseSaleAtt = pmsProductInfoMapper.selctAll();
        return pmsBaseSaleAtt;
    }

    @Override
    public Integer savaListAll(PmsBaseAttInfo pmsBaseAttInfo) {

        return null;
    }

    @Override
    public Integer savaBatch(List<PmsProductImage> pmsProductImage) {
        Integer row = pmsProductImageMapper.insertBatch(pmsProductImage);
        return row;
    }

    /**
     * 添加spu
     *
     * @param pmsProductInfo
     * @return
     */
    @Override
    @Transactional
    public Integer savePmsProcuct(PmsProductInfo pmsProductInfo) {
        if (pmsProductInfo != null) {
            Integer pId = 0;
            Integer pmsProductInfoRow = pmsProductInfoMapper.insert(pmsProductInfo);
            if (pmsProductInfoRow > 0) {
                pId = pmsProductInfo.getId();

            }
            //获取spu销售属性
            List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductInfo.getPmsProductSaleAttrList();
            for (PmsProductSaleAttr pm : pmsProductSaleAttrs) {
                pm.setProductId(pId);
            }
            Integer pmsProductSaleAttrRow = pmsProductSaleAttrMapper.insertBatch(pmsProductSaleAttrs);

            if (pmsProductSaleAttrRow > 0) {
                for (PmsProductSaleAttr ps : pmsProductSaleAttrs) {
                    //获取spu销售属性值
                    List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList = ps.getPmsProductSaleAttrValueList();
                    for (PmsProductSaleAttrValue pv : pmsProductSaleAttrValueList) {
                        pv.setSaleAttrId(ps.getId());
                        pv.setProductId(ps.getProductId());
                    }
                    Integer pmsProductSaleAttrValueRow = pmsProductSaleAttrValueMapper.insertBatch(pmsProductSaleAttrValueList);

                }
            }

        }

        return null;
    }

    private static final String PMSPRODUCTSALEATTR001 = "PmsProductSaleAttr001";

    @Override
    public List<PmsProductSaleAttr> getPmsProductSaleAttrBySkuIdAndProductId(Integer skuId, Integer productId) {
        String json = redisUtil.get(PMSPRODUCTSALEATTR001, 0);
        if (json != null) {
            if (!StringUtils.isEmpty(json)) {
                List<PmsProductSaleAttr> pmsProductSaleAttrs = JSON.parseArray(json, PmsProductSaleAttr.class);
                return pmsProductSaleAttrs;
            }
        } else {
            //如果出现一个高并发的缓存失效，导致大量请求访问数据库，则出现击穿，这时候设置分布式锁，
            // 一个一个请求通过可以缓解
            //数据库压力，防止缓存击穿
            //设置分布式锁单位为毫秒
            String token = String.valueOf(UUID.randomUUID());
            //1.在设置每个redis锁的时候，value值设置为token，可以防止释放锁是释放掉别的线程的锁
            String ok = redisUtil.setnxAndpx(PMSPRODUCTSALEATTR001 + "lock", token, "nx", "px", 10 * 1000);
            //获取锁成功时候，在设置的时间内可以访问数据库，在这个时间内，设置的key值不可以重复设置PMSPRODUCTSALEATTR001+"lock"
            if (StringUtils.isNotBlank(ok) && ok.equals("OK")) {
                List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectPmsProductSaleAttrBySkuIdAndProductId(skuId, productId);
                if (pmsProductSaleAttrs != null) {
                    //返回结果并且设置缓存
                    String srtjson = JSON.toJSONString(pmsProductSaleAttrs);
                    redisUtil.set(PMSPRODUCTSALEATTR001, srtjson, 0);
                    return pmsProductSaleAttrs;
                } else {
                    //如果缓存和数据库中都不存在这个key值，则设置一个空并给一个过期时间的key
                    redisUtil.setex(PMSPRODUCTSALEATTR001, 60 * 3, "");
                }
                //在访问mysql后，将mysql的分布式锁释放，
                //判断释放的锁是否为自己的redis的锁
                if (StringUtils.isNotEmpty(token) && redisUtil.get(PMSPRODUCTSALEATTR001 + "lock", 0).equals(token)) {
                    redisUtil.del(PMSPRODUCTSALEATTR001 + "lock");
                }
            } else {
                //设置锁失败，则自旋，休眠几秒后尝试重新访问
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getPmsProductSaleAttrBySkuIdAndProductId(skuId, productId);
            }

        }
        return null;
    }

    /**
     * 测试redisson
     *
     * @param productId
     * @param productQuantity
     * @return
     */
    //模拟库存数量
    private Integer lockCount = 10;

    public Boolean decrementProductStore(Long productId) {
        RedisLockUtil redisLockUtil=new RedisLockUtil();
        String key = "dec_store_lock_" + productId;
        // 加锁，设置超时时间9s
        boolean b = redisLockUtil.tryLock(key,9, 10);
        try {
            //加锁 操作很类似Java的ReentrantLock机制
            if (b) {
                //如果库存为空
                if (lockCount == 0) {
                    return false;
                }
                lockCount--;
                System.out.println("lockCount:"+lockCount);
            }
//
//            //简单减库存操作 没有重新写其他接口了
//            productInfo.setProductStock(productInfo.getProductStock() - 1);
//            productInfoMapper.updateByPrimaryKey(productInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //解锁
            RedisLockUtil.unlock(key);
        }
        return true;

    }
}
