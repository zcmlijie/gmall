package com.zcm.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.zcm.bean.Member;
import com.zcm.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberController {
    @Reference
    private MemberService memberService;

    @RequestMapping(value = "/saves",method = RequestMethod.POST)
    public Map<String,Object> saveAll(@RequestBody Member member){
        memberService.savaAll(member);
        Map<String,Object> map=new HashMap<>();
        map.put("插入成功","1");
        return map;
    }
}
