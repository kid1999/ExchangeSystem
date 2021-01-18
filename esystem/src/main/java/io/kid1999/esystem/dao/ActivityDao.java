package io.kid1999.esystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.kid1999.esystem.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Entity io.kid1999.esystem.entity.Activity
 */
@Mapper
@Repository
public interface ActivityDao extends BaseMapper<Activity> {


    @Select("SELECT\n" +
            "activity.id,\n" +
            "activity.activity_context as activityContext,\n" +
            "activity.activity_title as activityTitle,\n" +
            "activity.activity_description as activityDescription,\n" +
            "activity.address_id as addressId,\n" +
            "activity.detail_address as detailAddress,\n" +
            "activity.create_user_id as createUserId,\n" +
            "activity.start_time as startTime,\n" +
            "activity.end_time as endTime,\n" +
            "activity.deleted,\n" +
            "activity.number_of_join as numberOfJoin,\n" +
            "activity.`status`,\n" +
            "join_activity.id AS jid\n" +
            "FROM\n" +
            "activity ,\n" +
            "join_activity\n" +
            "WHERE\n" +
            "activity.id = join_activity.activity_id AND\n" +
            "activity.deleted = 0 AND\n" +
            "join_activity.deleted = 0 AND\n" +
            "join_activity.user_id = #{id}")
    List<HashMap<String,String>> findAllByJoinUserId(Long id);
}
