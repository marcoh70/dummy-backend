package com.dummy.controller;

import com.dummy.dto.UserInfo;
import com.dummy.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EnteController {

    private final CurrentUserService currentUserService;

    @GetMapping(path = "/ente")
    public String getUserInfo(){

        return currentUserService.getCurrentUserInfo();
    }
}
