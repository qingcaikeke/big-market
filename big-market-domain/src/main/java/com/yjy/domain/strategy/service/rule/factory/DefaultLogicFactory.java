package com.yjy.domain.strategy.service.rule.factory;

import com.yjy.domain.strategy.model.entity.RuleActionEntity;
import com.yjy.domain.strategy.service.annotation.LogicStrategy;
import com.yjy.domain.strategy.service.rule.ILogicFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yjy
 * @description 规则工厂
 * @create 把所有filter规则装到logicFilterMap，用于raffleStrategy
 */
@Service
public class DefaultLogicFactory {

    public Map<String, ILogicFilter<?>> logicFilterMap = new ConcurrentHashMap<>();

    public DefaultLogicFactory(List<ILogicFilter<?>> logicFilters) {
        logicFilters.forEach(logic -> {
            //找到加了注解的类，放到map里，key是规则名，val是filter实例
            LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(), LogicStrategy.class);
            if (null != strategy) {
                logicFilterMap.put(strategy.logicMode().getCode(), logic);
            }
        });
    }

    public <T extends RuleActionEntity.RaffleEntity> Map<String, ILogicFilter<T>> openLogicFilter() {
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_WIGHT("rule_weight", "【抽奖前规则】根据抽奖权重返回可抽奖范围KEY", "before"),
        RULE_BLACKLIST("rule_blacklist", "【抽奖前规则】黑名单规则过滤，命中黑名单则直接返回", "before"),
        RULE_LOCK("rule_lock", "【抽奖中规则】抽奖n次后，对应奖品可解锁抽奖", "center"),
        RULE_LUCK_AWARD("rule_luck_award", "【抽奖后规则】抽奖n次后，对应奖品可解锁抽奖", "after"),
        ;

        private final String code;
        private final String info;
        private final String type;

        public static boolean isCenter(String code){
            //与枚举值比，例RULE_WIGHT
            return "center".equals(LogicModel.valueOf(code.toUpperCase()).type);
        }

        public static boolean isAfter(String code){
            return "after".equals(LogicModel.valueOf(code.toUpperCase()).type);
        }

    }

}
