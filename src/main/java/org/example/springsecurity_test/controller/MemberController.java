package org.example.springsecurity_test.controller;


import org.example.springsecurity_test.dao.MemberDao;
import org.example.springsecurity_test.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class MemberController {
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public String register(@RequestBody Member member){
        String hashedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(hashedPassword);

        Integer memberId = memberDao.createMember(member);
        return "註冊成功";
    }

    @PostMapping("/userLogin")
    public String userLogin(Authentication authentication){
        // 取得使用者帳號
        String name = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return "登入成功 ! 帳號 " + name + " 的權限為 " + authorities;
    }
}
