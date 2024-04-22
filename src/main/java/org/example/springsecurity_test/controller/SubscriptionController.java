package org.example.springsecurity_test.controller;

import org.example.springsecurity_test.dao.MemberDao;
import org.example.springsecurity_test.dao.RoleDao;
import org.example.springsecurity_test.dto.SubscribeRequest;
import org.example.springsecurity_test.dto.UnsubscribeRequest;
import org.example.springsecurity_test.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriptionController {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private RoleDao roleDao;

    @PostMapping("/subscribe")
    public String subscribe(@RequestBody SubscribeRequest subscribeRequest) {
        Integer memberId = subscribeRequest.getMemberId();
        List<Role> roles = memberDao.getRolesByMemberId(memberId);

        // 檢查訂閱狀態
        boolean isSubscribed = checkSubscribeStatus(roles);

        // 根據訂閱狀態決定是否執行操作
        if(isSubscribed){
            return "已訂閱過，不需重複訂閱";
        }else {
            Role role = roleDao.getRoleByName("ROLE_VIP_MEMBER");
            memberDao.addRoleForMemberId(memberId, role);
            return "訂閱成功 ! 請刪除 Cookie 重新登入";
        }
    }

    @PostMapping("/unsubscribe")
    public String unsubscribe(@RequestBody UnsubscribeRequest unsubscribeRequest) {
        Integer memberId = unsubscribeRequest.getMemberId();
        List<Role> roles = memberDao.getRolesByMemberId(memberId);

        // 檢查訂閱狀態
        boolean isSubscribed = checkSubscribeStatus(roles);

        // 根據訂閱狀態決定是否執行操作
        if(isSubscribed){
            Role role = roleDao.getRoleByName("ROLE_VIP_MEMBER");
            memberDao.removeRoleFromMemberId(memberId,role);
            return "取消訂閱成功 ! 請刪除 Cookie 重新登入";
        }else{
            return "尚未訂閱，無法執行取消動作";
        }

    }


    private boolean checkSubscribeStatus(List<Role> roles){
        boolean isSubscribed = false;

        for(Role role : roles){
            if(role.getRoleName().equals("ROLE_VIP_MEMBER")){
                isSubscribed = true;
            }
        }

        return isSubscribed;
    }
}
