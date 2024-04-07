package com.yjy.domain.strategy.service.raffle;

import com.yjy.domain.strategy.model.entity.*;
import com.yjy.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.yjy.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.yjy.domain.strategy.repository.IStrategyRepository;
import com.yjy.domain.strategy.service.IRaffleStrategy;
import com.yjy.domain.strategy.service.armory.IStrategyDispatch;
import com.yjy.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.yjy.types.enums.ResponseCode;
import com.yjy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yjy
 * @description 抽奖策略抽象类，定义抽奖的标准流程
 * @create 模板方法的设计模式？
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    // 策略仓储服务 -> domain层像一个大厨，仓储层提供米面粮油
    protected IStrategyRepository repository;
    // 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 策略查询
        //如strategy表中 1	100001	抽奖策略	rule_weight,rule_blacklist	2023-12-09 09:37:19	2023-12-09 18:06:34
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);

        // 3. 抽奖前置 - 规则过滤
        // 子类调用 所有前置规则，得到过滤后的行为，行为有两种，如果是非放行得行为就可以返回了
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = this.doCheckRaffleBeforeLogic(RaffleFactorEntity.builder().userId(userId).strategyId(strategyId).build(), strategy.ruleModels());
        //如果是非放行行为
        if (ruleActionEntity!=null && RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())) {
            // 黑名单返回固定的奖品ID
            if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(ruleActionEntity.getRuleModel())) {
                return RaffleAwardEntity.builder()
                        .awardId(ruleActionEntity.getData().getAwardId())
                        .build();
            } // 如果是权重行为，权重根据返回的权重信息进行抽奖
            else if (DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode().equals(ruleActionEntity.getRuleModel())) {

                RuleActionEntity.RaffleBeforeEntity raffleBeforeEntity = ruleActionEntity.getData();
                String ruleWeightValueKey = raffleBeforeEntity.getRuleWeightValueKey();
                Integer awardId = strategyDispatch.getRandomAwardId(strategyId, ruleWeightValueKey);
                return RaffleAwardEntity.builder()
                        .awardId(awardId)
                        .build();
            }
        }

        // 4. 默认抽奖流程,或者说没有规则的情况下执行抽奖
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);

        // 5. 查询奖品规则「抽奖中（拿到奖品ID时，过滤规则）、抽奖后（扣减完奖品库存后过滤，抽奖中拦截和无库存则走兜底）」
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);

        // 6. 抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity = this.doCheckRaffleCenterLogic(RaffleFactorEntity.builder()
                .userId(userId)
                .strategyId(strategyId)
                .awardId(awardId)
                .build(), strategyAwardRuleModelVO.raffleCenterRuleModelList());

        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionCenterEntity.getCode())){
            log.info("【临时日志】中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。")
                    .build();
        }

        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();
    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

}
