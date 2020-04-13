package com.zcm.service;

import com.zcm.bean.*;

import java.util.List;
import java.util.Map;

public interface SPUServcie {
    /**
     * 根据名称和id模糊查询
     *
     * @return
     */
    PageBean<PmsProductInfo> getPmsProductInfos(String name,Integer id,Integer curr);

    /**
     * 平台的销售属性
     * @return
     */
    List<PmsBaseSaleAtt> getPmsSaleAtt();

    /**
     * 添加商品
     * @param pmsBaseAttInfo
     * @return
     */
    Integer savaListAll(PmsBaseAttInfo pmsBaseAttInfo);

    /**
     * 批量插入图片路径
     * @param pmsProductImage
     * @return
     */
    Integer savaBatch(List<PmsProductImage> pmsProductImage);

    /**
     * 添加spu
     * @param pmsProductInfo
     * @return
     */
    Integer savePmsProcuct(PmsProductInfo pmsProductInfo);
}
