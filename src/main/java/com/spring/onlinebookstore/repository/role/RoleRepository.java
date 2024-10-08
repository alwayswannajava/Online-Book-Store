package com.spring.onlinebookstore.repository.role;

import com.spring.onlinebookstore.model.Role;
import com.spring.onlinebookstore.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(RoleName name);
}
