1、项目启动后，使用postman测试
2、token 生成 get请求：http://localhost:8081/user/login?name=zhangsan&password=123  (params 添加参数)
3、token 校验 post请求: http://localhost:8081/user/test   （Header中添加token变量）
4、token 校验 post请求：http://localhost:8081/user/test0   (params 添加参数)
