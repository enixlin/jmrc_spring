package com.enixlin.jmrc.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.enixlin.jmrc.entity.OATool;
import com.enixlin.jmrc.entity.Roler;

@Mapper
public interface OAToolMapper {

	// ��ҵ����ȡ�ù���
	@Select("select * from OATool where id=#{id}")
	public OATool getOAToolById(int id);

	@Select("select * from OATool")
	public ArrayList<OATool> getOAToolList();

	// ����С����
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert("insert into OATool (name,description,panel,icon) value(#{name},#{description},#{panel},#{icon})")
	public boolean addOATool(OATool oATool);

	// ����С����
	@Update("update OATool set name=#{name},description=#{description},panel=#{panel},icon=#{icon} where id=#{id}")
	public boolean updateOATool(int id, OATool oATool);
	
	//ɾ��С����
	@Delete("delete from OATool where id=#{id}")
	public boolean deleteOATool(int id);
	
	

}
