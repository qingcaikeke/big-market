package com.yjy.domain.strategy.service;

import com.yjy.domain.strategy.model.entity.RaffleAwardEntity;
import com.yjy.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author yjy
 * @date 2024/4/7
 * @Description 抽奖策略接口
 * 接收抽奖因子，完成抽奖动作，返回抽奖奖品实体
 * todo
 */
public interface IRaffleStrategy {
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);

}
