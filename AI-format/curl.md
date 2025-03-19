# API接口测试文档

> 注意：在运行以下命令前，请先登录系统获取token，并将{token}替换为实际的token值

## 用户相关接口

### 1. 用户注册
```bash
curl -X POST "http://localhost:8080/user/register" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=testuser&password=123456&nickname=测试用户&email=test@example.com"
```

### 2. 用户登录
```bash
curl -X POST "http://localhost:8080/user/login" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=testuser&password=123456"
```

### 3. 获取用户信息
```bash
curl -X GET "http://localhost:8080/user/userInfo" \
  -H "Authorization: Bearer {token}"
```

### 4. 更新用户信息
```bash
curl -X PUT "http://localhost:8080/user/update" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "nickname=新昵称&email=newemail@example.com"
```

### 5. 更新密码
```bash
curl -X PATCH "http://localhost:8080/user/updatePwd" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "old_pwd=123456&new_pwd=newpassword&re_pwd=newpassword"
```

## 分类相关接口

### 1. 新增分类
```bash
curl -X POST "http://localhost:8080/category" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "categoryName=测试分类&categoryAlias=测试分类描述"
```

### 2. 获取分类列表
```bash
curl -X GET "http://localhost:8080/category" \
  -H "Authorization: Bearer {token}"
```

### 3. 获取分类详情
```bash
curl -X GET "http://localhost:8080/category/{id}" \
  -H "Authorization: Bearer {token}"
```

### 4. 更新分类
```bash
curl -X PUT "http://localhost:8080/category" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "id=1&categoryName=更新后的分类名&categoryAlias=更新后的分类描述"
```

### 5. 删除分类
```bash
curl -X DELETE "http://localhost:8080/category/{id}" \
  -H "Authorization: Bearer {token}"
```

## 测试说明

1. 所有请求都需要先登录获取token（除了注册和登录接口）
2. 请将{token}替换为实际的token值
3. 请将{id}替换为实际的分类ID
4. 默认服务器地址为localhost:8080，如有不同请自行修改
5. 所有POST/PUT请求的Content-Type都是application/x-www-form-urlencoded 