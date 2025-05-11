package com.xk.kangdada.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xk.kangdada.model.entity.ScoringResult;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评分结果表视图
 *
 * This class represents a view object (VO) for scoring results.
 * It is used to transfer data between the client and server.
 *
 * @author <a href="https://github.com/kjkyes">xk</a>
 * *
 */
@Data
public class ScoringResultVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 结果名称，如物流师
     */
    private String resultName;

    /**
     * 结果描述
     */
    private String resultDesc;

    /**
     * 结果图片
     */
    private String resultPicture;

    /**
     * 结果属性集合 JSON，如 [I,S,T,J]
     */
    private List<String> resultProp;

    /**
     * 结果得分范围，如 80，表示 80及以上的分数命中此结果
     */
    private Integer resultScoreRange;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户信息
     * Information of the user who created the scoring result.
     */
    private UserVO user;

    /**
     * 封装类转json对象
     * Converts a ScoringResultVO to a ScoringResult object.

     *
     * @param scoringResultVO the ScoringResultVO to convert
     * @return the converted ScoringResult object
     */
    public static ScoringResult voToObj(ScoringResultVO scoringResultVO) {
        if (scoringResultVO == null) {
            return null;
        }
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultVO, scoringResult); // Copy properties from VO to entity
        scoringResult.setResultProp(JSONUtil.toJsonStr(scoringResultVO.getResultProp())); // Convert tag list to JSON string
        return scoringResult;
    }

    /**
     * 对象转封装类
     * Converts a ScoringResult object to a ScoringResultVO.

     *
     * @param scoringResult the ScoringResult to convert
     * @return the converted ScoringResultVO object
     */
    public static ScoringResultVO objToVo(ScoringResult scoringResult) {
        if (scoringResult == null) {
            return null;
        }
        ScoringResultVO scoringResultVO = new ScoringResultVO();
        BeanUtils.copyProperties(scoringResult, scoringResultVO); // Copy properties from entity to VO
        scoringResultVO.setResultProp(JSONUtil.toList(scoringResult.getResultProp(), String.class)); // Convert JSON string to tag list
        return scoringResultVO;
    }
}
