package io.kid1999.esystem.common;

import org.springframework.context.annotation.Configuration;

/**
 * @author kid1999
 * @date 2020/9/30 20:49
 * @title 常量配置
 */

@Configuration
public class Constants {
	// MINIO 网址
	public static final String MINIO_URL = "http://10.16.66.232:9000";
	// MINIO 用户
	public static final String MINIO_ACCESSKEY = "admin";
	// MINIO 密码
	public static final String MINIO_SECREKEY = "Root=123";

	//设置过期时间
	public static final long TOKEN_EXPIRE_DATE=30*60*100000;
	//token秘钥
	public static final String TOKEN_SECRET = "ZCEQIUBFKSJBFJH2020BAWE";



}
