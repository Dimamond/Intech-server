package com.test.server.service;

import com.test.server.dto.*;
import com.test.server.entity.User;
import com.test.server.security.*;
import com.test.server.exception.MyServerException;
import com.test.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProviderService jwtTokenProviderService;


    public TokenDTO authorization(String login) {
        UserDTO userDTO = findUserByLogin(login);
        String token = jwtTokenProviderService.createToken(userDTO.getLogin());
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        return tokenDTO;
    }

    public UserDTO findUserByLogin(String login) {

        if (login == null)
            throw new MyServerException(HttpStatus.BAD_REQUEST,"User login is null");

        User user = userRepository.selectUserByLogin(login);
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setId(user.getId());

        return userDTO;
    }

    public Page<ContentDTO> getUserContent(Integer pageNum, Integer pageSize, Long userId) {
        return userRepository.selectUserContent(pageNum, pageSize, userId).map(content -> {
            ContentDTO contentDTO = new ContentDTO();
            contentDTO.setId(content.getId());
            contentDTO.setName(content.getName());
            return contentDTO;
        });
    }

    public void addUserContent(Long userId, Long contentId) {
        if (userId == null)
            throw new MyServerException(HttpStatus.BAD_REQUEST,"User id is null");
        if (contentId == null)
            throw new MyServerException(HttpStatus.BAD_REQUEST,"Content id is null");
        userRepository.insertUserContent(userId, contentId);
    }

    public void deleteUserContent(Long userId, Long contentId) {
        if (userId == null)
            throw new MyServerException(HttpStatus.BAD_REQUEST,"User id is null");
        if (contentId == null)
            throw new MyServerException(HttpStatus.BAD_REQUEST,"Content id is null");
        userRepository.deleteUserContent(userId, contentId);
    }




}
