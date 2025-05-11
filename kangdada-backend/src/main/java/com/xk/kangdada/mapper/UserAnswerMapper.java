package com.xk.kangdada.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xk.kangdada.model.dto.statistic.AppAnswerCountDTO;
import com.xk.kangdada.model.dto.statistic.AppAnswerResultCountDTO;
import com.xk.kangdada.model.entity.UserAnswer;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 小康
* @description 针对表【user_answer(用户答题记录)】的数据库操作Mapper
* @createDate 2025-04-06 19:00:49
* @Entity com.xk.kangdada.model.entity.UserAnswer
*/
public interface UserAnswerMapper extends BaseMapper<UserAnswer> {

    @Select("select appId, count(userId) as answerCount from user_answer\n" +
            "    group by appId order by answerCount desc limit 10;")
    // This method returns a list of AppAnswerCountDTO objects, which contains the appId and the count of userIds from the user_answer table, grouped by appId, ordered by answerCount in descending order, and limited to 10 results.
    List<AppAnswerCountDTO> doAppAnswerCount();

    @Select("select resultName, count(1) as resultCount from user_answer\n" +
            "where appId = #{appId}\n" +
            "group by resultName order by resultCount desc;")
    List<AppAnswerResultCountDTO> doAppResultCount(long appId);
}




