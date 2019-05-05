package com.enixlin.jmrc.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.enixlin.jmrc.entity.Feature;

@Mapper
public interface FeatureMapper {
	// 取得所有的用户名称和编号
    @Select("select * from feature order by id asc")
    public ArrayList<Feature> getAllFeature(); 
}
