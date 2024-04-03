package com.yjy.domain.strategy.service.armory;

/**
 * @author yjy
 * @description 策略抽奖调度
 * @create 2023-12-31 15:15
 */
public interface IStrategyDispatch {

    /**
     * 获取抽奖策略装配的随机结果
     *
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    Integer getRandomAwardId(Long strategyId);
    //示例，消耗6000积分后可抽到的奖品范围发生变化，9000积分继续发生变化
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

}
