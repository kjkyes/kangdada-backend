package com.xk.kangdada.manager;

import com.xk.kangdada.common.ErrorCode;
import com.xk.kangdada.exception.BusinessException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.*;
import io.reactivex.Flowable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * AI管理器
 */
@Component
public class AiManager {

    @Resource
    ClientV4 clientV4;

    //稳定的随机值
    public static final float STABLE_TEMPERATURE = 0.05f;
    //不稳定的随机值
    public static final float UNSTABLE_TEMPERATURE = 0.95f;

    /**
     * 同步不稳定请求（简化消息传递）
     *
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String doSyncUnstableRequest(String systemMessage, String userMessage){
        return doRequest(systemMessage, userMessage, Boolean.FALSE, UNSTABLE_TEMPERATURE);
    }

    /**
     * 同步稳定请求（简化消息传递）
     *
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String doSyncStableRequest(String systemMessage, String userMessage){
        return doRequest(systemMessage, userMessage, Boolean.FALSE, STABLE_TEMPERATURE);
    }

    /**
     * 通用同步请求（简化消息传递）
     *
     * @param systemMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public String doSyncRequest(String systemMessage, String userMessage, Float temperature){
        return doRequest(systemMessage, userMessage, Boolean.FALSE, temperature);
    }


    /**
     * 通用请求（简化消息传递）
     *
     * @param systemMessage
     * @param userMessage
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(String systemMessage, String userMessage, Boolean stream, Float temperature){
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        messages.add(systemChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        messages.add(userChatMessage);
        //构造请求
        return doRequest(messages, stream, temperature);
    }

    /**
     * 通用请求
     *
     * @param messages
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(List<ChatMessage> messages, Boolean stream, Float temperature){
        //构造请求
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(stream)
                .temperature(temperature)
                .invokeMethod(Constants.invokeMethod) //invokeMethodAsync异步调用
                .messages(messages)
                .build();
        try {
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return (String) invokeModelApiResp.getData().getChoices().get(0).getMessage().getContent();
//            return invokeModelApiResp.getMsg();
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 通用流式请求（简化消息传递）
     *
     * @param systemMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public Flowable<ModelData> doStreamRequest(String systemMessage, String userMessage, Float temperature){
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        messages.add(systemChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        messages.add(userChatMessage);
        //构造请求
        return doStreamRequest(messages, temperature);
    }

    /**
     * 通用流式请求
     *
     * @param messages
     * @param temperature
     * @return
     */
    public Flowable<ModelData> doStreamRequest(List<ChatMessage> messages, Float temperature){
        //构造请求
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.TRUE)
                .temperature(temperature)
                .invokeMethod(Constants.invokeMethod) //invokeMethodAsync异步调用
                .messages(messages)
                .build();
        try {
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return invokeModelApiResp.getFlowable();
//            return invokeModelApiResp.getMsg();
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }
}
