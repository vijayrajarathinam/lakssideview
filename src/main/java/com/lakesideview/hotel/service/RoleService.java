package com.lakesideview.hotel.service;

import com.lakesideview.hotel.model.Role;
import com.lakesideview.hotel.model.User;

import java.util.List;

public interface RoleService {

    List<Role> getRoles();

    Role createRole(Role role);

    void deleteRole(Long id);

    Role findByName(String name);

    User removeUserFromRole(Long userId, Long roleId);

    User assignRoleToUser(Long userId, Long roleId);

    Role removeAllUserFromRole(Long roleId);
}
