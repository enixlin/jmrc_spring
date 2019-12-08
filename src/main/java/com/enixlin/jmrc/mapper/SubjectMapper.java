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


		@Select("<script>select " + 
				"" + 
				"	 now_subject.subject," + 
				"	 now_subject.currency  ," + 
				"" + 
				"" + 
				"	case" + 
				"			WHEN credit_now IS NULL " + 
				"		OR credit_now = 0 THEN" + 
				"			0 ELSE credit_now " + 
				"		END credit_now," + 
				"" + 
				"" + 
				"	case" + 
				"			WHEN avg_credit_now IS NULL " + 
				"		OR avg_credit_now = 0 THEN" + 
				"			0 ELSE avg_credit_now " + 
				"		END avg_credit_now," + 
				"		" + 
				"		" + 
				"			case" + 
				"			WHEN credit_lastyear IS NULL " + 
				"		OR credit_lastyear = 0 THEN" + 
				"			0 ELSE credit_lastyear " + 
				"		END credit_lastyear," + 
				"		" + 
				"		" + 
				"					case" + 
				"			WHEN avg_credit_lastyear IS NULL " + 
				"		OR avg_credit_lastyear = 0 THEN" + 
				"			0 ELSE avg_credit_lastyear " + 
				"		END avg_credit_lastyear " + 
				"" + 
				"FROM " + 
				"(" + 
				"select  " + 
				"`总帐科目` as subject," + 
				"`货币代码` as currency," + 
				"sum(`期末贷方`) as credit_now," + 
				"sum(`年贷方余额累计数`/`年经过天数`) as avg_credit_now " + 
				"from subject_balance " + 
				"where `平台日期`='${date}'  "
				+ " and "
				+"`支行机构号`!='08308'"
				+ " and `总帐科目`  in "
				+ "<foreach collection='subjects'  item='item' open='(' close=')' separator=','> "
				+ "'${item}' "
				+ "</foreach> " 
				+ "GROUP BY `总帐科目`,`货币代码`" + 
				"ORDER BY `总帐科目`" + 
				") now_subject " + 
				"" + 
				"left join " + 
				"" + 
				"(" + 
				"select  " + 
				"`总帐科目` as subject," + 
				"`货币代码` as currency," + 
				"sum(`期末贷方`) as credit_lastyear," + 
				"sum(`年贷方余额累计数`/`年经过天数`) as avg_credit_lastyear " + 
				"from subject_balance " + 
				"where `平台日期`='20181231' "
				+ " and "
				+"`支行机构号`!='08308' "
				+ " and `总帐科目`in "
				+ "<foreach collection='subjects'  item='item' open='(' close=')' separator=','> "
				+ "'${item}' "
				+ "</foreach> " 
				+"GROUP BY `总帐科目`,`货币代码`" + 
				"ORDER BY `总帐科目`" + 
				") lastyear_subject " + 
				"" + 
				"on now_subject.subject=lastyear_subject.subject and now_subject.currency=lastyear_subject.currency </script>")
	ArrayList<LinkedHashMap<String, Object>> getDepositSubjectsSelf(@Param("date")String date, @Param("subjects")ArrayList<String> subjects);

	@Select("<script>select " + 
	"" + 
	"	 now_subject.subject," + 
	"	 now_subject.currency  ," + 
	"" + 
	"" + 
	"	case" + 
	"			WHEN credit_now IS NULL " + 
	"		OR credit_now = 0 THEN" + 
	"			0 ELSE credit_now " + 
	"		END credit_now," + 
	"" + 
	"" + 
	"	case" + 
	"			WHEN avg_credit_now IS NULL " + 
	"		OR avg_credit_now = 0 THEN" + 
	"			0 ELSE avg_credit_now " + 
	"		END avg_credit_now," + 
	"		" + 
	"		" + 
	"			case" + 
	"			WHEN credit_lastyear IS NULL " + 
	"		OR credit_lastyear = 0 THEN" + 
	"			0 ELSE credit_lastyear " + 
	"		END credit_lastyear," + 
	"		" + 
	"		" + 
	"					case" + 
	"			WHEN avg_credit_lastyear IS NULL " + 
	"		OR avg_credit_lastyear = 0 THEN" + 
	"			0 ELSE avg_credit_lastyear " + 
	"		END avg_credit_lastyear " + 
	"" + 
	"FROM " + 
	"(" + 
			"select  " + 
			"`总帐科目` as subject," + 
			"`货币代码` as currency," + 
			"sum(`期末贷方`) as credit_now," + 
			"sum(`年贷方余额累计数`/`年经过天数`) as avg_credit_now " + 
			"from subject_balance " + 
			"where `平台日期`='${date}'  "
			+ " and "
			+"`支行机构号`='08308' "
			+ " and `总帐科目`  in "
			+ "<foreach collection='subjects'  item='item' open='(' close=')' separator=','> "
			+ "'${item}' "
			+ "</foreach> " 
			+ "GROUP BY `总帐科目`,`货币代码`" + 
			"ORDER BY `总帐科目`" + 
	") now_subject " + 
	"" + 
	"left join " + 
	"" + 
	"(" + 
			"select  " + 
			"`总帐科目` as subject," + 
			"`货币代码` as currency," + 
			"sum(`期末贷方`) as credit_lastyear," + 
			"sum(`年贷方余额累计数`/`年经过天数`) as avg_credit_lastyear " + 
			"from subject_balance " + 
			"where `平台日期`='20181231' "
			+ " and "
			+"`支行机构号`='08308' "
			+ " and `总帐科目`in "
			+ "<foreach collection='subjects'  item='item' open='(' close=')' separator=','> "
			+ "'${item}' "
			+ "</foreach> " 
			+"GROUP BY `总帐科目`,`货币代码`" + 
			"ORDER BY `总帐科目`" + 
	") lastyear_subject " + 
	"" + 
	"on now_subject.subject=lastyear_subject.subject and now_subject.currency=lastyear_subject.currency </script>")
ArrayList<LinkedHashMap<String, Object>> getDepositSubjectsAgent(@Param("date")String date, @Param("subjects")ArrayList<String> subjects);


// getIncomeSubject
@Select(""
+"<script> "
	+"select "
		+"sum(`期末贷方`) as credit_end,"
		+"sum(`期末借方`) as debit_end,"
		+" `总帐科目` as subject ,"
		+"`货币代码` as currency "
	+"from subject_balance "
	+"where "
			+" `总帐科目` in "
			+"<foreach collection='subjects' item='item' open='(' close=')' separator=','>"
				+"${item}"
			+"</foreach>"
		+"and "
			+"`平台日期`=${date} "
		+"and "
			+"`货币代码`='${currency}' "
	+"group by "
		+"`货币代码`,"
		+" `总帐科目`"
+"</script>"
+"")
ArrayList<LinkedHashMap<String, Object>> getIncomeSubject(@Param("date")String date, @Param("subjects")ArrayList<String> subjects,@Param("currency")String currency);

@Select(""
+"<script> "
	+"select "
		+"sum(`期末贷方`) as credit_end,"
		+"sum(`期末借方`) as debit_end,"
		+" `总帐科目` as subject ,"
		+"`货币代码` as currency "
	+"from subject_balance "
	+"where "
			+" `总帐科目`=${subject} "
		+"and "
			+"`平台日期`=${date} "
		+"and "
			+"`货币代码`='${currency}' "
	+"group by "
		+"`货币代码`,"
		+" `总帐科目`"
+"</script>"
+"")
ArrayList<LinkedHashMap<String, Object>> getIncomeSubjectByCurrency(@Param("date")String date, @Param("subject")String subject,@Param("currency")String currency);


}




