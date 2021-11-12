# app-core
所有配置文件都有一个前缀, 如: spring.datasource。 在yaml中添加配置文件就要这么写

```yaml
spring:
  datasource:
    key: value
    key2: value2
```

## 数据库连接池配置

**前缀**：spring.datasource

|    属性名     | 默认值 |                             说明                             |
| :-----------: | :----: | :----------------------------------------------------------: |
|      url      |  null  | 数据库连接url, 一定要加上时区参数。如jdbc:mysql://localhost:3306/article_collect?ServerTimezone=GMT%2B8 |
|   username    |  null  |                       数据库登录用户名                       |
|   password    |  null  |                        数据库登录密码                        |
|  initialSize  |   0    |                  初始化时建立物理连接的个数                  |
|    minIdle    |   4    |                        最小连接池数量                        |
|   maxActive   |   8    |                        最大连接池数量                        |
| maxRetryTimes |   2    | 当一个前台请求需要访问数据库时，其最大重试次数。若在指定的次数内没有连接成功，服务器向前台发送500错误。 |
| retryDuration | 60000  |      当无法连接数据库时，每次重试的间隔时间，单位为毫秒      |

