package org.example.springsecurity_test.controller;

import org.example.springsecurity_test.dao.MemberDao;
import org.example.springsecurity_test.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @Autowired
    private MemberDao memberDao;

    @PostMapping("/register")
    public String register(@RequestBody Member member){

        Integer memberId = memberDao.createMember(member);
        return "註冊成功";
    }
}
