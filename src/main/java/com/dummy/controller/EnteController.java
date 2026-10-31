package com.dummy.controller;

import com.dummy.dto.UserInfo;
import com.dummy.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ente")
public class EnteController {

    private final CurrentUserService currentUserService;

    //@GetMapping(path = "/ente")
    public String getUserInfo(){

        return currentUserService.getCurrentUserInfo();
    }

    @PreAuthorize("hasAnyAuthority('ENTE_WRITE', 'ENTE_READ')")
    @GetMapping("/user")
    public Map<String, Object> securityTest(
            Authentication authentication) {

        return Map.of(
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }

    @PreAuthorize("hasAuthority('ENTE_ADMIN')")
    @GetMapping("/admin")
    public Map<String, Object> securityAdminTest(
            Authentication authentication) {

        return Map.of(
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }
}
