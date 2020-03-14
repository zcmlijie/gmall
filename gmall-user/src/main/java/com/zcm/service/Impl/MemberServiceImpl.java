package com.zcm.service.Impl;

import com.zcm.bean.Member;
import com.zcm.dao.MemberMapper;
import com.zcm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public void savaAll(Member member) {
        Date now=new Date();
        member.setCreateTime(now);
        member.setCity(member.getCity());
        member.setJob(member.getJob());
        Integer row=memberMapper.insert(member);
    }
}
