package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.LeavingComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Entity io.kid1999.esystem.entity.LeavingComment
 */
@Mapper
@Repository
public interface LeavingCommentDao extends BaseMapper<LeavingComment> {

    /**
     * 查询用户的所有评论
     * @param userId
     * @return
     */
    @Select("SELECT\n" +
            "leaving_comment.id,\n" +
            "leaving_comment.user1_id,\n" +
            "leaving_comment.user2_id,\n" +
            "leaving_comment.date,\n" +
            "leaving_comment.context,\n" +
            "leaving_comment.`status`,\n" +
            "leaving_comment.deleted,\n" +
            "`user`.username,\n" +
            "`user`.avatar_url\n" +
            "FROM\n" +
            "leaving_comment ,\n" +
            "`user`\n" +
            "WHERE\n" +
            "`user`.id = leaving_comment.user1_id and user.id = #{userId}")
    List<HashMap<String,String>> findAllByUser1Id(Long userId);


    /**
     * 查询用户的所有评论
     * @param userId
     * @return
     */
    @Select("SELECT\n" +
            "leaving_comment.id,\n" +
            "leaving_comment.user1_id,\n" +
            "leaving_comment.user2_id,\n" +
            "leaving_comment.date,\n" +
            "leaving_comment.context,\n" +
            "leaving_comment.`status`,\n" +
            "leaving_comment.deleted,\n" +
            "`user`.username,\n" +
            "`user`.avatar_url\n" +
            "FROM\n" +
            "leaving_comment ,\n" +
            "`user`\n" +
            "WHERE\n" +
            "`user`.id = leaving_comment.user2_id and user.id = #{userId}")
    List<HashMap<String,String>> findAllByUser2Id(Long userId);

}