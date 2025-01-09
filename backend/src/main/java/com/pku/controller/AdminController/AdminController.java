package com.pku.controller.AdminController;

import com.pku.pojo.dto.UserDTO;
import com.pku.pojo.entity.SeckillGoods;
import com.pku.properties.JwtProperties;
import com.pku.service.RedisService;
import com.pku.service.SeckillGoodsService;
import com.pku.service.UserService;
import com.pku.utils.JwtUtil;
import com.pku.utils.RSAUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/admin/admin")
public class AdminController {
    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try{
            String decryptedPassword = RSAUtil.decrypt(userDTO.getPassword());
            log.info("decryptedPassword: " + decryptedPassword);
            userDTO.setPassword(decryptedPassword);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("登录密码解密失败");
        }
        userDTO.setAdminLogin(true);
        Long status = userService.loginUser(userDTO);
        if(status == -1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名或密码不能为空");
        if(status == -2) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户不存在");
        if(status == -3) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码错误");
        if(status == -4) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("非管理用户");
        //登录成功时，status即为userid，将其制作成token发送给前端
        Map<String, Object> claims = new HashMap<>();
        claims.put("adminId", status);
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSeckillActivity(@RequestBody SeckillGoods seckillGoods, @RequestHeader String token) {
        Long userId = JwtUtil.validateJWT(jwtProperties.getAdminSecretKey(), token);
        if(userId == -1 || !userService.isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("该用户无权限访问");
        }
        Long status = seckillGoodsService.createSeckillGoods(seckillGoods);
        redisService.putGoods(seckillGoods.getGoodsId(), seckillGoods);
        return ResponseEntity.ok("创建成功："+status);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSeckillActivity(@RequestBody SeckillGoods seckillGoods, @RequestHeader("token") String token) {
        Long userId = JwtUtil.validateJWT(jwtProperties.getAdminSecretKey(), token);
        if(userId == -1 || !userService.isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("该用户无权限访问");
        }
        Long status = seckillGoodsService.updateSeckillGoods(seckillGoods);
        redisService.putGoods(seckillGoods.getGoodsId(), seckillGoods.getStockCount());
        return status == 0 ? ResponseEntity.notFound().build() : ResponseEntity.ok("更新成功");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSeckillActivity(@RequestBody SeckillGoods seckillGoods, @RequestHeader String token) {
        Long userId = JwtUtil.validateJWT(jwtProperties.getAdminSecretKey(), token);
        if(userId == -1 || !userService.isAdmin(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("该用户无权限访问");
        }
        seckillGoodsService.deleteSeckillGoods(seckillGoods.getId());
        redisService.delGoods(seckillGoods.getGoodsId());
        return ResponseEntity.ok("删除成功");
    }
}
