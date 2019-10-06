package com.test.server.config.interceptor;

import com.test.server.dto.UserDTO;
import com.test.server.security.*;
import com.test.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {


    private JwtTokenProviderService jwtTokenProviderService;

    private UserService userService;

    public AuthorizationInterceptor(JwtTokenProviderService jwtTokenProviderService, UserService userService) {
        this.jwtTokenProviderService = jwtTokenProviderService;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean isAuthorized = false;
        try {
            String token = jwtTokenProviderService.resolveToken(request);
            if (token != null && jwtTokenProviderService.validateToken(token)) {
                String userLogin = jwtTokenProviderService.getLogin(token);
                UserDTO userDTO = userService.findUserByLogin(userLogin);
                CustomSecurityContextHolder.setLoggedUser(userDTO);
                isAuthorized = true;

            }
        }
        catch (Exception e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        }


        return isAuthorized;
    }
}
