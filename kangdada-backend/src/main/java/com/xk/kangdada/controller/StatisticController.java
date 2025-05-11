package com.xk.kangdada.controller;

import com.xk.kangdada.common.BaseResponse;
import com.xk.kangdada.common.ErrorCode;
import com.xk.kangdada.common.ResultUtils;
import com.xk.kangdada.exception.ThrowUtils;
import com.xk.kangdada.mapper.UserAnswerMapper;
import com.xk.kangdada.model.dto.statistic.AppAnswerCountDTO;
import com.xk.kangdada.model.dto.statistic.AppAnswerResultCountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * App 统计分析接口
 *
 * @author <a href="https://github.com/kjkyes">xk</a>
 * *
 */
@RestController
@RequestMapping("/app/statistic")
@Slf4j
public class StatisticController {

    @Resource
    private UserAnswerMapper userAnswerMapper;

    /**
     * 热门应用及其回答数统计（top 10）
     * @return
     */
    @GetMapping("/answer_count")
    public BaseResponse<List<AppAnswerCountDTO>> doAppAnswerCount() {
        return ResultUtils.success(userAnswerMapper.doAppAnswerCount());
    }

    /**
     * 某应用结果分布统计
     * @param appId
     * @return
     */
    @GetMapping("/answer_result_count")
    public BaseResponse<List<AppAnswerResultCountDTO>> doAppAnswerResultCount(long appId) {
        ThrowUtils.throwIf(appId == 0 || appId <= 0, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(userAnswerMapper.doAppResultCount(appId));
    }
}
