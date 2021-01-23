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

- [ ] 基于ES的搜索

- [ ] 基于RabbitMQ的任务队列、消息队列

- [ ] 基于Flink的日志流

- [ ] 基于Spark的定时日志分析

- [ ] 定时Redis持久化到数据库

- [ ] 流量和点击分析可视化

- [ ] 基于商品or基于用户的商品推荐（spark）

  

## 环境安装 (基于docker)

> 后期基于Docker-file 和 Docker-compose 统一部署。

```
docker pull mysql:5.7
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD='Root=123' mysql
```

#### redis

```
docker pull redis
docker run -p 6379:6379 -d redis redis-server
```

#### minIO

```
docker pull minio/minio
docker run -p 9000:9000 \
  -v /data:/data:z \
  -e "MINIO_ACCESS_KEY=admin" \
  -e "MINIO_SECRET_KEY=Root=123" \
  minio/minio server -d /data
```

#### rabbitMQ

```

docker pull rabbitmq:3-management
docker run -dit --name rabbitmq --restart=always -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS='Root=123' -p 15672:15672 -p 5672:5672 rabbitmq:3-management
```

#### ES

....

## 环境要求
* Docker
* JDK1.8
## 项目技术栈
* 后端 : SpringBoot、SpringMVC、Mybatis Plus
* 前端 : Vue.js、Element-UI、MDUI
* 文件存储系统 : MinIO
* 数据库：Mysql5.7、Redis
* 网络服务器 : Nginx
* 搜索引擎：ES 
* 消息中间件：rabbitMQ

