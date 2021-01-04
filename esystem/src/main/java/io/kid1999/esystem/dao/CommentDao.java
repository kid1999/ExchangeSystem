package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity io.kid1999.esystem.entity.Comment
 */
@Mapper
@Repository
public interface CommentDao extends BaseMapper<Comment> {

}