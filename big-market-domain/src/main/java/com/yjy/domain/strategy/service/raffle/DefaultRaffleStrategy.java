package com.yjy.domain.strategy.service.raffle;

import com.yjy.domain.strategy.model.vo.RuleTreeVO;
import com.yjy.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.yjy.domain.strategy.repository.IStrategyRepository;
import com.yjy.domain.strategy.service.AbstractRaffleStrategy;
import com.yjy.domain.strategy.service.armory.IStrategyDispatch;
import com.yjy.domain.strategy.service.rule.chain.ILogicChain;
import com.yjy.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.yjy.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.yjy.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yjy
 * @description 默认的抽奖策略实现
 * @create 进行所有的前置规则过滤
 */
//版本1：接收所有的前置规则，通过map找到对应的实现类，完成过滤，首先过滤黑名单，再过滤其他，然后返回take_over或allow
//      接收所有的中置规则String，通过map找到对应的实现类，完成过滤，如果中间某一环节被拦截了，直接返回take_over，否则返回allow
    //版本2：改为模板模式，使用责任链和规则树
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {
    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(repository, strategyDispatch, defaultChainFactory, defaultTreeFactory);
    }

    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }

    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardVO.builder().awardId(awardId).build();
        }
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，未在库表 rule_tree、rule_tree_node、rule_tree_line 配置对应的规则树信息 " + strategyAwardRuleModelVO.getRuleModels());
        }
        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        return treeEngine.process(userId, strategyId, awardId);
    }

}


