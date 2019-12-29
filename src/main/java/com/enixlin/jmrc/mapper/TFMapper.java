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
	
	
	
	
	
	
	
	
	
@Select("<script>"
		+ "select " + 
		"" + 
		"	c.amount_usx as amount_usx_c," + 
		"	c.amount_rmx as amount_rmx_c," + 
		"	c.amount_rmb as amount_rmb_c," + 
		" 	case when p.amount_usx is null or p.amount_usx=0 then 0 else p.amount_usx end  amount_usx_p," + 
		" 	case when	p.amount_rmx is null or p.amount_rmx=0 then 0 else p.amount_rmx end  amount_rmx_p," + 
		" 	case when	p.amount_rmb is null or p.amount_rmb=0 then 0 else p.amount_rmb end  amount_rmb_p," + 
		"	c.rate as usd_rate_c," + 
		" 	case when p.rate is null or p.rate=0 then 0 else p.rate end	usd_rate_p " + 
		"" + 
		" FROM " + 
		"		(" + 
		"		select " + 
		"			a.amount_usx as amount_usx," + 
		"			a.amount_rmb as amount_rmx," + 
		"			b.amount as amount_rmb," + 
		"	  		b.amount/((a.amount_rmb-b.amount)/a.amount_usx) as rate " + 
		" 		FROM " + 
		"			(" + 
		"			select " + 
		"				sum(REPLACE(`综合人民币`,',','')) as amount_rmb," + 
		"				sum(REPLACE(`综合美元`,',','')) as amount_usx " + 
		"" + 
		"			FROM "
		+ "				tf_middle "
		+ "			where "
		+ "				`数据抽取日期`=${date} "
		+ "				and "
		+ " 			`科目` in "
		+ "				<foreach collection='subjects' item='item' open='(' close=')' separator=','> "
		+ "					'${item}'"
		+ "				</foreach>"
		+ " 			and "
		+ "				`特色产品` in  "
		+ "				<foreach collection='special' item='item' open='(' close=')' separator=','> "
		+ "					'${item}'"
		+ "				</foreach>"
		+"" + 
		"			) a " + 
		"" + 
		" left join " + 
		"" + 
		"			(" + 
		"			select" + 
		"				sum(REPLACE(`余额`,',','')) as amount " + 
		"" + 
		"			FROM "
		+ "				tf_middle "
		+ "			where "
		+ "				`数据抽取日期`=${date} "
		+ "				and "
		+ " 			`科目` in "
		+ "				<foreach collection='subjects' item='item' open='(' close=')' separator=','> "
		+ "					'${item}'"
		+ "				</foreach>"
		+ " 			and "
		+ "				`特色产品` in  "
		+ "				<foreach collection='special' item='item' open='(' close=')' separator=','> "
		+ "					'${item}'"
		+ "				</foreach>"
		+ " 			and "
		+ "				`币种`='cny' " + 
		"" + 
		"			) b " + 
		" on 1=1 " + 
		") c " + 
		"" + 
		" left join " + 
		"" + 
		"		(" + 
		"		select " + 
		"			a.amount_usx as amount_usx," + 
		"			a.amount_rmb as amount_rmx," + 
		"			b.amount as amount_rmb," + 
		"	  	b.amount/((a.amount_rmb-b.amount)/a.amount_usx) as rate " + 
		"		FROM " + 
		"			(" + 
		"			select " + 
		"				sum(REPLACE(`综合人民币`,',','')) as amount_rmb," + 
		"				sum(REPLACE(`综合美元`,',','')) as amount_usx " + 
		"" + 
		"			FROM "
		+ "				tf_middle "
		+ "			where "
		+ "				`数据抽取日期`=DATE_ADD(${date},INTERVAL -1 YEAR) "
		+ "				and "
		+ "				`科目` in "
		+ "				<foreach collection='subjects' item='item' open='(' close=')' separator=','> "
		+ "					'${item}'"
		+ "				</foreach>"
		+ " 			and "
		+ "				`特色产品` in  "
		+ "				<foreach collection='special' item='item' open='(' close=')' separator=','> "
		+ "					'${item}'"
		+ "				</foreach>"
		+"		) a " + 
		"" + 
		" 		left join " + 
		"" + 
		"		(" + 
		"		select" + 
		"			sum(REPLACE(`余额`,',','')) as amount " + 
		"		FROM "
		+ "			tf_middle "
		+ "		where "
		+ "			`数据抽取日期`=DATE_ADD(${date},INTERVAL -1 YEAR) "
		+ "			and "
		+ "			`科目` in "
		+ "			<foreach collection='subjects' item='item' open='(' close=')' separator=','> "
		+ "				'${item}'"
		+ "			</foreach>"
		+ " 	and `特色产品` in  "
		+ "		<foreach collection='special' item='item' open='(' close=')' separator=','> "
		+ "			'${item}'"
		+ "		</foreach>"
		+ " 	and `币种`='cny'" + 
		"" + 
		"		) b "  + 
		"" + 
		"		on 1=1" + 
		") p " + 
		"" + 
		"on 1=1"
		+ "</script>"
		)
public 	ArrayList<LinkedHashMap<String, Object>> getTFBalance(@Param("date")String date,@Param("subjects")ArrayList<String> subjects,@Param("special")ArrayList<String> special);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}