package com.yjy.domain.strategy.model.entity;

import com.yjy.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import lombok.*;

/**
 * @author yjy
 * @description 规则动作实体
 * @create filer过滤后返回，描述过滤之后的行为
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleActionEntity<T extends RuleActionEntity.RaffleEntity> {

    private String code = RuleLogicCheckTypeVO.ALLOW.getCode();
    private String info = RuleLogicCheckTypeVO.ALLOW.getInfo();
    private String ruleModel;
    private T data;

    /**
     * 抽奖规则包含抽奖前的，抽奖中的，抽奖后的，不同类型返回的行为会有差异，以此为父类，其他基于此扩展
     */
    static public class RaffleEntity {

    }

    // 抽奖之前规则，如黑名单，权重（4000积分xx，6000积分xx）
    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class RaffleBeforeEntity extends RaffleEntity {
        /**
         * 策略ID
         */
        private Long strategyId;

        /**
         * 权重值Key；用于抽奖时可以选择权重抽奖。
         * 4000:102,103,104,105
         */
        private String ruleWeightValueKey;

        /**
         * 奖品ID；黑名单奖品100
         */
        private Integer awardId;
    }

    // 抽奖之中
    static public class RaffleCenterEntity extends RaffleEntity {

    }

    // 抽奖之后
    static public class RaffleAfterEntity extends RaffleEntity {

    }

}
