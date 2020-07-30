package com.enixlin.jmrc.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.Rule;

@Mapper
public interface RolerMapper {
    
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")// keyProperty user 实体属性,keyColumn user表字段
    @Insert("insert into roler (name) value(#{name})")
    public Roler addRoler(Roler roler);
    
    @Update("update roler set name=#{name} where id=#{id}")
    public Roler updateRoler(Roler roler);
    
    @Delete("delete from roler where id=#{id}")
    public int deleteRoler(Roler roler);
    
    @Select("select * from roler where id=#{id}")
    public Roler getRolerById(Roler roler);
    
    @Select("select * from roler")
    public ArrayList<Roler> getAllRolers();
    
    @Select("select rule.* from roler "
    	+ "left join roler_rule on roler.id=roler_rule.roler_id "
    	+ "left join rule on roler_rule.rule_id=rule.id "
    	+ "where roler.id=#{id}"
    	+ "")
    public ArrayList<Rule> getAllRuleByRoler(Roler roler);
    
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into roler_rule (roler_id,rule_id) value(#{roler.id},#{rule.id})")
    public int addRolerRule(@Param("roler")Roler roler ,@Param("rule")Rule rule);
    
    @Delete("delete from roler_rule where roler_id=#{id} and rule_id=#{id}")
    public int deleteRolerRule(@Param("roler")Roler roler,@Param("rule")Rule rule);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param userId
	 * @return
	 *ArrayList<Roler>
	 * 创建时间：2019年10月17日
	 */
    @Select("select * from user_roler left join roler on user_roler.roler_id=roler.id  where user_roler.user_id=#{userId}")
	public ArrayList<Roler> getRolerByUserId(@Param("userId")int userId);
    
}
