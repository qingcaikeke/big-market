package com.yjy.domain.strategy.service.rule.chain;

/**
 * @author yjy
 * @description 责任链装配
 * @create 2024-01-20 11:53
 */
public interface ILogicChainArmory {

    //填充下一个节点
    ILogicChain appendNext(ILogicChain next);
    //拿到下一个节点，用于调用logic方法
    ILogicChain next();

}
