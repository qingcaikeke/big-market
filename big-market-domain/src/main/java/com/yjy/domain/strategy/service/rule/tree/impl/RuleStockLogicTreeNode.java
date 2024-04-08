package com.yjy.domain.strategy.service.rule.tree.impl;

import com.yjy.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.yjy.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.yjy.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yjy
 * @description 库存扣减节点
 * @create 2024-01-27 11:25
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }

}
