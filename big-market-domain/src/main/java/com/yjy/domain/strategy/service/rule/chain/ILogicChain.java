package com.yjy.domain.strategy.service.rule.chain;

/**
 * @author yjy
 * @description 抽奖策略规则责任链接口
 * @create 分离装配和使用
 */
public interface ILogicChain extends ILogicChainArmory{

    /**
     * 进行拦截处理
     */
    Integer logic(String userId, Long strategyId);

}
