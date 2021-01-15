package io.kid1999.esystem.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.kid1999.esystem.dao.CommentDao;
import io.kid1999.esystem.entity.Comment;
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
 * @create 2021-01-04 11:19
 * @description 商品评论收藏管理
 **/

@RestController @RequestMapping("/comment")
@Api(tags = "商品评论收藏管理")
public class CommentApi {

    @Autowired
    private CommentDao commentDao;

    @PostMapping("")
    @ApiOperation("新建评论")
    Result insertComment(@RequestBody Comment comment){
        comment.setDate(LocalDateTime.now());
        comment.setStatus((byte) 0);
        commentDao.insert(comment);
        return new Result().success();
    }

    @PutMapping("")
    @ApiOperation("修改评论")
    Result updateComment(@RequestBody Comment comment){
        comment.setDate(LocalDateTime.now());
        commentDao.updateById(comment);
        return new Result().success();
    }

    @GetMapping("/{userId}")
    @ApiOperation("根据user_id获取评论")
    Result selectComment(@PathVariable int userId){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Comment> comments = commentDao.selectList(wrapper);
        return new Result(200,"查询成功！",comments);
    }

    @GetMapping("/goods/{goodsId}")
    @ApiOperation("根据user_id获取评论")
    Result selectGoodsComment(@PathVariable Long goodsId){
        List<HashMap<String, String>> comments = commentDao.findAllByGoodsId(goodsId);
        return new Result(200,"查询成功！",comments);
    }

    @GetMapping("")
    @ApiOperation("获取所有评论")
    Result selectAllComment(){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.select("*");
        return new Result(200,"查询成功！",commentDao.selectList(wrapper));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除评论")
    Result deleteComment(@PathVariable int id){
        commentDao.deleteById(id);
        return new Result().success("删除评论成功！");
    }
}