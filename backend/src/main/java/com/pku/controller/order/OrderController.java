package com.pku.controller.order;


import com.pku.context.BaseContext;
import com.pku.pojo.dto.OrderDTO;
import com.pku.pojo.vo.OrderVO;
import com.pku.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pku.properties.JwtProperties;
import com.pku.utils.JwtUtil;
import com.pku.context.BaseContext;

@RestController
@Slf4j
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestHeader("token") String token, @RequestBody OrderDTO orderDTO){
        orderDTO.setUserId(BaseContext.getCurrentId());
        Long status = orderService.addOrder(orderDTO);
        if(status == -1){
            return ResponseEntity.notFound().build();
        }else if(status == -2){
            return ResponseEntity.badRequest().body("库存不足");
        }else{
            String msg = "orderId为："+String.valueOf(status);
            log.info(msg);
            return ResponseEntity.ok("下单成功");
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancalOrder(@PathVariable Long id){
        Long status = orderService.cancelOrder(id);
        if(status == -1){
            return ResponseEntity.notFound().build();
        }else if(status == -2){
            return ResponseEntity.badRequest().body("重复删除");
        }else{
            return ResponseEntity.ok("取消成功");
        }
    }

    @GetMapping("")
    public ResponseEntity<Page<OrderVO>> getOderById(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(BaseContext.getCurrentId(), page, size));
    }
}
