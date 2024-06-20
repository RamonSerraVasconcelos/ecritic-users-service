package com.ecritic.ecritic_users_service.core.model;

import com.ecritic.ecritic_users_service.exception.ResourceViolationException;

public enum Role {

    DEFAULT,
    MODERATOR,
    ADMIN;

    public static Role parseRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (Exception e) {
            throw new ResourceViolationException("Invalid role");
        }
    }
}
