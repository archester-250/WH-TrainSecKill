package com.pku.service.Impl;

import com.pku.mapper.UserMapper;
import com.pku.pojo.dto.UserDTO;
import com.pku.pojo.entity.User;
import com.pku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public HttpStatus registerUser(UserDTO userDTO) {
        List<User> users = userMapper.selectUserByUsername(userDTO.getUsername());
        if(users != null && !users.isEmpty()) {
            return HttpStatus.BAD_REQUEST;
        }
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();
        userMapper.insertUser(user);
        return HttpStatus.ACCEPTED;
    }

    @Override
    public Integer loginUser(UserDTO userDTO) {
        if(userDTO.getUsername() == null || userDTO.getPassword() == null || userDTO.getUsername().isEmpty() || userDTO.getPassword().isEmpty()) {
            return -1; //账号或密码不能为空
        }
        List<User> users = userMapper.selectUserByUsername(userDTO.getUsername());
        if (users.isEmpty()) {
            return 0; //账号不存在
        }
        else if(users.get(0).getPassword().equals(userDTO.getPassword())) {
            return 1;
        }
        else{
            return 2; //密码错误
        }
    }
}
