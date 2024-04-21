package org.example.springsecurity_test.dao;

import org.example.springsecurity_test.model.Member;

public interface MemberDao {
    Member getMemberByEmail(String email);

    Integer createMember(Member member);
}
