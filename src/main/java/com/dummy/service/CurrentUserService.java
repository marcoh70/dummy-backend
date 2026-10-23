package com.dummy.service;

import com.dummy.config.CurrentUserConfig;
import com.dummy.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrentUserService {

    private final CurrentUserConfig currentUserConfig;

    public String getCurrentUserInfo(){

        return  """
                UserId: %s \n
                Authorities: %s \n  
                Claims: %s
                """.formatted(currentUserConfig.getUserId(), currentUserConfig.getUserAuthorities(),currentUserConfig.getClaims());

    }
}
