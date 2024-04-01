package com.yjy.infrastructure.persistent.dao;

import com.yjy.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yjy
 * @date 2024/4/1
 * @Description
 */
@Mapper
public interface IStrategyAwardDao {
    List<StrategyAward> queryStrategyAwardList();
}
