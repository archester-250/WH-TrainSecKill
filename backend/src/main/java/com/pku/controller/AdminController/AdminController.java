package com.pku.controller.AdminController;

import com.pku.pojo.entity.SeckillGoods;
import com.pku.service.RedisService;
import com.pku.service.SeckillGoodsService;
import com.pku.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user/admin")
public class AdminController {
    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createSeckillActivity(@RequestBody SeckillGoods seckillGoods, @RequestParam Long userId) {
        if(!userService.isAdmin(userId)) {
            return ResponseEntity.badRequest().body("该用户无权限访问");
        }
        Long status = seckillGoodsService.createSeckillGoods(seckillGoods);
        redisService.putGoods(seckillGoods.getGoodsId(), seckillGoods);
        return ResponseEntity.ok("创建成功："+status);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSeckillActivity(@RequestBody SeckillGoods seckillGoods, @RequestParam Long userId) {
        if(!userService.isAdmin(userId)) {
            return ResponseEntity.badRequest().body("该用户无权限访问");
        }
        Long status = seckillGoodsService.updateSeckillGoods(seckillGoods);
        redisService.putGoods(seckillGoods.getGoodsId(), seckillGoods.getStockCount());
        return status == 0 ? ResponseEntity.notFound().build() : ResponseEntity.ok("更新成功");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSeckillActivity(@RequestBody SeckillGoods seckillGoods, @RequestParam Long userId) {
        if(!userService.isAdmin(userId)) {
            return ResponseEntity.badRequest().body("该用户无权限访问");
        }
        seckillGoodsService.deleteSeckillGoods(seckillGoods.getId());
        redisService.delGoods(seckillGoods.getGoodsId());
        return ResponseEntity.ok("删除成功");
    }
}
