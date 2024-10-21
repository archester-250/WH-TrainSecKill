package com.pku.controller.user;

import com.pku.pojo.dto.UserDTO;
import com.pku.pojo.entity.User;
import com.pku.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        if(userService.registerUser(userDTO) == HttpStatus.ACCEPTED) return ResponseEntity.ok("注册成功！");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("用户名重复");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        Integer status = userService.loginUser(userDTO);
        return switch (status) {
            case -1 -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("用户名或密码不能为空");
            case 0 -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("用户不存在");
            case 1 -> ResponseEntity.ok("登录成功！");
            case 2 -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("密码错误");
            default -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("");
        };
    }
}
