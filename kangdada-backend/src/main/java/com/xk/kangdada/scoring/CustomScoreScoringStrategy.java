package com.xk.kangdada.scoring;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xk.kangdada.model.dto.question.QuestionContentDTO;
import com.xk.kangdada.model.entity.App;
import com.xk.kangdada.model.entity.Question;
import com.xk.kangdada.model.entity.ScoringResult;
import com.xk.kangdada.model.entity.UserAnswer;
import com.xk.kangdada.model.vo.QuestionVO;
import com.xk.kangdada.service.QuestionService;
import com.xk.kangdada.service.ScoringResultService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 自定义打分类应用评分策略
 *
 * @author <a href="https://github.com/kjkyes">xk</a>
 * *
 */

@ScoringStrategyConfig(appType = 0, scoringStrategy = 0)
public class CustomScoreScoringStrategy implements ScoringStrategy {
    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;
    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        // 1. 根据 id 查询到题目和题目结果信息（按分数降序排序）
        Long appId = app.getId();
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class)
                        .eq(ScoringResult::getAppId, appId)
                        .orderByDesc(ScoringResult::getResultScoreRange)
        );

        // 2. 统计用户的总得分
        int totalScore = 0;
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContent = questionVO.getQuestionContent();

        //设置假choices，不影响后续存储答案
        List<String> newChoices = JSONUtil.toList(JSONUtil.toJsonStr(choices), String.class);
        // 遍历题目列表
        for (int i = 0; i < newChoices.size(); i++) {
            QuestionContentDTO questionContentDTO = questionContent.get(i);
            // 遍历题目中的选项
            for (QuestionContentDTO.Option option : questionContentDTO.getOptions()) {
                // 如果选项的 key 在用户选择的答案中
                if(option.getScore() > 0 && option.getKey().equals(newChoices.get(0))){
                    int score = Optional.of(option.getScore()).orElse(0);
                    totalScore += score;
                    newChoices.remove(0);
                    if(newChoices.size() == 0){
                        break;
                    }
                }
            }
        }
//        // 遍历题目列表
//        for (QuestionContentDTO questionContentDTO : questionContent) {
//            // 遍历答案列表
//            for (String answer : choices) {
//                // 遍历题目中的选项
//                for (QuestionContentDTO.Option option : questionContentDTO.getOptions()) {
//                    // 如果选项的 key 和答案相同
//                    if (option.getKey().equals(answer)) {
//                        int score = Optional.of(option.getScore()).orElse(0);
//                        totalScore += score;
//                    }
//                }
//            }
//        }
        // 3.遍历得分结果，找到第一个用户分数大于得分范围的结果，作为最终结果
        ScoringResult maxScoreResult = scoringResultList.get(0);

        for (ScoringResult scoringResult : scoringResultList) {
            if (totalScore >= scoringResult.getResultScoreRange()) {
                maxScoreResult = scoringResult;
                break;
            }
        }
        // 4.构造返回值，填充答案对应的属性

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoreResult.getId());
        userAnswer.setResultName(maxScoreResult.getResultName());
        userAnswer.setResultDesc(maxScoreResult.getResultDesc());
        userAnswer.setResultPicture(maxScoreResult.getResultPicture());
        userAnswer.setResultScore(totalScore);
        return userAnswer;
    }
}
