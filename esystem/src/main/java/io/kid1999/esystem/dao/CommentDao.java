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
            "`comment`.user1_id,\n" +
            "`comment`.user2_id,\n" +
            "`comment`.goods_id,\n" +
            "`comment`.date,\n" +
            "`comment`.context,\n" +
            "`comment`.`status`,\n" +
            "`comment`.deleted,\n" +
            "`user`.username,\n" +
            "`user`.avatar_url\n" +
            "FROM\n" +
            "`comment` ,\n" +
            "`user`\n" +
            "WHERE\n" +
            "`comment`.user1_id = `user`.id and comment.goods_id = #{goodsId} and `comment`.deleted = 0")
    List<HashMap<String,String>> findAllByGoodsId(Long goodsId);


    /**
     * 查询所有user的goods的comment
     * @param userId
     * @return
     */
    @Select("\n" +
            "\n" +
            "SELECT\n" +
            "`comment`.id,\n" +
            "`comment`.user1_id,\n" +
            "`comment`.user2_id,\n" +
            "`comment`.goods_id,\n" +
            "`comment`.date,\n" +
            "`comment`.context,\n" +
            "`comment`.`status`,\n" +
            "`comment`.deleted,\n" +
            "`user`.username,\n" +
            "`user`.avatar_url,\n" +
            "`user`.address_id,\n" +
            "address.address\n" +
            "FROM\n" +
            "`comment` ,\n" +
            "`user` ,\n" +
            "address\n" +
            "WHERE\n" +
            "`comment`.user2_id = #{userId} AND\n" +
            "`comment`.user1_id = `user`.id AND\n" +
            "`comment`.deleted = 0 AND\n" +
            "`user`.address_id = address.id\n")
    List<HashMap<String,String>> findAllByUserId(Long userId);
}