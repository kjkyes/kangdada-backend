package com.xk.kangdada.scoring;

import com.xk.kangdada.model.entity.App;
import com.xk.kangdada.model.entity.UserAnswer;

import java.util.List;

/**
 * 评分策略
 *
 * @author <a href="https://github.com/kjkyes">xk</a>
 * *
 */
public interface ScoringStrategy {

    /**
     * 执行评分
     *
     * @param choices
     * @param app
     * @return
     * @throws Exception
     */
    UserAnswer doScore(List<String> choices, App app) throws Exception;

}
