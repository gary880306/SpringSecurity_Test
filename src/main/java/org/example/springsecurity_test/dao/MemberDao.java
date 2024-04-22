package org.example.springsecurity_test.dao;

import org.example.springsecurity_test.model.Member;
import org.example.springsecurity_test.model.Role;


import java.util.List;

public interface MemberDao {
    Member getMemberByEmail(String email);

    Integer createMember(Member member);

    // 權限相關
    List<Role> getRolesByMemberId(Integer memberId);
}
