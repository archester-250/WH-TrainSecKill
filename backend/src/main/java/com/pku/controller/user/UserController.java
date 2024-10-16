package com.pku.controller.user;

import com.pku.pojo.dto.UserDTO;
import com.pku.pojo.entity.User;
import com.pku.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        if(userService.registerUser(userDTO) == HttpStatus.ACCEPTED) return ResponseEntity.ok("注册成功！");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("用户名重复");
    }
}
