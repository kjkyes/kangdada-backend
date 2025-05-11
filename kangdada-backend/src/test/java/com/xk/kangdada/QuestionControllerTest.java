package com.xk.kangdada;

import com.xk.kangdada.controller.QuestionController;
import com.xk.kangdada.model.dto.question.AiGenerateQuestionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
@SpringBootTest
public class QuestionControllerTest {
    @Resource
    private QuestionController questionController;

    @Test
    void aiGenerateQuestionSSEVIPTest() throws InterruptedException {
        AiGenerateQuestionRequest request = new AiGenerateQuestionRequest();
        request.setAppId(3L);
        request.setQuestionNum(10);
        request.setOptionNum(2);

        // 模拟普通用户
        questionController.aiGenerateQuestionSSETest(request, false);
        // 模拟普通用户
        questionController.aiGenerateQuestionSSETest(request, false);
        // 模拟VIP用户
        questionController.aiGenerateQuestionSSETest(request, true);

        Thread.sleep(1000000L);
    }

}
