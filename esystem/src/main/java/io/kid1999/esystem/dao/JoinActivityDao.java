package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.JoinActivity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author kid1999
 * @Entity io.kid1999.esystem.entity.JoinActivity
 */
@Mapper
@Repository
public interface JoinActivityDao extends BaseMapper<JoinActivity> {

}