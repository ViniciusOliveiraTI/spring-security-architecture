package com.viniciusdev.spring_security_architecture.config;

import com.viniciusdev.spring_security_architecture.entities.Permission;
import com.viniciusdev.spring_security_architecture.entities.Role;
import com.viniciusdev.spring_security_architecture.entities.User;
import com.viniciusdev.spring_security_architecture.repositories.PermissionRepository;
import com.viniciusdev.spring_security_architecture.repositories.RoleRepository;
import com.viniciusdev.spring_security_architecture.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Set;

@Configuration
public class AppConfig implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AppConfig(PermissionRepository permissionRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Permission p1 = new Permission(null, "user:read");
        Permission p2 = new Permission(null, "user:write");
        Permission p3 = new Permission(null, "user:delete");

        Permission p4 = new Permission(null, "admin:read");
        Permission p5 = new Permission(null, "admin:write");
        Permission p6 = new Permission(null, "admin:delete");

        permissionRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));

        Role basicRole = new Role(null, "USER");

        basicRole.setPermissions(Set.of(p1, p2, p3));

        Role adminRole = new Role(null, "ADMIN");

        adminRole.setPermissions(Set.of(p4, p5, p6));

        roleRepository.saveAll(Arrays.asList(basicRole, adminRole));

        User adm = new User(null, "admin", "admin@gmail.com", passwordEncoder.encode("12345678"));

        adm.setRoles(Set.of(adminRole));

        User u1 = new User(null, "Bruce Wayner", "bruce@gmail.com", passwordEncoder.encode("12345678"));

        u1.setRoles(Set.of(basicRole));

        User u2 = new User(null, "Peter Parker", "peter@gmail.com", passwordEncoder.encode("12345678"));

        u2.setRoles(Set.of(basicRole));

        User u3 = new User(null, "Tony Stark", "tony@gmail.com", passwordEncoder.encode("12345678"));

        u3.setRoles(Set.of(basicRole));

        userRepository.saveAll(Arrays.asList(adm, u1, u2, u3));
    }

}
