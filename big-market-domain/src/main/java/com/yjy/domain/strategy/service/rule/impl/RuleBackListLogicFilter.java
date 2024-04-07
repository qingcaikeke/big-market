package com.yjy.domain.strategy.service.rule.impl;

import com.yjy.domain.strategy.model.entity.RuleActionEntity;
import com.yjy.domain.strategy.model.entity.RuleMatterEntity;
import com.yjy.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.yjy.domain.strategy.repository.IStrategyRepository;
import com.yjy.domain.strategy.service.annotation.LogicStrategy;
import com.yjy.domain.strategy.service.rule.ILogicFilter;
import com.yjy.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.yjy.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yjy
 * @description 【抽奖前规则】黑名单用户过滤规则
 * @create
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_BLACKLIST)
public class RuleBackListLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {

    @Resource
    private IStrategyRepository repository;

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-黑名单 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());

        String userId = ruleMatterEntity.getUserId();

        // 查询规则值配置 //如100:user001,user002,user003
        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 看当前用户id是否在黑名单中
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                //如果在，返回过滤之后要进行的行为
                return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                        .ruleModel(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode())
                        .data(RuleActionEntity.RaffleBeforeEntity.builder()
                                .strategyId(ruleMatterEntity.getStrategyId())
                                .awardId(awardId)
                                .build())
                        .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                        .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                        .build();
            }
        }
        //如果不在，执行后续流程，或者说其他过滤规则
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }

}
