package com.zcm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcm.bean.PageBean;
import com.zcm.bean.PmsBaseAttInfo;
import com.zcm.service.PmsBaseAttService;
import message.R;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//跨域
public class AttrController {
    @Reference
    PmsBaseAttService pmsBaseAttService;

    /**
     * 获取三级分类
     * @param id3
     * @return
     */
    @RequestMapping(value = "/getPmsBaseAtt",method = RequestMethod.POST)
    private List<PmsBaseAttInfo> getPmsBaseAtt(Integer id3){
        List<PmsBaseAttInfo> list=pmsBaseAttService.getPmsBaseAtt(id3);
        return list;
    }
    @RequestMapping(value = "/saveAll",method = RequestMethod.POST)
    private Map<String,Object> saveAll(@RequestBody  PmsBaseAttInfo pmsBaseAttInfo){
        pmsBaseAttService.savePmsBaseAtt(pmsBaseAttInfo);
        Map map=new HashMap();
        map.put("code",200);
        map.put("message","保存成功");
        return map;
    }
    @RequestMapping(value = "/getPageBean",method = RequestMethod.POST)
    private R getPmsBaseValuePageBean(Integer curr){
        PageBean<PmsBaseAttInfo> pageBean=pmsBaseAttService.getPmsBaseAttPageBean(curr);
        return R.ok().data("pageBean",pageBean).message("分页成功");
    }

}
