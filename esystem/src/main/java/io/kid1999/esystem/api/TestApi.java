package io.kid1999.esystem.api;

import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.utils.FileUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kid1999
 * @date 2020/9/30 21:12
 * @title TestApi
 */

@Deprecated
@Slf4j
@RestController
public class TestApi {
	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private UserDao userDao;

	@GetMapping("/getPath")
	public String getFileName(){
//		log.info(fileUtil.bucketExists("images"));
		return "success";
	}

	@GetMapping("/test")
	public Object test(){
		return userDao.selectById(1);
	}
}
