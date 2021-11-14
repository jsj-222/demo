



# 项目web接口

[TOC]



## 文章web接口

### 查询文章

- 接口地址: [/article/query]()

- 请求方式: GET

- 请求参数


| 参数 |  类型   | 必须 |  说明  |
  | --- | --- | --- | --- |
|  id  | Integer | true | 文章id |
- 返回结果说明

  | 字段名           | 类型       | 说明         |
    | ---------------- | ---------- | ------------ |
  | code             | Integer    | 业务code     |
  | message          | String     | 对code的说明 |
  | data             | Hash       | 返回数据值   |
  | data.id          | Integer    | 文章id       |
  | data.title       | String     | 文章标题     |
  | data.articleType | Integer    | 文章类型     |
  | data.body        | String     | 文章内容     |
  | data.source      | String     | 文章来源     |
  | data.viewCount   | Integer    | 文章查看量   |
  | data.startedAt   | String     | 提交时间(毫秒级别时间戳)     |
  | data.weight      | BigDecimal | 权重         |
  | data.keyword     | String     | 关键字       |
  | data.active      | Integer    | 是否激活     |
  | data.isDelete    | Integer    | 是否被删除   |
  | data.createdAt   | String     | 创建时间(毫秒级别时间戳)     |
  | data.updateAt    | String     | 修改时间(毫秒级别时间戳)     |
  | data.imgUrl      | String     | 图片地址     |



- 响应结果示例

  ```json
  {
  	"code": 0,
  	"message": "success",
  	"data": {
  		"id": 1,
  		"title": "PD-L1/CTLA-4双抗KN046注册临床IND获批准，用于PD-(L)1治疗后进展的非小细胞肺癌",
  		"articleType": 0,
  		"body": "hello world",
  		"sourceUrl": "https://www.wuxuwang.com/zixun/78605309-1c13-11ec-b564-b8599fb66350",
  		"source": "戊戌数据",
  		"viewCount": 0,
  		"startedAt": "1636337448633",
  		"weight": 50.00,
  		"keyword": "",
  		"active": 0,
  		"isDelete": 0,
  		"createdAt": "1636337448633",
  		"updatedAt": "1636337448633",
  		"imgUrl": "https://file.wuxuwang.com/news/1632390728520-1629786207311-2021-08-24_142304.png"
  	}
  }
  ```



### 分页获取文章

- 接口地址：[/article/list]()

- 请求方式: GET

- 请求参数

  | 参数  | 类型    | 必须  | 说明                         |
  | ----- | ------- | ----- | ---------------------------- |
  | index | Integer | true  | 第几页,最小为1               |
  | max   | Integer | false | 每页最多显示多少个, 默认为10 |

- 返回结果说明请参考查询文章

- 返回结果示例

  ```json
  {
  	"code": 20000,
  	"message": "success",
  	"data": [{
  		"id": 1,
  		"title": "PD-L1/CTLA-4双抗KN046注册临床IND获批准，用于PD-(L)1治疗后进展的非小细胞肺癌",
  		"articleType": 0,
  		"body": "hello world",
  		"sourceUrl": "https://www.wuxuwang.com/zixun/78605309-1c13-11ec-b564-b8599fb66350",
  		"source": "戊戌数据",
  		"viewCount": 0,
  		"startedAt": "1636290385000",
  		"weight": 5.55,
  		"keyword": "",
  		"active": 0,
  		"isDelete": 0,
  		"createdAt": "1632844800000",
  		"updatedAt": "1632844800000",
  		"imgUrl": "https://file.wuxuwang.com/news/1632390728520-1629786207311-2021-08-24_142304.png"
  	}, {
  		"id": 2,
  		"title": "连投三家服务机构，加码互联网医疗，字节跳动的医疗梦进展几何？",
  		"articleType": 1,
  		"body": "htmlBody",
  		"active": 1,
  		"isDelete": 0,
  		"createdAt": "1632844800000",
  		"updatedAt": "1632844800000",
  		"imgUrl": "https://cdn.vcbeat.top/upload/source/26/82/42/20/5e5cce7f64bf0.jpg"
  	}, {
  		"id": 3,
  		"title": "htmlBody",
  		"sourceUrl": "https://www.wuxuwang.com/zixun/33d66d30-09fb-11ec-b564-b8599fb66350",
  		"source": "戊戌数据",
  		"viewCount": 0,
  		"startedAt": "1630339200000",
  		"weight": 50.00,
  		"keyword": "创新药,生物药",
  		"active": 1,
  		"isDelete": 0,
  		"createdAt": "1632844800000",
  		"updatedAt": "1632844800000",
  		"imgUrl": "https://file.wuxuwang.com/news/1630381912992-2021-08-31_115127.png"
  	}]
  }
  ```

### 根据标题搜索文章

- 接口地址: /article/search

- 请求方式: GET

- 请求参数

  | 参数    | 类型    | 必须  | 说明                                |
  | ------- | ------- | ----- | ----------------------------------- |
  | keyword | String  | true  | 搜索的关键字                        |
  | index   | Integer | false | 分页的第几页，默认为第一页          |
  | max     | Integer | false | 每页最多显示多少，默认为10，最大100 |

- 返回结果说明: 同分页获取文章
- 返回结果示例: 同分页获取文章

### 删除文章

- 接口地址: [/article/delete]()

- 请求方式: POST

- 请求参数

  | 参数 |  类型   | 必须 |  说明  |
    | --- | --- | --- | --- |
  |  id  | Integer | true | 文章id |

- 返回结果说明

  | 字段名  | 类型    | 说明         |
    | ------- | ------- | ------------ |
  | code    | Integer | 业务code     |
  | message | String  | 对code的说明 |

- 返回结果示例

  ```json
  {
      "code": 0,
      "message": "success"
  }
  ```

### 修改文章

- 接口地址: [/article/modify]()

- 请求方式: POST

- 请求参数

  | 参数        | 类型    | 必须  | 说明                             |
  | ----------- | ------- | ----- | -------------------------------- |
  | id          | Integer | true  | 文章id                           |
  | title       | String  | false | 文章标题                         |
  | articleType | Integer | false | 文章类型                         |
  | body        | String  | false | 文章内容                         |
  | sourceUrl   | String  | false | 文章来源地址                     |
  | source      | String  | false | 文章来源                         |
  | weight      | Double  | false | 权重                             |
  | keyword     | String  | false | 关键字                           |
  | isDelete    | Integer | false | 是否被删除,1为删除,0为没有被删除 |
  | imgUrl      | String  | false | 图片地址                         |

  *建议至少有一个参数被修改后才发送ajax请求*

- 返回结果说明

  | 字段名  | 类型    | 说明         |
    | ------- | ------- | ------------ |
  | code    | Integer | 业务code     |
  | message | String  | 对code的说明 |


- 返回结果示例

  ```json
  {
      "code": 0,
      "message": "success"
  }
  ```



### 添加文章

- 请求接口: [/article/insert]()
- 请求方法: POST

- 请求参数

  | 参数名      | 参数类型   | 必须  | 说明        |
    | ----------- | ---------- | ----- | ----------- |
  | articleType | Integer    | false | 文章类型    |
  | body        | String     | true  | 文章内容    |
  | source      | String     | true  | 文章来源    |
  | imgUrl      | String     | true  | 图片地址    |
  | title       | String     | true  | 文章标题    |
  | weight      | BigDecimal | false | 权重        |
  | keyword     | String     | true  | 关键字      |
  | sourceUrl   | String     | true  | 文章来源url |

- 返回结果说明

  | 字段名  | 类型    | 说明         |
    | ------- | ------- | ------------ |
  | code    | Integer | 业务code     |
  | message | String  | 对code的说明 |
  | data | number  | 新添加的文章id |
- 返回结果示例

  ```json
  {
      "code": 0,
      "message": "success",
      "data": 123
  }
  ```

## 账单web接口

### 查询一天的账单

working~

