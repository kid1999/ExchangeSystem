package io.kid1999.esystem.api;

import io.kid1999.esystem.utils.FileUtil;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static io.kid1999.esystem.common.Constants.IMG_BUCKET;
import static io.kid1999.esystem.common.Constants.MINIO_URL;

/**
 * @author kid1999
 * @create 2021-01-05 9:52
 * @description TODO
 **/
@RestController
@RequestMapping("/file")
@Api(tags = "文件操作")
public class FileApi {


    @PostMapping("")
    @ApiOperation("上传文件到Minio")
    Result insertFile(@RequestParam("file") MultipartFile file){
        String randomFileName = FileUtil.getRandomFileName() +
                getFileNameNoEx(file);
        try {
            InputStream in = file.getInputStream();
            FileUtil.putObject(IMG_BUCKET,randomFileName,in,file.getSize());
            HashMap<String,String> map = new HashMap<>();
            map.put("fileName",MINIO_URL + "/" + IMG_BUCKET + "/" +  randomFileName);
            return new Result().success(map);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result().failed();
        }
    }


    String getFileNameNoEx(MultipartFile file){
        String ex = "";
        if (!file.isEmpty()) {
            int begin = file.getOriginalFilename().indexOf(".");
            int last = file.getOriginalFilename().length();
            ex = file.getOriginalFilename().substring(begin, last);
        }
        return ex;
    }
}