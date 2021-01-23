package io.kid1999.esystem.api;

import io.kid1999.esystem.dao.AddressDao;
import io.kid1999.esystem.entity.Address;
import io.kid1999.esystem.utils.AddressAndContactWayUtil;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author kid1999
 * @create 2021-01-18 11:31
 * @description 地址信息管理
 **/
@Slf4j
@RestController
@RequestMapping("/address")
@Api(tags = "地址信息管理")
public class AddressApi {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private AddressAndContactWayUtil addressUtil;

    @GetMapping("/{id}")
    @ApiOperation("获取地址信息")
    public Result getAddressById(@PathVariable Long id){
        log.info("获取地址信息 " + id);
        Address address = addressDao.selectById(id);
        return new Result().success(address);
    }


    @PostMapping("")
    @ApiOperation("生成地址信息，返回id")
    public Result createAddress(@RequestBody HashMap<String,String> map){
        log.info("生成地址信息 ");
        return new Result(200,"success",addressUtil.checkAndSaveAddress(map));
    }
}