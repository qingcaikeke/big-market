package com.yjy.domain.strategy.service.rule.chain;

import com.yjy.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @author yjy
 * @description 抽奖策略规则责任链接口
 * @create 分离装配和使用
 */
public interface ILogicChain extends ILogicChainArmory{

    /**
     * 进行拦截处理
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);

}
