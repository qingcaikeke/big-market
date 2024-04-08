package com.yjy.domain.strategy.service.rule.tree;

import com.yjy.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author yjy
 * @description 规则树接口
 * @create 2024-01-27 11:14
 */
public interface ILogicTreeNode {

    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId);

}
