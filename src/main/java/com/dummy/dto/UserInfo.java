package com.dummy.dto;

import java.util.List;

public record UserInfo(
        String userId,
        List<String> autorities,
        List<String> claims) {
}
