package com.nguyen.security.service;

import com.nguyen.security.entity.Role;
import com.nguyen.security.entity.User;
import com.nguyen.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(@Autowired RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findByUsers(User user) {
        return roleRepository.findByUsers(user);
    }
}
