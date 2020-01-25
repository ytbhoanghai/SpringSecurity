package com.nguyen.security.service;

import com.nguyen.security.entity.User;

public interface UserService {

    User findByUsername(String username);
}
