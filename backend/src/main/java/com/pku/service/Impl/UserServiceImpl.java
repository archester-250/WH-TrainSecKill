package com.pku.service.Impl;

import com.pku.mapper.UserMapper;
import com.pku.pojo.dto.UserDTO;
import com.pku.pojo.entity.User;
import com.pku.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import static com.pku.utils.PasswordUtil.generateSalt;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Long registerUser(UserDTO userDTO) {
        List<User> users = userMapper.selectUserByUsername(userDTO.getUsername());
        if (users != null && !users.isEmpty()) {
            return (long) -1; //用户已注册
        }
        users = userMapper.selectUserByEmail(userDTO.getEmail());
        if (users != null && !users.isEmpty()) {

            return (long) -2; //邮箱已注册
        }
        users = userMapper.selectUserByMobile(userDTO.getMobile());
        if (users != null && !users.isEmpty()) {
            return (long) -3; //手机号已注册
        }
        String salt = generateSalt();
        log.info("salt.length: {}", salt.length());
        User user = User.builder()
                .nickname(userDTO.getUsername())
                .password(userDTO.getPassword())
                .salt(salt)
                .email(userDTO.getEmail())
                .mobile(userDTO.getMobile())
                .registerDate(LocalDateTime.now())
                .lastLoginDate(null)
                .loginCount(0).build();
        userMapper.insertUser(user);
        return (long) 1;
    }

    @Override
    public Long loginUser(UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getPassword() == null || userDTO.getUsername().isEmpty() || userDTO.getPassword().isEmpty()) {
            return (long) -1; //账号或密码不能为空
        }
        List<User> users = userMapper.selectUserByUsername(userDTO.getUsername());
        if (users == null || users.isEmpty()) {
            return (long) -2; //账号不存在
        } else if (users.get(0).getPassword().equals(userDTO.getPassword())) {
            User user = users.get(0);
            user.setLoginCount(user.getLoginCount() + 1);
            user.setLastLoginDate(LocalDateTime.now());
            userMapper.updateUser(user);
            return users.get(0).getId();
        } else {
            return (long) -3; //密码错误
        }
    }

    @Override
    public User getUserById(Long userid) {
        List<User> users = userMapper.selectUserById(userid);
        if(users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public boolean isAdmin(Long userId) {
        List<User> users = userMapper.selectUserById(userId);
        if(users == null || users.isEmpty() || users.get(0).getRole() == 0) {
            return false;
        }
        return true;
    }
}


