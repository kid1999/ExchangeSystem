package io.kid1999.esystem.api;

import io.kid1999.esystem.utils.FileUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kid1999
 * @date 2020/9/30 21:12
 * @title TestApi
 */

@RequestMapping("/api")
@Log
@RestController
public class TestApi {
	@Autowired
	private FileUtil fileUtil;


	@GetMapping("/getPath")
	public String getFileName(){
		System.out.println(fileUtil.bucketExists("images"));
		return "success";
	}
}
