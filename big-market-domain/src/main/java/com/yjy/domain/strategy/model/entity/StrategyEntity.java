package com.yjy.domain.strategy.model.entity;

import com.yjy.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yjy
 * @description 策略实体
 * @create 2023-12-31 15:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyEntity {

    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖策略描述 */
    private String strategyDesc;
    /** 抽奖规则模型 示例rule_weight,rule_blacklist */
    private String ruleModels;

    /**
     * 查询当前策略id下全部规则
     * @return
     */
    public String[] ruleModels() {
        if (StringUtils.isBlank(ruleModels)) return null;
        return ruleModels.split(Constants.SPLIT);
    }

    /**
     * 查询是否存在rule_weight规则
     * 实体充血：实体对象中添加对应的方法，这样可以把属于实体的方法和实体聚合，让行为和逻辑在一个对象中
     * @return
     */
    public String getRuleWeight() {
        String[] ruleModels = this.ruleModels();
        if(ruleModels==null) return null;
        for (String ruleModel : ruleModels) {
            if ("rule_weight".equals(ruleModel)) return ruleModel;
        }
        return null;
    }

}
