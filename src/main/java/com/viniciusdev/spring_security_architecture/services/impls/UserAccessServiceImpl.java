package com.viniciusdev.spring_security_architecture.services.impls;

import com.viniciusdev.spring_security_architecture.services.OwnershipService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("userAccessService")
public class UserAccessServiceImpl implements OwnershipService<UUID> {

    @Override
    public boolean isOwner(UUID id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return false;
        UUID authId = UUID.fromString(authentication.getName());
        return id.equals(authId);
    }
}
