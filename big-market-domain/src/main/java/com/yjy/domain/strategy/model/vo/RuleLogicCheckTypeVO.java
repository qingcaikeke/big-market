package com.yjy.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yjy
 * @description 规则过滤校验类型值对象  放行或接管
 * @create 用于与规则过滤后返回的行为实体信息比较，确定是继续进行还是直接返回
 */
@Getter
@AllArgsConstructor
public enum RuleLogicCheckTypeVO {

    ALLOW("0000", "放行；执行后续的流程，不受规则引擎影响"),
    TAKE_OVER("0001","接管；后续的流程，受规则引擎执行结果影响"),
    ;

    private final String code;
    private final String info;

}
