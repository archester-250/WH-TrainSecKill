package com.pku.controller.user;

import com.pku.pojo.dto.UserDTO;
import com.pku.pojo.entity.User;
import com.pku.properties.JwtProperties;
import com.pku.service.UserService;
import com.pku.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        if(userService.registerUser(userDTO) == HttpStatus.ACCEPTED) return ResponseEntity.ok("注册成功！");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("用户名重复");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        Long status = userService.loginUser(userDTO);
        if(status == -1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名或密码不能为空");
        if(status == -2) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户不存在");
        if(status == -3) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码错误");
        //登录成功时，status即为userid，将其制作成token发送给前端
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", status);
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        return ResponseEntity.ok(token);
    }
}
