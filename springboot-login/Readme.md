## 说明

主要包括JWT 登录验证  和 Controller 中的参数注入 

### JWT部分说明
```
 流程说明：
   1） 登录成功后，用户的信息（即Token）
         -----先会被存储到 Redis 中,用于再次访问的验证
         -----然后会被会被发送给前端，用于前端在LocalStorage/SessionStorage 中的存储
         
   2） 再次访问，前端页面将Token 信息封装在 Header 中，发送请求。
       后台解析token, 与redis中存储的进行比对，判断登录情况
    
       
```
### Controller 中的参数注入说明

- 核心代码
``` 
    @GetMapping("/list")
    @ResponseBody
    @Authorization
    public ResponseEntity<Object> list( @CurrentUser User user) {
        if (user != null) {
            System.out.println("用户数据注入成功：" + user.toString());
        }

        List<User> users = userService.getUserList();
        return  new ResponseEntity<Object>(ResultModel.ok(users), HttpStatus.OK);
    }
    
 
```
说明： @Authorization ：此注解用于登录校验，未登录跳转到登录<br/>
       @CurrentUser User user :避免每次 获取用户的信息的时候，都需要request.getAttribute("userId");<br/>
                               直接一个注解搞定



## JWT构成

 一个JWT实际上就是一个字符串，它由三部分组成，头部、载荷与签名。
 
```
     头部：加密算法
     载荷：签发者信息，包含（公司、秘钥、用户信息）
     签名：头部、载荷合成后，其合成的 "." 连接的字符串与秘钥 按照`头部`表明的算法进行加密，形成签名
```











