package com.nguyen.security.service;

import com.nguyen.security.clazz.EStatus;
import com.nguyen.security.entity.Role;
import com.nguyen.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public CustomUserDetailsService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        return convertUserToUserDetails(user);
    }

    private List<GrantedAuthority> getGrantedAuthority(User user) {
        List<Role> roles = roleService.findByUsers(user);
        return roles.stream().
                map(role -> new SimpleGrantedAuthority("ROLE_" + role.getType())).
                collect(Collectors.toList());
    }

    private org.springframework.security.core.userdetails.User convertUserToUserDetails(User user) {
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthority(user);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getStatus() == EStatus.ACTIVE,
                true,
                true,
                true,
                grantedAuthorities);
    }
}
