package com.zcm.dao;

import com.zcm.bean.PmsBaseSaleAtt;
import com.zcm.bean.PmsProductInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface PmsProductInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsProductInfo record);

    int insertSelective(PmsProductInfo record);

    PmsProductInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsProductInfo record);

    int updateByPrimaryKey(PmsProductInfo record);

    List<PmsProductInfo> getPmsProductPageBean(Map<String,Object> map);

    Integer PageBeanCount(@Param("name") String name, @Param("id") Integer id);

    /**
     * 平台销售属性字典
     * @return
     */
    List<PmsBaseSaleAtt> selctAll();
}
