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


ResponseEntity.ok(PRIVATE_KEY);

- 响应格式
```json
{	    
  "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALmIDD+4R5QU2yV8CjQX3wMYYo6wRZx3OcC84AN5UncLxyEf5ejhFZ7J6mFK8sM8RNedhoxDEM43UScvBWD42fMCAwEAAQ=="
}
```