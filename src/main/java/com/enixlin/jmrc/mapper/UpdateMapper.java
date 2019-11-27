/**
 * 
 */
package com.enixlin.jmrc.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.enixlin.jmrc.entity.SettleRecord;
import com.google.gson.JsonArray;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author linzhenhuan
 *
 */
@Mapper
public interface UpdateMapper extends BaseMapper<SettleRecord> {

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年11月21日
	 */
	@Select("update settle_record set product_name='跨境人民币汇入' where busy_currency='cny' and product_name='汇入汇款'")
	void fixedSettleRecord_input();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年11月21日
	 */
	@Select("update settle_record set product_name='跨境人民币汇出' where busy_currency='cny' and product_name='汇出汇款'")
	void fixedSettleRecord_output();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年11月21日
	 */
	@Select("delete  FROM settle_record where busy_Currency='cny' and cust_Number=''  and (product_Name='跨境人民币汇入' or product_Name='跨境人民币汇出')")
	void fixedSettleRecord_delete_1();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年11月21日
	 */
	@Select("delete  FROM settle_record where busy_Currency='cny' and cust_name='江门农村商业银行股份有限公司'  and (product_Name='跨境人民币汇入' or product_Name='跨境人民币汇出')")
	void fixedSettleRecord_delete_2();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @return
	 *String
	 * 创建时间：2019年11月21日
	 */
	@Select("select updatedate from updatelog where type='${type}' ")
	String getLastUpdateDate(@Param("type") String type);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @return
	 *String
	 * 创建时间：2019年11月21日
	 */
	@Delete("delete from   updatelog where type='${type}' ")
	int deleteUpdatelelog(@Param("type") String type);
	



	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param record 
	 * @return
	 *Object
	 * 创建时间：2019年11月12日
	 */
	@Insert("<script>"
			+ "insert into subject_balance  values "
			+ "<foreach collection='records' item='item' open=' ' close=' ' separator=','>"
			+ 		"<foreach collection='item' item='itemIN' open=' (' close=' )' separator=','>"
			+ 			"'${itemIN}'"
			+ 		"</foreach> "
			+ "</foreach> "
			+ "</script>")
	int addSubjectBalance(@Param("records")ArrayList<LinkedHashMap<String, Object>> record);

	
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

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param reocrd
	 *void
	 * 创建时间：2019年11月21日
	 */
	@Insert("<script>"
			+ "Insert into settle_record values "
			+ "<foreach collection='record' item='item' open=' (' close=' )' separator=','>"
			+ 			"${item}"
			+ "</foreach> "
			+ "</script>")
	void addSettle(@Param("record")SettleRecord reocrd);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param datatime
	 * @param type
	 *void
	 * 创建时间：2019年11月22日
	 */
	@Options(useGeneratedKeys=true,  keyColumn="id")
	@Insert("Insert into updatelog (updatedate,type) values('${date}','${type}')")
	void updatelog(@Param("date")String datatime, @Param("type")String type);

	
	@Delete("delete from settle_record where  busy_Date>=#{start} and busy_Date<=#{end}  ")
	void deleteSettleRecord(@Param("start")String start, @Param("end")String end);

	@Delete("delete from subject_balance where  `平台日期`>=#{start} and `平台日期`<=#{end}  ")
	void deleteSubjectRecord(@Param("start")String start, @Param("end")String end);

	@Delete("delete from tf_middle where   `数据抽取日期`>=#{start} and `数据抽取日期`<=#{end}  ")
	void deleteTFRecord(@Param("start")String start, @Param("end")String end);

}
