package io.kid1999.esystem.api;

import io.kid1999.esystem.dao.ContactWayDao;
import io.kid1999.esystem.entity.ContactWay;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kid1999
 * @create 2021-02-21 12:34
 * @description 联系方式管理
 **/

@Slf4j
@RestController @RequestMapping("/contactway")
@Api(tags = "联系方式管理")
public class ContactWayApi {
    @Resource
    private ContactWayDao contactWayDao;

    @GetMapping("/{id}")
    Result getContactWay(@PathVariable Long id){
        log.info("查询地址 " + id);
        ContactWay contactWay = contactWayDao.selectById(id);
        return new Result().success(contactWay);
    }
}