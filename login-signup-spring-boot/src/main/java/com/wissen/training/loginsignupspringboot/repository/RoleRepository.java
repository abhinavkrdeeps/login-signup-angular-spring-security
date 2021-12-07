package com.wissen.training.loginsignupspringboot.repository;

import com.wissen.training.loginsignupspringboot.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
