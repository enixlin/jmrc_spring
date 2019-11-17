package com.enixlin.jmrc.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExchangeMapper {

	@Select("<script>select "
			+ "sum(usd_rate*busy_amount) as amount "
			+ "from settle_record "
			+ "where "
			+ "product_Name in ('结汇','售汇','远期结汇','远期售汇') "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getTotalExchangePerformance(
			@Param("start") String start, @Param("end") String end);

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
	ArrayList<LinkedHashMap<String, Object>> getTypeTotal(
			@Param("start") String start, @Param("end") String end);

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @return ArrayList<LinkedHashMap<String,Object>> 创建时间：2019年11月17日
	 */
	@Select("<script>"
			+ "select "
			+ "left(busy_date,6) as month ,"
			+ "sum(busy_amount*usd_rate)/10000 as amount,"
			+ "count(product_name) as times "
			+ "from settle_record "
			+ "where "
			+ "product_name in ('结汇','售汇','远期结汇','远期售汇') "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by month "
			+ "order by month asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getTypeTotalMonth(@Param("start") String start, @Param("end") String end);
	
	
	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @return ArrayList<LinkedHashMap<String,Object>> 创建时间：2019年11月17日
	 */
	@Select("<script>"
			+ "select "
			+ "left(busy_date,6) as month ,"
			+ "sum(busy_amount*usd_rate)/10000 as amount,"
			+ "count(product_name) as times "
			+ "from settle_record "
			+ "where "
			+ "product_name in ('${product}') "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by month "
			+ "order by month asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getProductMonthPerformance(@Param("product")String product,@Param("start") String start, @Param("end")String end);

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @return ArrayList<LinkedHashMap<String,Object>> 创建时间：2019年11月17日
	 */
	@Select("<script>"
			+ "select "
			+ "belong_branch_code as branchId ,"
			+ "belong_branch_name as branchName ,"
			+ "cust_number as clientId ,"
			+ "cust_name as clientName ,"
			+ "sum(busy_amount*usd_rate) as amount,"
			+ "count(product_name) as times "
			+ "from settle_record "
			+ "where "
			+ "product_name in ('${product}') "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by clientId "
			+ "order by amount asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getProductClientDetail(@Param("product")String product,@Param("start") String start, @Param("end")String end);

}
