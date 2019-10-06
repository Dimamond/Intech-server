package com.test.server.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AuthorizationDTO implements Serializable {


    private static final long serialVersionUID = 8632041432462193727L;

    @NotNull
    private String login;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
