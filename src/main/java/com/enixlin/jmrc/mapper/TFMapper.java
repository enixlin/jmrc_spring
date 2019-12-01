package com.enixlin.jmrc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TFMapper {

    @Select("select count(`客户代码`) from tf_middle")
    public int getRecordCount();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @return
	 *String
	 * 创建时间：2019年11月28日
	 */
    @Select("select `数据抽取日期` from tf_middle order by  `数据抽取日期` desc limit 1")
	public String getLastReportDate();

}