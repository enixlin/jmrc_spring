package com.enixlin.jmrc.mapper;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper  extends Mapper<User> {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    // keyProperty user 实体属性,keyColumn user表字段
    @Insert("insert into user (name,password,status) value(#{name},#{password},#{status})")
    public int addUser(User user);

    // 更新用户信息
    @Update("update user set name=#{name},password=#{password},status=#{status} where id=#{id}")
    public int updateUser(User user);

    // 取得所有的用户名称和编号
    @Select("select id,name from user")
    public ArrayList<User> getAllUserNameAndId();

    // 取得所有的用户的所有信息
    @Select("select * from user")
    public ArrayList<User> getAllUserInformation();

    // 根据用户编号取得该用户的所有角色
    @Select("select roler.id ,roler.name  from user_roler " + "left join " + " roler on  "
	    + " user_roler.roler_id=roler.id "
	    + "where user_roler.user_id=#{id} ")
    public ArrayList<Roler> getRolerByUser(User user);


    @Select ("select * from  user where name=#{name}")
    public User getUserByName(String name);

    // 添加角色到用户
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into user_roler (user_id,roler_id) value(#{user.id},#{roler.id})")
    public int addRolerToUser(@Param("user")User user, @Param("roler")Roler roler);
    
    //去掉指定用户的某些角色
    @Delete("delete from user_roler where user_id=#{user.id} and roler_id=#{rolerId}")
    public int removeRolersFromUser(@Param("user")User user, @Param("rolerId")int rolerID);
}
