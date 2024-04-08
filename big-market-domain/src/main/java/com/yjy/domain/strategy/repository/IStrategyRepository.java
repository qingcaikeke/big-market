package com.yjy.domain.strategy.repository;



import com.yjy.domain.strategy.model.entity.StrategyAwardEntity;
import com.yjy.domain.strategy.model.entity.StrategyEntity;
import com.yjy.domain.strategy.model.entity.StrategyRuleEntity;
import com.yjy.domain.strategy.model.vo.RuleTreeVO;
import com.yjy.domain.strategy.model.vo.StrategyAwardRuleModelVO;

import java.util.List;
import java.util.Map;

/**
 * @author yjy
 * @description 策略服务仓储接口，实现类在infrastructure的repository
 * @create
 */
public interface IStrategyRepository {
    /**
     * 查当前策略下的所有奖品
     * @param strategyId
     * @return
     */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 构建用于抽奖的map并存到redis
     * @param strategyId
     * @param rateRange
     * @param strategyAwardSearchRateTable
     */
    void storeStrategyAwardSearchRateTable(String strategyId, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);
    /**
     * 查到用于抽奖的map和range
     * @param strategyId
     * @param rateKey
     * @return
     */
    Integer getStrategyAwardAssemble(String strategyId, Integer rateKey);
    int getRateRange(Long strategyId);
    int getRateRange(String key);
    //查询策略id包含哪几种抽奖规则
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);
    //查到给定策略id下的rule_weight下的信息，示例：104,105,106,107,108,109(查的是整条记录)
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleWeight);

    //从strategy_rule表中查到rule_val,如100:user001,user002,user003（查的是值对象）
    String queryStrategyRuleValue(Long strategyId, String ruleModel);
    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);
    //用于中置规则，查的是抽到的奖品，有什么规则，如6次后才能解锁
    //rule_models:    rule_lock,rule_luck_award
    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);
    //根据规则树id，查规则树信息
    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);
}
