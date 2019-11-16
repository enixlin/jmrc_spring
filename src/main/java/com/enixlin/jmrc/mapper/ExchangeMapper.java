package com.enixlin.jmrc.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExchangeMapper  {

	@Select("<script>select "
			+ "sum(usd_rate*busy_amount) as amount "
			+ "from settle_record "
			+ "where "
			+ "product_Name in ('结汇','售汇','远期结汇','远期售汇') "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ "</script>"
			)
	ArrayList<LinkedHashMap<String, Object>> getTotalExchangePerformance(@Param("start")String start, @Param("end")String end);

	
	@Select("<script> "
			+ "select "
			+ "product_name,"
			+ "sum(usd_rate*busy_amount) as amount,"
			+ "count(product_name) as times  "
			+ "from settle_record "
			+ "where "
			+ "product_name in ('结汇','售汇','远期结汇','远期售汇') "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by product_name "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getTypeTotal(@Param("start")String start, @Param("end")String end);


	
	
	

}
