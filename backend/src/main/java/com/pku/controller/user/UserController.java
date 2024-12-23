package com.pku.controller.user;

import com.pku.pojo.dto.UserDTO;
import com.pku.pojo.entity.User;
import com.pku.properties.JwtProperties;
import com.pku.service.UserService;
import com.pku.utils.JwtUtil;
import com.pku.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        log.info("userDTO: " + userDTO);
        try{
            String decryptedPassword = RSAUtil.decrypt(userDTO.getPassword());
            log.info("decryptedPassword: " + decryptedPassword);
            userDTO.setPassword(decryptedPassword);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("注册密码解密失败");
        }
        Long status = userService.registerUser(userDTO);
        if(status == -1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户已注册");
        if(status == -2) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("邮箱已注册");
        if(status == -3) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("手机号已注册");
        return ResponseEntity.ok("注册成功！");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try{
            String decryptedPassword = RSAUtil.decrypt(userDTO.getPassword());
            log.info("decryptedPassword: " + decryptedPassword);
            userDTO.setPassword(decryptedPassword);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("登录密码解密失败");
        }
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

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestBody User user) {
        log.info("userId: " + user.getId());
        User result = userService.getUserById(user.getId());
        if(result == null) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");}
        return ResponseEntity.ok(result);
    }

}
