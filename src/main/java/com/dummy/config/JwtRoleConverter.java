package com.dummy.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtRoleConverter
        implements Converter<Jwt, Collection<GrantedAuthority>> {


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Set<GrantedAuthority> authorities = new LinkedHashSet<>();

        // 1. Extract global roles (realm roles)
        Object realmAccessClaim = jwt.getClaim("realm_access");

        if (realmAccessClaim instanceof Map<?, ?> realmAccess) {
            Object rolesClaim = realmAccess.get("roles");

            if (rolesClaim instanceof Collection<?> roles) {
                roles.stream()
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .forEach(authorities::add);
            }
        }

        // 2. Identify target service through audience
        List<String> audiences = jwt.getAudience();

        if (audiences == null || audiences.size() != 1) {
            return authorities;
        }

        String targetService = audiences.get(0);

        // 3. Extract service-specific roles
        Object resourceAccessClaim = jwt.getClaim("resource_access");

        if (!(resourceAccessClaim instanceof Map<?, ?> resourceAccess)) {
            return authorities;
        }

        Object serviceAccess = resourceAccess.get(targetService);

        if (!(serviceAccess instanceof Map<?, ?> serviceAccessMap)) {
            return authorities;
        }

        Object rolesClaim = serviceAccessMap.get("roles");

        if (rolesClaim instanceof Collection<?> roles) {
            roles.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .map(SimpleGrantedAuthority::new)
                    .forEach(authorities::add);
        }

        authorities.stream()
                .forEach(a -> System.out.println("Authority: " + a.getAuthority()));

        return authorities;
    }
}
