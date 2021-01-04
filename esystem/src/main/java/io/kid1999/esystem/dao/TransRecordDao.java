package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.TransRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity io.kid1999.esystem.entity.TransRecord
 */
@Mapper
@Repository
public interface TransRecordDao extends BaseMapper<TransRecord> {

}