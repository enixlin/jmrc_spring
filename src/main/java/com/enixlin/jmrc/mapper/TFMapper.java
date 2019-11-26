package com.enixlin.jmrc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TFMapper {

    @Select("select count(`客户代码`) from tf_middle")
    public int getRecordCount();

}