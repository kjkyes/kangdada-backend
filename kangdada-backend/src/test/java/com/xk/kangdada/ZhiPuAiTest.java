package com.xk.kangdada;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ZhiPuAiTest {

    @Resource
    ClientV4 clientV4;
    @Test
    public void test() {
        // 初始化客户端
//        ClientV4 client = new ClientV4.Builder(apiKey).build();
        ClientV4 client = clientV4;
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "作为一名营销专家，请为智谱开放平台创作一个吸引人的slogan");
        messages.add(chatMessage);
//        String requestId = String.format("YourRequestId-d%", System.currentTimeMillis());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod) //invokeMethodAsync异步调用
                .messages(messages)
                .build();
        ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
        System.out.println("model output:" + invokeModelApiResp.getMsg());
    }
}
