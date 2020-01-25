package com.nguyen.security.repository;

import com.nguyen.security.entity.Role;
import com.nguyen.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByUsers(User user);
}
