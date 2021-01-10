package io.kid1999.esystem.utils;

import cn.hutool.core.util.RandomUtil;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.kid1999.esystem.common.Constants.*;

/**
 * @author kid1999
 * @date 2020/9/30 20:38
 * @title 文件工具类  -- 基于minio
 */
@Log
@Component
@Configuration
public class FileUtil {

	private static MinioClient instance;

	@PostConstruct
	public static void init() {
		try {
			instance = new MinioClient(MINIO_URL,MINIO_ACCESSKEY,MINIO_SECREKEY);
			log.info("log success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 判断 bucket是否存在
	 * @param bucketName
	 * @return
	 */
	public static boolean bucketExists(String bucketName){
		try {
			return instance.bucketExists(bucketName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建 bucket
	 * @param bucketName
	 */
	public static void makeBucket(String bucketName){
		try {
			boolean isExist = instance.bucketExists(bucketName);
			if(!isExist) {
				instance.makeBucket(bucketName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件上传
	 * @param bucketName
	 * @param objectName
	 * @param filename
	 */
	public static void putObject(String bucketName, String objectName, String filename){
		try {
			instance.putObject(bucketName,objectName,filename,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 文件上传
	 * @param bucketName
	 * @param objectName
	 * @param stream
	 */
	public static void putObject(String bucketName, String objectName, InputStream stream,long fileSize){
		try {
			PutObjectOptions options = new PutObjectOptions(fileSize,MULTIPART_SIZE);
			instance.putObject(bucketName,objectName,stream,options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除文件
	 * @param bucketName
	 * @param objectName
	 */
	public static void removeObject(String bucketName, String objectName){
		try {
			instance.removeObject(bucketName,objectName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 */
	public static InputStream downloadObject(String bucketName,String objectName){
		try {
			return instance.getObject(bucketName,objectName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 文件随机命名
	 * @return
	 */
	public static String getRandomFileName() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime dateTime = LocalDateTime.now();
		String localTime = dateTime.format(format);
		return localTime + RandomUtil.randomString(5);
	}

}
