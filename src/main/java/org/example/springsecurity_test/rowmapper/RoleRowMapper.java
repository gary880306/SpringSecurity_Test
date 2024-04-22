package org.example.springsecurity_test.rowmapper;

import org.example.springsecurity_test.model.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();
        role.setRoleId(resultSet.getInt("role_id"));
        role.setRoleName(resultSet.getString("role_name"));

        return role;
    }
}