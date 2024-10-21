package com.pku.service;

import com.pku.pojo.dto.UserDTO;
import com.pku.pojo.entity.User;
import org.springframework.http.HttpStatus;

public interface UserService {
    public HttpStatus registerUser(UserDTO userDTO);
    public Integer loginUser(UserDTO userDTO);
}
