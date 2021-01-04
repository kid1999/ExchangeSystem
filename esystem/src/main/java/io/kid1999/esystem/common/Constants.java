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
	public static final String MINIO_URL = "http://159.75.6.26:9000";
	// MINIO 用户
	public static final String MINIO_ACCESSKEY = "kid1999";
	// MINIO 密码
	public static final String MINIO_SECREKEY = "pass=kid1999";

	//设置过期时间
	public static final long TOKEN_EXPIRE_DATE=30*60*100000;
	//token秘钥
	public static final String TOKEN_SECRET = "ZCEQIUBFKSJBFJH2020BAWE";

	//github oauth
	public static final String GITHUB_CLIENT_ID = "a6fd02d5fee1728dfee2";
	public static final String GITHUB_CLIENT_SECRET = "60752c69673019967d49819fc2c353cc31f5199a";
	public static final String GITHUB_CALLBACK_URL = "https://kid1999.top/oauth/callback/github";


}
