package com.xk.kangdada.model.dto.question;

import lombok.Data;

/**
 * 题目答案封装类
 *
 * @author <a href="https://github.com/kjkyes">xk</a>
 * *
 */
@Data
public class QuestionResultDTO {

    /**
     * 题目
     */
    private String title;

    /**
     * 用户答案
     */
    private String userAnswer;
}
