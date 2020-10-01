package io.kid1999.esystem.utils;

import io.minio.MinioClient;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

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

	private MinioClient instance;

	@PostConstruct
	public void init() {
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
	public boolean bucketExists(String bucketName){
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
	public void makeBucket(String bucketName){
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
	public void putObject(String bucketName, String objectName, String filename){
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
	public void putObject(String bucketName, String objectName, InputStream stream){
		try {
			instance.putObject(bucketName,objectName,stream,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除文件
	 * @param bucketName
	 * @param objectName
	 */
	public void removeObject(String bucketName, String objectName){
		try {
			instance.removeObject(bucketName,objectName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 */
	public InputStream downloadObject(String bucketName,String objectName){
		try {
			return instance.getObject(bucketName,objectName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
