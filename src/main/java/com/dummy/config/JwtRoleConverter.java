package com.dummy.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class JwtRoleConverter
        implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        // 1. Identify the target service through the JWT audience
        List<String> audiences = jwt.getAudience();

        if (audiences == null || audiences.size() != 1) {
            return List.of();
        }

        String targetService = audiences.get(0);

        // 2. Read resource_access
        Object resourceAccessClaim = jwt.getClaim("resource_access");

        if (!(resourceAccessClaim instanceof Map<?, ?> resourceAccess)) {
            return List.of();
        }

        // 3. Find the roles belonging to the target service
        Object serviceAccess = resourceAccess.get(targetService);

        if (!(serviceAccess instanceof Map<?, ?> serviceAccessMap)) {
            return List.of();
        }

        Object rolesClaim = serviceAccessMap.get("roles");

        if (!(rolesClaim instanceof Collection<?> roles)) {
            return List.of();
        }

        // 4. Convert roles into Spring Security authorities
        return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
    }
}
