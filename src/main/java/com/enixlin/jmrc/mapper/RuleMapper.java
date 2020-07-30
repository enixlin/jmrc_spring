package com.enixlin.jmrc.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.enixlin.jmrc.entity.Rule;

@Mapper
public interface RuleMapper {
    

    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    @Insert("insert into rule (name,url,panel,leaf,icon) value(#{name},#{url}#{panel},#{leaf},#{icon})")
    public int addRule(Rule rule);
    
    @Select("select * from rule ")
    public ArrayList<Rule> getAllRules();
    
    @Select("select * from rule where id=#{id} ")
    public Rule getRuleById(Rule rule);
    
    @Update("update rule set name=#{name},url=#{url},leaf=#{leaf},panel=#{panel},icon=#{icon} where id=#{id}")
    public int  updateRule(Rule rule);
    
    @Delete("delete from rule where id=#{id}")
    public int deleteRule(Rule rule);
  
   
}
