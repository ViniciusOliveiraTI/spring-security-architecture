package com.viniciusdev.spring_security_architecture.repositories;

import com.viniciusdev.spring_security_architecture.entities.Permission;
import com.viniciusdev.spring_security_architecture.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    Optional<Role> findByName(String name);

}
