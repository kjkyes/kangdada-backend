package com.xk.kangdada.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * App评分策略枚举枚举
 *
 * @author <a href="https://github.com/kjkyes">xk</a>
 * *
 */
public enum ScoringStrategyEnum {

    CUSTOM("自定义", 0),
    AI("AI", 1);

    private final String text;

    private final Integer value;

    ScoringStrategyEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        // 链式调用，响应式编程中大多是如下的链式调用的语法
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static ScoringStrategyEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ScoringStrategyEnum anEnum : ScoringStrategyEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
