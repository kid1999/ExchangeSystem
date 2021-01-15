package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Entity io.kid1999.esystem.entity.Comment
 */
@Mapper
@Repository
public interface CommentDao extends BaseMapper<Comment> {


    /**
     * 获取goods的评论
     * @param goodsId
     * @return
     */
    @Select("SELECT\n" +
            "`comment`.id,\n" +
            "`comment`.user_id,\n" +
            "`comment`.goods_id,\n" +
            "`comment`.date,\n" +
            "`comment`.context,\n" +
            "`comment`.`status`,\n" +
            "`comment`.deleted,\n" +
            "`user`.user_name,\n" +
            "`user`.avatar_url\n" +
            "FROM\n" +
            "`comment` ,\n" +
            "`user`\n" +
            "WHERE\n" +
            "`comment`.user_id = `user`.id and comment.goods_id = #{goodsId}")
    List<HashMap<String,String>> findAllByGoodsId(Long goodsId);
}