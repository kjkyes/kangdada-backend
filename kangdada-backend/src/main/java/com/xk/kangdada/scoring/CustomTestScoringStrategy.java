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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义测评类应用评分策略
 *
 * @author <a href="https://github.com/kjkyes">xk</a>
 * *
 */
@ScoringStrategyConfig(appType = 1, scoringStrategy = 0)
public class CustomTestScoringStrategy implements ScoringStrategy {

    @Resource
    private ScoringResultService scoringResultService;

    @Resource
    private QuestionService questionService;

    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        // 1. 根据 id 查询到题目和题目结果信息
        Long appId = app.getId();
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class).eq(ScoringResult::getAppId, appId)
        );

        // 2. 统计用户每个选择对应的属性个数，如 I = 10个，E = 5个
        // 初始化一个 Map，key 为属性，value 为该属性出现的次数
        Map<String, Integer> optionCount = new HashMap<>();

        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContent = questionVO.getQuestionContent();

        // 遍历题目列表
        for (QuestionContentDTO questionContentDTO : questionContent) {
            // 遍历答案列表
            for (String answer : choices) {
                // 遍历题目中的选项
                for (QuestionContentDTO.Option option : questionContentDTO.getOptions()) {
                    // 如果选项的 key 和答案相同
                    if (option.getKey().equals(answer)) {
                        // 获取选项对应的result属性
                        String result = option.getResult();

                        // 如果 optionCount 中没有result属性，则初始化为 0
                        if (!optionCount.containsKey(result)) {
                            optionCount.put(result, 0);
                        }
                        // 在optionCount中增加result属性的出现次数
                        optionCount.put(result, optionCount.get(result) + 1);
                    }
                }
            }
        }


        // 3. 遍历每种评分结果，计算哪个结果的得分最高
        int maxScore = 0;
        ScoringResult maxScoreResult = scoringResultList.get(0);

        for (ScoringResult scoringResult : scoringResultList) {
            List<String> resultProp = JSONUtil.toList(scoringResult.getResultProp(), String.class);
            int score = resultProp.stream().mapToInt(prop -> optionCount.getOrDefault(prop, 0)).sum();

            if (score > maxScore) {
                maxScore = score;
                maxScoreResult = scoringResult;
            }
        }
        // 4. 构造返回值，填充答案对象的属性
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoreResult.getId());
        userAnswer.setResultName(maxScoreResult.getResultName());
        userAnswer.setResultDesc(maxScoreResult.getResultDesc());
        userAnswer.setResultPicture(maxScoreResult.getResultPicture());

        return userAnswer;
    }
}
