package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.LeavingComment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity io.kid1999.esystem.entity.LeavingComment
 */
@Mapper
@Repository
public interface LeavingCommentDao extends BaseMapper<LeavingComment> {

}