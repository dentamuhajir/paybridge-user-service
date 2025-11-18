package com.fintech.platform.repository;

import com.fintech.platform.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Boolean existsByName(String name);
}
