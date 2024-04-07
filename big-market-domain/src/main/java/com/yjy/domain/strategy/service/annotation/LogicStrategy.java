package com.yjy.domain.strategy.service.annotation;

import com.yjy.domain.strategy.service.rule.factory.DefaultLogicFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yjy
 * @description 策略自定义枚举
 * @create 放在filter上，指明是什么样的规则
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicStrategy {

    DefaultLogicFactory.LogicModel logicMode();

}
