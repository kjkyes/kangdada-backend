package com.xk.kangdada.model.dto.statistic;

import lombok.Data;

@Data
public class AppAnswerCountDTO {

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 用户提交答案数
     */
    private Long answerCount;
}
