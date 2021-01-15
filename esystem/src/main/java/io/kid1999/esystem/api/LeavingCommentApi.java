package io.kid1999.esystem.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.LeavingCommentDao;
import io.kid1999.esystem.entity.LeavingComment;
import io.kid1999.esystem.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-04 11:44
 * @description 留言管理操作
 **/
@RestController @RequestMapping("/leavingComment")
@Api(tags = "留言管理操作")
public class LeavingCommentApi {

    @Autowired
    private LeavingCommentDao leavingCommentDao;

    @PostMapping("")
    @ApiOperation("创建留言信息")
    Result insertLivingComment(@RequestBody LeavingComment comment){
        comment.setDate(LocalDateTime.now());
        leavingCommentDao.insert(comment);
        return new Result().success("创建留言成功！");
    }

    @PutMapping("")
    @ApiOperation("修改留言信息")
    Result updateLivingComment(@RequestBody LeavingComment comment){
        comment.setDate(LocalDateTime.now());
        leavingCommentDao.updateById(comment);
        return new Result().success("修改留言成功！");
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取留言")
    Result getLivingCommentById(@PathVariable Long id){
        LeavingComment comment = leavingCommentDao.selectById(id);
        return new Result(200,"获取留言成功！",comment);
    }

    @GetMapping("/me/{userId}")
    @ApiOperation("获取我写的所有留言")
    Result getLivingCommentByMeId(@PathVariable Long userId){
        List<HashMap<String, String>> comments = leavingCommentDao.findAllByUser1Id(userId);
        return new Result(200,"获取留言成功！",comments);
    }

    @GetMapping("/othersToMe/{userId}")
    @ApiOperation("获取写给我的所有留言")
    Result getLivingCommentByUserId(@PathVariable Long userId){
        List<HashMap<String, String>> comments = leavingCommentDao.findAllByUser2Id(userId);
        return new Result(200,"获取留言成功！",comments);
    }


    @GetMapping("")
    @ApiOperation("获取所有留言信息")
    Result getAllLivingComment(){
        QueryWrapper<LeavingComment> wrapper = new QueryWrapper<>();
        wrapper.select("*");
        List<LeavingComment> comments = leavingCommentDao.selectList(wrapper);
        return new Result(200,"获取留言成功！",comments);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除留言信息")
    Result deleteLivingComment(@PathVariable Long id){
        leavingCommentDao.deleteById(id);
        return new Result().success("删除留言成功！");
    }

}