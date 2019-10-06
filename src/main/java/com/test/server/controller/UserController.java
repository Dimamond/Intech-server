package com.test.server.controller;


import com.test.server.dto.*;

import com.test.server.security.*;

import com.test.server.exception.MyServerException;
import com.test.server.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/private/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Простенькая кастомная авторизация")
    @PostMapping(path = "/authorization")
    public ResponseEntity<TokenDTO> authorization(@RequestBody AuthorizationDTO authorizationDTO) {
        try {
            TokenDTO tokenDTO = userService.authorization(authorizationDTO.getLogin());
            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
        } catch (MyServerException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }

    }


    @ApiOperation(value = "Получение контента пользователя")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization, format - 'Bearer token'",
                    required = true,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = "Bearer ")})
    @GetMapping(path = "/content/page")
    public ResponseEntity<Page<ContentDTO>> getContentPage(@RequestParam(required = false, value = "pageNum", defaultValue = "0") Integer pageNum,
                                           @RequestParam(required = false, value = "pageSize", defaultValue = "1") Integer pageSize) {

        UserDTO userDTO = CustomSecurityContextHolder.getLoggedUser();
        try {
            Page<ContentDTO> result = userService.getUserContent(pageNum, pageSize, userDTO.getId());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (MyServerException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }

    @ApiOperation(value = "Добавление контента пользователю")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization, format - 'Bearer token'",
                    required = true,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = "Bearer ")})
    @PutMapping(path = "/content")
    public ResponseEntity addUserContent(@RequestBody IdDTO idDTO) {
        UserDTO userDTO = CustomSecurityContextHolder.getLoggedUser();

        try {
            userService.addUserContent(userDTO.getId(), idDTO.getId());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MyServerException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }


    }

    @ApiOperation(value = "Удаление контента у пользователя")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization, format - 'Bearer token'",
                    required = true,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = "Bearer ")})
    @DeleteMapping(path = "/content")
    public ResponseEntity deleteUserContent(@RequestBody IdDTO idDTO) {
        UserDTO userDTO = CustomSecurityContextHolder.getLoggedUser();

        try {
            userService.deleteUserContent(userDTO.getId(), idDTO.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MyServerException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }

}
