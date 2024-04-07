package com.yjy.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yjy
 * @date 2024/4/7
 * @Description 抽奖因子，用于完成IRaffleStrategy抽奖动作
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleFactorEntity {
    /** 用户ID */
    private String userId;
    /** 策略ID */
    private Long strategyId;
    /** 奖品ID */
    private Integer awardId;
}
