package com.yjy.domain.strategy.service.rule.tree.factory.engine;

import com.yjy.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author yjy
 * @description 规则树组合接口
 * @create 2024-01-27 11:33
 */
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);

}
