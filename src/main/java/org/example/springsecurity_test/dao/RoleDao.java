package org.example.springsecurity_test.dao;

import org.example.springsecurity_test.model.Role;

public interface RoleDao {
    Role getRoleByName(String roleName);
}
