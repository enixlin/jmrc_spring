package com.enixlin.jmrc.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.UnitPerformance;

import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface SettleRecordMapper extends BaseMapper<SettleRecord> {

	@Select("SELECT sum(usd_Rate * busy_Amount)/10000 as performance ,product_Name as name from settle_record where busy_Date>=#{start} and busy_Date<=#{end}  GROUP BY name ")
	ArrayList<IndexPerformance> getAllBusyTypeProformance(@Param("start")String start,@Param("end")String end,@Param("products")ArrayList<String> products); 

	@Select("select sum(busy_amount * IF(usd_rate is null,0,usd_rate)) as performance , belong_branch_code as code from settle_record where busy_date >= #{param1} and busy_date<=#{param2} group by code ")
	ArrayList<UnitPerformance> getAllUnitPerformance(String start, String end);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param start
	 * @param end
	 * @return
	 *List<IndexPerformance>
	 * 创建时间：2019年6月11日
	 */
	@Select("select sum(usd_Rate * busy_Amount)/10000 as performance, product_Name as p,left(busy_date,6) as name  from settle_record where busy_Date>=#{start} and busy_Date<=#{end}  GROUP BY name  ")
	 List<IndexPerformance> getMonthPerformance(@Param("start")String start, @Param("end")String end);
	
	

}
