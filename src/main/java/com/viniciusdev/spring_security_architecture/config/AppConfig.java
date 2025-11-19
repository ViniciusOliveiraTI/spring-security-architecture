package com.viniciusdev.spring_security_architecture.config;

import com.viniciusdev.spring_security_architecture.entities.Permission;
import com.viniciusdev.spring_security_architecture.entities.Role;
import com.viniciusdev.spring_security_architecture.repositories.PermissionRepository;
import com.viniciusdev.spring_security_architecture.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Set;

@Configuration
public class AppConfig implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public AppConfig(PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Permission p1 = new Permission(null, "read");
        Permission p2 = new Permission(null, "write");
        Permission p3 = new Permission(null, "delete");

        permissionRepository.saveAll(Arrays.asList(p1, p2, p3));

        Role basicRole = new Role(null, "USER");

        basicRole.setPermissions(Set.of(p1, p2, p3));

        roleRepository.save(basicRole);

    }

}
