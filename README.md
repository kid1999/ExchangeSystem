# ESystem
> 基于SpringBoot与Vue.js的物物交换二手平台。

![Author](https://img.shields.io/badge/Author-kid1999-lightgrey.svg)
![license](https://img.shields.io/github/license/kid1999/fileUpload.svg)
![ESSystem](https://img.shields.io/badge/kid1999-ExchangeSystem-blue)
![java](https://img.shields.io/badge/language-java-orange.svg)

## 项目初衷
致力服务于校园二手交易市场，同时作为我的毕业设计。



## 环境安装 (基于docker)

#### mysql

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



## 环境要求
* Docker
* JDK1.8
## 项目技术栈
* 后端 : SpringBoot、SpringMVC、JPA
* 前端 : Vue.js、Element-UI
* 文件存储系统 : MinIO
* 数据库：Mysql5.7、Redis
* 网络服务器 : Nginx
* 搜索引擎：ES 

## pom 主要依赖
* redis
* lombok
* mimio-client
* jpa
* javaMail
* hutool
* swagger2

-----

## 基本设计思路

![image-20200930225525976](https://i.loli.net/2020/09/30/2547M6QDZGL9IwH.png)
