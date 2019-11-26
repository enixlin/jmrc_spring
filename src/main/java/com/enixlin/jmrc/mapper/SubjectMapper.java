package com.enixlin.jmrc.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SubjectMapper {

    @Select("select `平台日期` from subject_balance order by `平台日期` desc limit 1")
    String getLastReportDate();
    

@Select(
    "select  sum(`期末借方`) as income, `货币代码`,`总帐科目`"
    +"from subject_balance "
    +"where `总帐科目`='6411' and `平台日期`='20190930' "
    +"GROUP BY `货币代码` "
    )
    ArrayList<LinkedHashMap<String ,Object>> getCreditBalance(@Param("currency")String currency,@Param("date")String date);

}