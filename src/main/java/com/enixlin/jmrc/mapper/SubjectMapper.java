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
    

    /**
     * 查询指定时间和指定科目的时时点余额
     */
@Select(
        "<script>"
        +" select `货币代码` ,`总账科目`,sum(`借方余额`) "
        +" from subject_balance "
        +" where  "
        +" `总账科目` in "
        +"<foreach collection='' item='item' begin='(' end=')' sperater=',' >"
        +"{item}"
        +"</foreach>"
        +" and "
        +" `平台日期`='?{date}' "
        +" group by `货币代码` "
        +"</script>"
    )
    ArrayList<LinkedHashMap<String ,Object>> getCreditBalance(@Param("subjects")ArrayList<String> subjects,@Param("date")String date);



}