package io.kid1999.esystem.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.TransRecordDao;
import io.kid1999.esystem.entity.TransRecord;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-04 18:03
 * @description 交易记录管理操作
 **/
@RestController
@RequestMapping("trans_record")
@Api(tags = "交易记录管理操作")
public class TransRecordApi {

    @Autowired
    private TransRecordDao transRecordDao;

    @PostMapping("")
    @ApiOperation("创建交易信息")
    Result insertTransRecord(TransRecord transRecord){
        transRecord.setCreateDate(LocalDateTime.now());
        transRecordDao.insert(transRecord);
        return new Result().success();
    }


    @PutMapping("")
    @ApiOperation("修改交易信息")
    Result updateTransRecord(TransRecord transRecord){
        transRecord.setCreateDate(LocalDateTime.now());
        transRecordDao.updateById(transRecord);
        return new Result().success();
    }

    @GetMapping("/{id}")
    @ApiOperation("查询交易信息")
    Result getTransRecord(@PathVariable int id){
        TransRecord record = transRecordDao.selectById(id);
        return new Result(200,"查询成功！",record);
    }

    @GetMapping("")
    @ApiOperation("查询所有交易信息")
    Result getAllTransRecord(){
        QueryWrapper<TransRecord> wrapper = new QueryWrapper<>();
        wrapper.select("*");
        List<TransRecord> records = transRecordDao.selectList(wrapper);
        return new Result(200,"查询成功！",records);
    }

    @GetMapping("/user/{id}")
    @ApiOperation("查询user交易信息")
    Result getTransRecordByUserId(@PathVariable int id){
        QueryWrapper<TransRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        List<TransRecord> records = transRecordDao.selectList(wrapper);
        return new Result(200,"查询成功！",records);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除交易信息")
    Result deleteTransRecord(@PathVariable int id){
        transRecordDao.deleteById(id);
        return new Result(200,"删除成功！");
    }
}

