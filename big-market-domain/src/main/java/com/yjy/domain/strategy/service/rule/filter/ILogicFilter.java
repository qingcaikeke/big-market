package com.yjy.domain.strategy.service.rule.filter;

import com.yjy.domain.strategy.model.entity.RuleActionEntity;
import com.yjy.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 抽奖规则过滤接口，如黑名单，权重
 * @create 接收过滤所需要的参数，返回过滤后的行为
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);

}
