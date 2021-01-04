package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity io.kid1999.esystem.entity.Activity
 */
@Mapper
@Repository
public interface ActivityDao extends BaseMapper<Activity> {

}