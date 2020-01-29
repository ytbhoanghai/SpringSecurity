package com.nguyen.security;

import com.nguyen.security.entity.User;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    @PostAuthorize("returnObject.type.equalsIgnoreCase(authentication.name)")
    User findById(Integer id);
    @PreAuthorize("hasRole('ADMIN') or hasRole('ROOT')")
    void updateUser(User user);
    @PreAuthorize("hasRole('ROOT')")
    void deleteUser(Integer id);
}
