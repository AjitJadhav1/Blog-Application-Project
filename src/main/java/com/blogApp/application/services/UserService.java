package com.blogApp.application.services;

import java.util.List;

import com.blogApp.application.payloads.UserDTO;


public interface UserService {

    UserDTO createUser(UserDTO user);
    UserDTO UpdateUser(UserDTO user, Integer userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUsers();
    void deleteUser(Integer userId);

}

