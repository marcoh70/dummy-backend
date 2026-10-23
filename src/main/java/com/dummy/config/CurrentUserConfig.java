package com.dummy.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurrentUserConfig {

    public String getUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            throw new IllegalStateException(
                    "No hay un usuario autenticado mediante JWT");
        }

        return jwtAuth.getToken().getSubject() + jwtAuth.getToken().getSubject();
    }

    public List<String> getUserAuthorities() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        authentication.getName();
        return authentication.getAuthorities().stream()
                .map(auth -> "Authority: " + auth.getAuthority() + "]\n")
                .toList();

    }

    public List<String> getClaims() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            throw new IllegalStateException(
                    "No hay un usuario autenticado mediante JWT");
        }

        return jwtAuth.getToken().getClaims()
                .entrySet()
                .stream()
                .map(es -> "Claim: [" + es.getKey() + ": " + es.getValue() + "]\n")
                .toList();
    }
}
