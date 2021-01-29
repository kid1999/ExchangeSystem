package io.kid1999.esystem.common;

import org.springframework.context.annotation.Configuration;

/**
 * @author kid1999
 * @date 2020/9/30 20:49
 * @title 常量配置
 */

@Configuration
public class Constants {
	/**
	 * MINIO 网址
	 */
	public static final String MINIO_URL = "http://159.75.6.26:9000";
	/**
	 * MINIO 用户
	 */
	public static final String MINIO_ACCESSKEY = "kid1999";
	/**
	 * MINIO 密码
	 */
	public static final String MINIO_SECREKEY = "pass=kid1999";

	/**
	 * 设置过期时间
	 */
	public static final int TOKEN_EXPIRE_DATE=30*60*100000;
	/**
	 * token秘钥
	 */
	public static final String TOKEN_SECRET = "ZCEQIUBFKSJBFJH2020BAWE";

	/**
	 * JWT 的 client-id
	 */
	public static final String  CLIENT_ID = "admin";

	/**
	 * JWT 的 client-secret
	 */
	public static final String  CLIENT_SECRET = "123";


	/**
	 * github oauth
	 */
	public static final String GITHUB_CLIENT_ID = "a6fd02d5fee1728dfee2";
	public static final String GITHUB_CLIENT_SECRET = "60752c69673019967d49819fc2c353cc31f5199a";
	public static final String GITHUB_CALLBACK_URL = "http://localhost:8080/api/oauth/callback";

	/**
	 * minio 桶
	 */
	public static final String IMG_BUCKET = "images";
	/**
	 * minio 分块容量
	 */
	public static final long MULTIPART_SIZE = 5242880;

	/**
	 * 默认头像地址
	 */
	public static final String DEFAULT_AVATAR_URL = "http://kid1999.top:9000/default/avatar.png";

	/**
	 * redis 内存保存时间（过期时间） 1 day
	 */
	public static final long REDIS_EXPIRE_DATE = 24*60*60;


	/**
	 * ES goods entry 的 index
	 */
	public static final String ES_GOODS_INDEX = "goods";

}
