package com.test.server.security;

import com.test.server.dto.UserDTO;

public class CustomSecurityContextHolder {

    private static final ThreadLocal<UserDTO> threadLocalScope = new  ThreadLocal<>();

    public final static UserDTO getLoggedUser() {
        return threadLocalScope.get();
    }

    public final static void setLoggedUser(UserDTO user) {
        threadLocalScope.set(user);
    }
}
