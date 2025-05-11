package com.xk.kangdada.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * AI生成题目请求
 *
 * @author <a href="https://github.com/kjkyes">xk</a>
 * *
 */
@Data
public class AiGenerateQuestionRequest implements Serializable {

    /**
     * 应用ID
     */
    private Long appId;


    /**
     * 题目数量
     */
    private Integer questionNum;

    /**
     * 选项数
     */
    private Integer optionNum;

    private static final long serialVersionUID = 1L;
}


