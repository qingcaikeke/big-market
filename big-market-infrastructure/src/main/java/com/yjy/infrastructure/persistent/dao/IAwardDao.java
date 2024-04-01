package com.yjy.infrastructure.persistent.dao;

import com.yjy.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yjy
 * @date 2024/4/1
 * @Description
 */
@Mapper
public interface IAwardDao {
    //函数名对应mapper select标签里的id名
    List<Award> queryAwardList();
}

