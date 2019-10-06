package com.test.server.dto;

import java.io.Serializable;

public class TokenDTO implements Serializable {

    private static final long serialVersionUID = 1663921477514177010L;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}