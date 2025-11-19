package com.viniciusdev.spring_security_architecture.services;

import org.springframework.security.core.Authentication;

public interface OwnershipService<ID> {

    boolean isOwner(ID id, Authentication authentication);

}
