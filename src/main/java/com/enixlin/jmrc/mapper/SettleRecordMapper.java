package com.enixlin.jmrc.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.MonthPerformace;
import com.enixlin.jmrc.entity.Product;
import com.enixlin.jmrc.entity.SettleRange;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.Unit;
import com.enixlin.jmrc.entity.UnitPerformance;
import com.google.gson.JsonArray;

import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface SettleRecordMapper extends BaseMapper<SettleRecord> {

	@Select(" "
			+ "SELECT "
			+ "sum(usd_Rate * busy_Amount)/10000 as performance ,"
			+ "count(busy_amount) as times,"
			+ "product_Name as name "
			+ "from "
			+ "settle_record "
			+ "where "
			+ "busy_Date>=#{start} and busy_Date<=#{end}  GROUP BY name ")
	ArrayList<IndexPerformance> getAllBusyTypeProformance(
			@Param("start") String start, @Param("end") String end,
			@Param("products") ArrayList<String> products);

	/**
	 * 取得所有的经营单位在指定时间段内的国际结算量
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @return ArrayList<UnitPerformance> 创建时间：2019年8月4日
	 */
	@Select("<script>"
			+ "select "
			+ "select sum(busy_amount * usd_rate)/10000 as performance,"
			+ "belong_branch_code as code "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ "group by code "
			+ "</script>")
	ArrayList<UnitPerformance> getAllUnitPerformance(
			@Param("start") String start, @Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * 取得每月的业务量
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @return List<IndexPerformance> 创建时间：2019年6月11日 @
	 */
	@Select("<script> select sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times, "
			+ "left(busy_date,6) as month "
			+ "from settle_record "
			+ " where "
			+ "product_name in  "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by month "
			+ " order by month asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getMonthPerformance(@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： 取得所有的业务统计 </br>
	 * @return List<SettleRange> 创建时间：2019年6月13日
	 */
	@Select("select *  from settle_range")
	List<SettleRange> getsettleRange();

	/**
	 * 取得所有国际结算统计口径内的产品信息
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： 取得所有国际结算统计口径内的产品信息 </br>
	 * @return List<String> 创建时间：2019年6月13日
	 */
	@Select("select *  from settle_record")
	List<String> getProductInRange();

	@Select("select distinct product_name as name  from settle_record  ")
	ArrayList<String> getAllProdects();

	/**
	 * 保存国际结算量的统计口径产品
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param products
	 * @return int 创建时间：2019年 7月7日
	 */
	@Insert("<script> "
			+ "Insert into settle_product_information"
			+ " (name,isSettleRange) values "
			+ "<foreach collection='products' item='item' separator=',' > "
			+ "( #{item},1)"
			+ "</foreach> "
			+ " </script>")
	int saveSettleRangeProduct(@Param("products") ArrayList<String> products);

	/**
	 * 从国际业务流水表中取得所有的业务产品种类信息
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @return ArrayList<String> 创建时间：2019年7月8日
	 */
	@Select("select distinct product_name as name  from settle_record  ")
	ArrayList<String> getAllProductFromSettleRecord();

	/**
	 * 从国际业务产品表中读取所有的参与国际结算统计的外汇业务产品信息
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @return ArrayList<Product> 创建时间：2019年7月8
	 */
	@Select("select *  from product where settleRange='1' ")
	ArrayList<Product> getSettleRangeProduct();

	/**
	 * 取得所有国际结算量产品的分类统计
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @param productName
	 * @return List<IndexPerformance> 创建时间：2019年7月23日
	 */
	@Select("<script> select sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times, "
			+ "product_name as name "
			+ "from settle_record "
			+ " where "
			+ "product_name in  "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by performance desc "
			+ "</script>")
	ArrayList<IndexPerformance> getAllBusySettleTypeProformance(
			@Param("start") String start, @Param("end") String end,
			@Param("products") ArrayList<String> products);

	/**
	 * 取得所有的经营单位名称
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @return ArrayList<String> 创建时间：2019年7月26日
	 */
//	@Select("<script>select distinct belong_branch_name as name from settle_record where busy_date&gt;='20180920' </script>")
	@Select("<script>select distinct belong_branch_name as name from settle_record  </script>")
	ArrayList<String> getAllUnitName();

	/**
	 * 取得所有的经营单位的完整信息
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @return ArrayList<Unit> 创建时间：2019年7月27日
	 */
	@Select("select "
			+ "distinct belong_branch_name as name,"
			+ "belong_branch_code as id "
			+ "from "
			+ "settle_record ")
	ArrayList<Unit> getAllUnitInfo();

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @return ArrayList<IndexPerformance> 创建时间：2019年7月28日
	 */
	@Select("<script> select sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times, "
			+ "product_name as name "
			+ "from settle_record "
			+ " where "
			+ "product_name in  "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by performance desc "
			+ "</script>")
	ArrayList<IndexPerformance> getSettleTypeProformanceByDate(
			@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @param product
	 * @return ArrayList<SettleRecord> 创建时间：2019年7月29日
	 */
	@Select("<script> "
			+ "select * from settle_record where "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ "and "
			+ "product_name = #{product}"
			+ "</script>")
	ArrayList<SettleRecord> getDateRangeDetail(@Param("start") String start,
			@Param("end") String end,
			@Param("product") String product);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param unit
	 * @param products
	 * @param start
	 * @param end void 创建时间：2019年7月30日
	 */
	@Select("<script> "
			+ "select "
			+ "product_Name as name,"
			+ "sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ " cust_Name =#{unit.name} "
			+ " and "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by performance desc "
			+ "</script>")
	ArrayList<IndexPerformance> getClientIndexPerformance(
			@Param("unit") Unit unit,
			@Param("products") ArrayList<Product> products,
			@Param("start") String start, @Param("end") String end);

	@Select("<script> "
			+ "select "
			+ "belong_branch_Name ,"
			+ "product_Name as name,"
			+ "sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ " belong_branch_Name =#{unit.name} "
			+ " and "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by performance desc "
			+ "</script>")
	ArrayList<IndexPerformance> getUnitIndexPerformance(
			@Param("unit") Unit unit,
			@Param("products") ArrayList<Product> products,
			@Param("start") String start, @Param("end") String end);

	/**
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param unit
	 * @param start
	 * @param end
	 * @return ArrayList<MonthPerformace> 创建时间：2019年8月3日
	 */
	@Select("<script> "
			+ "select "
			+ "belong_branch_Name ,"
			+ "left(busy_date,6) as name ,"
			+ "product_Name ,"
			+ "sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ " belong_branch_Code =#{unit.id} "
			+ " and "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by name desc "
			+ "</script>")
	ArrayList<MonthPerformace> getUnitMonthPerformace(@Param("unit") Unit unit,
			@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param expiry
	 * @return ArrayList<UnitTask> 创建时间：2019年8月4日
	 */
	@Select("select * from settle_task where expiry=#{expiry} ")
	ArrayList<HashMap<String, Object>> getUnitTasks(
			@Param("expiry") String expiry);

	@Select("select * from settle_task_percent where expiry=#{expiry} and season=#{season}")
	ArrayList<HashMap<String, Object>> getUnitTasks_season(
			@Param("expiry") String expiry, @Param("season") int season);

	/**
	 * 取得经营单位的国际结算量实绩（用于填充国际结算业务任务完成率表）
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @param products
	 * @return ArrayList<UnitPerformance> 创建时间：2019年8月10日
	 */
	@Select("<script>"
			+ "select belong_branch_name as branchName,"
			+ "belong_branch_code as branchCode,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by branchCode "
			+ " order by branchCode desc "
			+ "</script>")
	ArrayList<HashMap<String, Object>> getUnitPerformance(
			@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param compareDate
	 * @param compareDate2
	 * @param products
	 * @return ArrayList<HashMap<String,Object>> 创建时间：2019年8月25日
	 */
	@Select("<script>"
			+ "select belong_branch_name as branchName,"
			+ "belong_branch_code as branchCode,"
			+ "cust_name as custName,"
			+ "cust_number as custCode,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by custCode "
			+ " order by branchCode desc "
			+ "</script>")
	ArrayList<HashMap<String, Object>> getClientPerformance(
			@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param compareDate
	 * @param end
	 * @return ArrayList<HashMap<String,Object>> 创建时间：2019年8月25日
	 */
	@Select("<script>"
			+ "select cust_Name as custName,"
			+ "cust_number as custCode,"
			+ "belong_branch_code as branchCode,"
			+ "belong_branch_name as branchName "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " and "
			+ "cust_type=#{clientType} "
			+ " group by custCode "
			+ " order by branchCode desc "
			+ "</script>")
	ArrayList<HashMap<String, Object>> getClients(@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products,
			@Param("clientType") String clietnType);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param unit
	 * @param products
	 * @param start
	 * @param end void 创建时间：2019年8月31日
	 */
	@Select("<script>"
			+ "select "
			+ "left(busy_date,6) as month ,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " and "
			+ "belong_branch_code=${unit.getId}"
			+ " group by month "
			+ " order by month asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getUnitSettleMonthPerformance(@Param("unit") Unit unit,
			@Param("products") ArrayList<Product> products,
			@Param("start") String start, @Param("end") String end);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param unit
	 * @param products
	 * @param start
	 * @param end void 创建时间：2019年8月31日
	 */
	@Select("<script>"
			+ "select "
			+ "left(busy_date,6) as month ,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " and "
			+ "cust_number=${unit.getId}"
			+ " group by month "
			+ " order by month asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getClientSettleMonthPerformance(@Param("unit") Unit unit,
			@Param("products") ArrayList<Product> products,
			@Param("start") String start, @Param("end") String end);
	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param unit
	 * @param products
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年8月31日
	 */
	@Select("<script>"
			+ "select "
			+ "id as name , "
			+ "left(busy_date,6) as month ,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by month "
			+ " order by month desc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getTotalSettleMonthPerformance(
			@Param("unit")Unit unit, @Param("products")ArrayList<Product> products, @Param("start")String start, @Param("end")String end);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年9月3日
	 */
	@Select("update settle_record set product_name='跨境人民币汇入' where busy_currency='cny' and product_name='汇入汇款'")
	void fixedSettleRecord_input();

	@Select("update settle_record set product_name='跨境人民币汇出' where busy_currency='cny' and product_name='汇出汇款'")
	void fixedSettleRecord_output();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年9月3日
	 */
	@Select("delete  FROM settle_record where busy_Currency='cny' and cust_Number=''  and (product_Name='跨境人民币汇入' or product_Name='跨境人民币汇出')")
	void fixedSettleRecord_delete_1(); 
	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年9月3日
	 */
	@Select("delete  FROM settle_record where busy_Currency='cny' and cust_name='江门农村商业银行股份有限公司'  and (product_Name='跨境人民币汇入' or product_Name='跨境人民币汇出')")
	void fixedSettleRecord_delete_2();

	@Select("select distinct product_name from settle_record")
	ArrayList<LinkedHashMap<String, Object>> getAllProductsFromSettleRecord();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年9月22日
	 */
	@Delete("delete from product")
	void deleteAllRangeProducts();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param rangeProducts
	 * @return
	 *Object
	 * 创建时间：2019年9月22日
	 */
	@Insert("<script>"
			+ "insert into product (name,settleRange) values "
			+ "<foreach collection='rangeProducts' item='item' open=' ' close=' ' separator=','>"
			+ "(${item.getName},${item.isSettleRange})"
			+ "</foreach> "
			+ "</script>")
	int saveRangeProducts(@Param("rangeProducts")ArrayList<Product> rangeProducts);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param unit
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年9月23日
	 */
	@Select("<script>"
			+ "select "
			+ "product_name ,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " and "
			+ "belong_branch_code=${unit.getId}"
			+ " group by product_name "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getUnitProductPerformance(
			@Param("unit")Unit unit, @Param("start")String start, @Param("end")String end,@Param("products")ArrayList<Product> products);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param unit
	 * @param start
	 * @param end
	 * @param products
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年9月30日
	 */
	@Select("<script>"
			+ "select "
			+ "cust_name ,"
			+ "cust_number ,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " and "
			+ "belong_branch_code=${unit.getId}"
			+ " group by cust_number "
			+ "order by amount desc"
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getUnitClientPerformance(@Param("unit")Unit unit,
			@Param("start")String start, @Param("end")String end, @Param("products")ArrayList<Product> products);

	@Select("select updatedate from updatelog   order by updatedate desc limit 1")
	String getLastUpdateDate();


	//getTotalSettlePerformance
	@Select("<script>"
			+ "select "
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ "</script>")
	String getTotalSettlePerformance(
			@Param("start")String start, @Param("end")String end, @Param("products")ArrayList<Product> products);
	
	
	@Insert("insert into updatelog (updatedate) value(#{datatime})")
	void updatelog(@Param("datatime")String datatime);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param product
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<HashMap<String,Object>>
	 * 创建时间：2019年10月12日
	 */
	@Select("<script> "
			+ "select "
			+ "left(busy_date,6) as month, "
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name ='${product}' "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by month "
			+ " order by month asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getProductMonthPerformance(
			@Param("product")String product, @Param("start")String start, @Param("end")String end);

	@Select("<script> "
			+ "select "
			+ "cust_number as clientId, "
			+ "cust_name as clientName, "
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name ='${product}' "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by clientId "
			+ " order by amount asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getProductClientDetail(@Param("product")String product, @Param("start")String start, @Param("end")String end);

	
	

	
	
	@Select("<script> "
			+ "select "
			+ "belong_branch_code as belong_branch_number,"
			+ "belong_branch_name,"
			+ "cust_number , "
			+ "cust_name , "
			+ "product_name , "
			+ "busy_date , "
			+ "busy_currency,"
			+ "busy_amount,"
			+ "usd_rate "
			+ "from settle_record "
			+ "where "
			+ "product_Name ='${product}' "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " order by busy_date asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getProductDetail(@Param("product")String product, @Param("start")String start, @Param("end")String end);

	@Select("<script> "
			+ "select "
			+ "product_name , "
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by product_name  "
			+ "order by amount desc"
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getAllProductDetail(@Param("products")ArrayList<Product> products, @Param("start")String start, @Param("end")String end);
	
	@Select("<script>"
			+ "select "
			+ "product_name ,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " and "
			+ "cust_number=${unit.getId}"
			+ " group by product_name "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getClientProductPerformance(@Param("unit")Unit unit,@Param("start") String start, @Param("end")String end,@Param("products")ArrayList<Product> products);

	@Select("select busy_date from settle_record order by busy_date desc limit 1")
	String getLastBusyDate();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param client
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年10月17日
	 */
	@Select("<script> "
			+ "select "
			+ "belong_branch_code as belong_branch_number,"
			+ "belong_branch_name,"
			+ "cust_number , "
			+ "cust_name , "
			+ "product_name , "
			+ "busy_date , "
			+ "busy_currency,"
			+ "busy_amount,"
			+ "usd_rate "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "cust_number ='${client}' "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " order by busy_date asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getClientDetail(@Param("client")String client,
			@Param("start")String start, @Param("end")String end,@Param("products")ArrayList<Product> products);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年10月18日
	 */
	@Delete("delete from updatelog")
	void truncateUpdateLog();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param ja
	 * @return
	 *Object
	 * 创建时间：2019年10月29日
	 */
	@Insert("<script>"
			+ "insert into tf_middle  values "
			+ "<foreach collection='records' item='item' open=' ' close=' ' separator=','>"
			+ 		"<foreach collection='item' item='itemIN' open=' (' close=' )' separator=','>"
			+ 			"${itemIN}"
			+ 		"</foreach> "
			+ "</foreach> "
			+ "</script>")
	int addTF(@Param("records")JsonArray ja);
	
	@Delete("delete from tf_middle")
	void truncateTFMiddle();
}
