# Api文档
## UserController层 "/user/user"
### 1. 注册
#### PostMapping("/register")
ResponseEntity.status(HttpStatus.BAD_REQUEST).body("注册密码解密失败");
ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户已注册");
ResponseEntity.status(HttpStatus.BAD_REQUEST).body("邮箱已注册");
ResponseEntity.status(HttpStatus.BAD_REQUEST).body("手机号已注册");
ResponseEntity.ok("注册成功！");
> 400 Bad Request
> 200 OK

------------
- 请求格式
```json
{
  "username" : "test",
  "password" : "B2snWkw9S768FHOtOLY0iU8owSaSAydjCr18qcFTA2lgDIFlo6fIyfeOlKkdTYnzAByCpaE5HDlKByUNeIObWQ==",
  "email" : "123@123",
  "mobile" : "123456"
}
```
- password
  密钥位数：512，密钥格式：PKCS#8
------------


### 2. 登录
#### PostMapping("/login")
ResponseEntity.status(HttpStatus.BAD_REQUEST).body("注册密码解密失败");
ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名或密码不能为空");
ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户不存在");
ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码错误");
ResponseEntity.ok(token);

------------
- 请求格式
```json
{
  "username" : "test",
  "password" : "B2snWkw9S768FHOtOLY0iU8owSaSAydjCr18qcFTA2lgDIFlo6fIyfeOlKkdTYnzAByCpaE5HDlKByUNeIObWQ=="
}
```
-响应成功格式
```json
{	"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMsImlzcyI6ImFyY2hlc3Rlci0yNTAiLCJpYXQiOjE3MzI0NjI5NzQsImV4cCI6MTczMjQ2NDk3NH0.cEJj_q-grUfBHAQuBLFWUaNaBShiCDAPBl_BzV5bUiM"
}
```

------------

### 3. 查询用户信息
#### GetMapping("/info")

------------

- 请求格式
```json
{
  "id" : "1"
}
```
- 响应格式
```json
{
  "id": 1,
  "nickname": "xuxugege123",
  "password": "123456",
  "salt": "test",
  "email": "123@123",
  "mobile": "201314",
  "registerDate": [
    2024,
    11,
    22,
    19,
    47,
    26
  ],
  "lastLoginDate": null,
  "loginCount": 0
}
```

## ProductController层 "/user/products"
### 1. 分页查询商品列表
#### GetMapping("")

------------
ResponseEntity.ok(productService.findProducts(page, size, keyword, category, priceMin, priceMax));

请求 URL：
```html
http://localhost:28080/user/products?page=1&size=12&keyword=手机&priceMin=1000&priceMax=5000
```

> @RequestParam(defaultValue = "1") int page,
@RequestParam(defaultValue = "3") int size,
@RequestParam(required = false) String keyword,
@RequestParam(required = false) Long category,
@RequestParam(required = false) BigDecimal priceMin,
@RequestParam(required = false) BigDecimal priceMax

请求头：
token:
```json
eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMsImlzcyI6ImFyY2hlc3Rlci0yNTAiLCJpYXQiOjE3MzI0NjI5NzQsImV4cCI6MTczMjQ2NDk3NH0.cEJj_q-grUfBHAQuBLFWUaNaBShiCDAPBl_BzV5bUiM
```

------------

- 响应格式
```html
http://localhost:28080/user/products?page=2&size=2
```
```json
{
  "content": [
    {
      "id": 3,
      "name": "辣条",
      "title": "辣条",
      "img": null,
      "detail": "爽",
      "price": 1.50,
      "stock": 66
    },
    {
      "id": 4,
      "name": "卫生纸",
      "title": "t4",
      "img": null,
      "detail": "d4",
      "price": 3.34,
      "stock": 5
    }
  ],
  "pageable": {
    "pageNumber": 1,
    "pageSize": 2,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 2,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 2,
  "totalElements": 4,
  "size": 2,
  "number": 1,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": false,
  "numberOfElements": 2,
  "empty": false
}
```
```html
http://localhost:28080/user/products?priceMin=1&priceMax=2.5
```
```json
{
  "content": [
    {
      "id": 1,
      "name": "可乐",
      "title": "可乐",
      "img": "",
      "detail": "快乐水",
      "price": 2.50,
      "stock": -1
    },
    {
      "id": 2,
      "name": "雪碧",
      "title": "雪碧",
      "img": null,
      "detail": "清爽",
      "price": 2.50,
      "stock": -1
    },
    {
      "id": 3,
      "name": "辣条",
      "title": "辣条",
      "img": null,
      "detail": "爽",
      "price": 1.50,
      "stock": 66
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 3,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 3,
  "size": 3,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 3,
  "empty": false
}
```

### 2. 查询商品详情
#### GetMapping("/{id}")

------------
ResponseEntity.notFound().build();
ResponseEntity.ok(product);

>404 NOT FOUND

请求头：token:
```json
eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMsImlzcyI6ImFyY2hlc3Rlci0yNTAiLCJpYXQiOjE3MzI0NjI5NzQsImV4cCI6MTczMjQ2NDk3NH0.cEJj_q-grUfBHAQuBLFWUaNaBShiCDAPBl_BzV5bUiM
```

------------
请求URL：
```html
http://localhost:28080/user/products/2
```
- 响应格式
```json
{
  "id": 2,
  "name": "雪碧",
  "title": "雪碧",
  "img": null,
  "detail": "清爽",
  "price": 2.50,
  "stock": -1
}
```


## PublicKeyController层 "/public-key"

------------
### 返回公钥
#### GetMapping("")

------------


ResponseEntity.ok(Map<String, String>);

- 响应格式
```json
{
  "publicKey": "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALmIDD+4R5QU2yV8CjQX3wMYYo6wRZx3OcC84AN5UncLxyEf5ejhFZ7J6mFK8sM8RNedhoxDEM43UScvBWD42fMCAwEAAQ=="
}
```

## CategoryController层 "/user/categories"
### 获取类别
#### @GetMapping("")


------------

ResponseEntity.ok(categories);
- 响应格式
```json
[
    {
        "id": 1,
        "name": "水"
    },
    {
        "id": 2,
        "name": "日用品"
    },
    {
        "id": 3,
        "name": "零食"
    }
]
```

## OrderController层 “/user/order”

------------

### 下单
#### PostMapping("/add")
|情况   |返回结果   |
| ------------ | ------------ |
|商品不存在   |notFound 404  |
|库存不足   |badRequest 400  |
|下单成功   |ok 200  |
请求格式
```json
{
    "goodsId" : 8,
    "goodsCount" : 2
}
```


------------

### 取消订单
#### PostMapping("/cancel/{id}")
|情况   |返回结果   |
| ------------ | ------------ |
|订单不存在|notFound 404|
|重复删除|badRequest 400|
|取消成功|ok 200|

------------


### 获取订单列表
#### GetMapping("")
请求格式`http://localhost:28080/user/order?page=1&size=2`

@RequestParam(defaultValue = "1") int page,
@RequestParam(defaultValue = "3") int size

返回格式
```json
{
    "content": [
        {
            "id": 1,
            "goodsName": "辣条",
            "goodsCount": 3,
            "goodsPrice": 1.50,
            "status": 1,
            "createDate": [
                2024,
                12,
                24,
                15,
                3,
                19
            ],
            "payDate": [
                2024,
                12,
                24,
                15,
                3,
                19
            ]
        },
        {
            "id": 2,
            "goodsName": "辣条",
            "goodsCount": 3,
            "goodsPrice": 1.50,
            "status": 1,
            "createDate": [
                2024,
                12,
                24,
                15,
                3,
                47
            ],
            "payDate": [
                2024,
                12,
                24,
                15,
                3,
                47
            ]
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 2,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 2,
    "totalPages": 1,
    "size": 2,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}
```

# Admin Controller API

## 概述
`AdminController` 是一个用于管理秒杀活动的RESTful API控制器，提供了创建、更新和删除秒杀活动的功能。它通过与 `SeckillGoodsService`、`RedisService` 和 `UserService` 进行交互来实现这些功能。

## 基础信息
- **Base URL**: `/admin/admin`
- **认证**: 需要管理员权限

## API接口

### 管理员登录
一、接口基本信息
- **接口名称**：管理员登录接口
- **接口地址**：`/login`（假设此接口所在的基础路径为项目的根路径，若有不同的基础路径需按照实际情况补充完整）
- **请求方式**：POST
  - ResponseEntity.status(HttpStatus.BAD_REQUEST).body("注册密码解密失败");
  - ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名或密码不能为空");
  - ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户不存在");
  - ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码错误");
  - ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("非管理用户");
  - ResponseEntity.ok(token);
------------
- 请求格式
```json
{
  "username" : "test",
  "password" : "B2snWkw9S768FHOtOLY0iU8owSaSAydjCr18qcFTA2lgDIFlo6fIyfeOlKkdTYnzAByCpaE5HDlKByUNeIObWQ=="
}
```
-响应成功格式
```json
{	"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMsImlzcyI6ImFyY2hlc3Rlci0yNTAiLCJpYXQiOjE3MzI0NjI5NzQsImV4cCI6MTczMjQ2NDk3NH0.cEJj_q-grUfBHAQuBLFWUaNaBShiCDAPBl_BzV5bUiM"
}
```

------------



### 创建秒杀活动
- **URL**: `/create`
- **方法**: `POST`
- **请求参数**:
  - `seckillGoods` (JSON, body): 秒杀商品信息
      ```json
      {
          "goodsId": 1,
          "seckillPrice": 22.22,
          "stockCount": 100,
          "startTime": "2025-01-01T00:00:00",
          "endTime": "2025-01-01T23:59:59"
      }
      ```
- **返回值**:
  - `200 OK`: 创建成功，返回创建状态
      ```json
      {
          "message": "创建成功：1"
      }
      ```
  - `400 Bad Request`: 用户无权限访问
      ```json
      {
          "message": "该用户无权限访问"
      }
      ```

### 更新秒杀活动
- **URL**: `/update`
- **方法**: `PUT`
- **请求参数**:
  - `seckillGoods` (JSON, body): 秒杀商品信息，部分/全部都可
      ```json
      {
          "id": 1,
          "goodsId": 1,
          "seckillPrice": 22.22,
          "stockCount": 50,
          "startTime": "2025-01-01T00:00:00",
          "endTime": "2025-01-01T23:59:59"
      }
      ```
  - `userId` (Long, query): 用户ID
- **返回值**:
  - `200 OK`: 更新成功
      ```json
      {
          "message": "更新成功"
      }
      ```
  - `400 Bad Request`: 用户无权限访问
      ```json
      {
          "message": "该用户无权限访问"
      }
      ```
  - `404 Not Found`: 秒杀商品未找到

### 删除秒杀活动
- **URL**: `/delete`
- **方法**: `DELETE`
- **请求参数**:
  - `seckillGoods` (JSON, body): 秒杀商品信息
      ```json
      {
          "id": 1
      }
      ```
  - `userId` (Long, query): 用户ID
- **返回值**:
  - `200 OK`: 删除成功
      ```json
      {
          "message": "删除成功"
      }
      ```
  - `400 Bad Request`: 用户无权限访问
      ```json
      {
          "message": "该用户无权限访问"
      }
      ```

## 注意事项
- 所有请求都需要管理员权限，否则会返回400错误.
- 创建和更新秒杀活动时，需要提供完整的秒杀商品信息，包括商品ID、库存数量、开始时间和结束时间.
- 删除秒杀活动时，只需要提供秒杀商品的ID即可.


# Seckill Controller API

## 概述
`SeckillController` 是一个用于处理秒杀活动的RESTful API控制器，提供了秒杀操作和查询秒杀商品的功能。它通过与 `RedisService`、`SeckillGoodsService`、`SeckillOrderService` 和 `RabbitTemplate` 进行交互来实现秒杀逻辑。

## 基础信息
- **Base URL**: `/user/seckill`

## API接口

### 秒杀操作
- **URL**: `/sec?goodsId=1`
- **方法**: `GET`
- **请求参数**:
  - `goodsId` (Long, query): 商品ID
- **返回值**:
  - `200 OK`: 秒杀成功
      ```json
      {
          "message": "用户1 秒杀->商品1成功"
      }
      ```
  - `400 Bad Request`: 秒杀失败，原因可能包括库存不足、用户已购买、秒杀活动未开始或已结束等
      ```json
      {
          "message": "商品的库存量没有剩余或商品不存在,秒杀结束"
      }
      ```
  - `404 Not Found`: 商品不存在
- **描述**:
  - 使用Redis和消息队列实现秒杀操作。
  - 首先检查库存，如果库存不足或活动未开始/已结束，则返回相应错误信息。
  - 检查用户是否已购买该商品，如果已购买则返回错误信息。
  - 扣减库存并发送消息到库存消息队列和订单消息队列，记录用户购买信息。

### 查询秒杀商品
- **URL**: `/secGoods`
- **方法**: `GET`
- **返回值**:
  - `200 OK`: 返回所有秒杀商品信息
      ```json
      [
          {
              "id": 1,
              "goodsId": 1,
              "seckillPrice": 22.22,
              "stockCount": 100,
              "startDate": "2025-01-01T00:00:00",
              "endDate": "2025-01-01T23:59:59"
          },
          {
              "id": 2,
              "goodsId": 2,
              "seckillPrice": 33.3,
              "stockCount": 50,
              "startDate": "2025-01-02T00:00:00",
              "endDate": "2025-01-02T23:59:59"
          }
      ]
      ```
- **描述**:
  - 返回所有可用的秒杀商品信息.