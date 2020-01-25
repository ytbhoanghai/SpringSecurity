package com.nguyen.security.service;

import com.nguyen.security.entity.Role;
import com.nguyen.security.entity.User;

import java.util.List;

public interface RoleService {

    List<Role> findByUsers(User user);
}
