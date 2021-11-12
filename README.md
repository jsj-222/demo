# demo

## 启动项目
### 1. 打包项目: 
```shell
    # 跳过测试进行打包
    mvn package -D maven.test.skip=true
    
    cd app-core/target
    
    # 将jar包移动到合适的位置
    mv app-core-1.0.jar xxx
```
### 2.添加配置文件
```shell
    # 在jar包所在目录创建一个文件夹
    mkdir "config"
    
    cd config
    
    vim application.yaml
```
### 3.添加配置
```yaml
spring:
  profiles:
    # 使用生产环境的配置文件
    active: pro
    # 配置数据库
  datasource:
    # url要写时区, 不然大概率报错
    url: jdbc:mysql://localhost:3306/article_collect?ServerTimezone=GMT%2B8
    username: root
    password: abc123
# 如需配置端口，可以按照如下格式修改，默认为8080
server:
  port: 8080
```

### 4.启动
请确保在java8环境下
```shell
  java -jar app-core-1.0.jar
```
启动后会在jar包目录下生成logs文件

## 项目模块索引
**app-core**: 项目的主类(启动类)所在位置

**app-security**: web接口安全管理模块(暂时没用)

**app-web**: 提供web接口的模块[(查看web接口)](app-web/README.md)

**app-service**: 为web接口提供相关的服务

**app-data**: 与数据库交互的模块

**app-common**: 工具类
