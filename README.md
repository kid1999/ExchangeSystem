# ESystem
> 基于SpringBoot与Vue.js的物物交换二手交易平台。

![Author](https://img.shields.io/badge/Author-kid1999-lightgrey.svg)
![license](https://img.shields.io/github/license/kid1999/fileUpload.svg)
![ESSystem](https://img.shields.io/badge/kid1999-ExchangeSystem-blue)
![java](https://img.shields.io/badge/language-java-orange.svg)

## 基本设计思路

![image-20200930225525976](https://i.loli.net/2020/09/30/2547M6QDZGL9IwH.png)

## 预期任务：

- [x] 综合oauth2-JWT的安全验证体系

- [x] 基于JavaMail的用户校验

- [x] 基于ES的搜索

- [x] 定时 Redis持久化到SQL、SQL定时到ES

- [x] 基于Spark-ALS算法的商品推荐

- [x] 基于Kafka的任务队列、消息队列

- [x] Spring Cache - Redis 缓存加速

- [x] 热门搜索、热门商品

- [x] 交易逻辑完善

- [ ] 商品分类

- [ ] SQL批量插入优化

- [ ] 流量和点击分析可视化

  

## 环境安装 (基于docker)

> 后期基于Docker-file 和 Docker-compose 统一部署。

参见：[常见Docker环境运维](https://kid1999.github.io/2020/09/07/%E5%B8%B8%E7%94%A8%E7%8E%AF%E5%A2%83Docker%E8%BF%90%E7%BB%B4/)

## 项目技术栈
* 语言：Java1.8、Js
* 后端 : SpringBoot、SpringMVC、Mybatis Plus
* 前端 : Vue.js、Element-UI、MDUI
* 文件存储系统 : MinIO
* 数据库：Mysql5.7、Redis
* 网络服务器 : Nginx
* 搜索引擎：ES 
* 消息中间件：Kafka
* 商品推荐：Spark-ALS

