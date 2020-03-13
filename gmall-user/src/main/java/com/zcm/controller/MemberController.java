package com.zcm.controller;

import com.zcm.pojo.Member;
import com.zcm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Map<String,Object> saveAll(@RequestBody Member member){
        memberService.savaAll(member);
        Map<String,Object> map=new HashMap<>();
        map.put("插入成功","1");
        return map;
    }
}
