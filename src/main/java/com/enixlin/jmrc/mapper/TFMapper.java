package com.enixlin.jmrc.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    

    
	@Select("select sum(replace(`本年累计收息`,',','')) as sumInt,`科目` "
	
    		+ " FROM tf_middle "
    		+ " where `科目`='13040301' and `数据抽取日期`='${date}' "
    		+ " group by `科目`")
    public ArrayList<LinkedHashMap<String, Object>> getTFInt(@Param("date")String date);


	// getRTInt
	  
	@Select(
	"select "
	+" c.subject as subject,"
	+" c.sumInt as sumInt_c,"
	+" case when p.sumInt is null or p.sumInt=0 then 0 else p.sumInt end  sumInt_p"
	+" from "
	+"("
	+			"select sum(replace(`本年累计收息`,',','')) as sumInt,`科目` as subject "
    		+ " FROM tf_middle "
			+ " where `科目`='13040301' and `数据抽取日期`='${date}' "
			+" and `特色产品` ='0749-“退税贷”出口退税应收款融资'  "
			+ " group by `科目` "
	+") c"	
	+" left join  "	
	+"("
			+"select sum(replace(`本年累计收息`,',','')) as sumInt,`科目` as subject "
    		+ " FROM tf_middle "
			+ " where `科目`='13040301' and `数据抽取日期`=date_format(date_add('${date}',interval  -1 YEAR),'%Y%m%d' ) "
			+" and `特色产品` ='0749-“退税贷”出口退税应收款融资'  "
			+ " group by `科目`"
	+") p"
	+" on c.subject=p.subject "
			
			
			)
	public ArrayList<LinkedHashMap<String, Object>> getRTInt(@Param("date")String date);


	@Select(

	"select "
	+" c.subject as subject,"
	+" c.sumInt as sumInt_c,"
	+" case when p.sumInt is null or p.sumInt=0 then 0 else p.sumInt end  sumInt_p"
	+" from "
	+"("
	+			"select sum(replace(`本年累计收息`,',','')) as sumInt,`科目` as subject "
    		+ " FROM tf_middle "
			+ " where `科目`='13040301' and `数据抽取日期`='${date}' "
			+" and `特色产品` ='P08300201700512'  "
			+ " group by `科目` "
	+") c"	
	+" left join  "	
	+"("
			+"select sum(replace(`本年累计收息`,',','')) as sumInt,`科目` as subject "
    		+ " FROM tf_middle "
			+ " where `科目`='13040301' and `数据抽取日期`=date_format(date_add('${date}',interval -1 YEAR),'%Y%m%d' ) "
			+" and `特色产品` ='P08300201700512'  "
			+ " group by `科目`"
	+") p"
	+" on c.subject=p.subject ")
public ArrayList<LinkedHashMap<String, Object>> getOrderInt(@Param("date")String date);
}