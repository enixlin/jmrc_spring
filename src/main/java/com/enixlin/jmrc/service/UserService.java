package com.enixlin.jmrc.service;

import java.util.ArrayList;
import java.util.Map;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.User;

public interface UserService {

    public java.util.List<Map<String, Object>> getUserNameAndId();

    public java.util.List<Map<String, Object>> getUserAllInformation();


    public User getUserById(int id);
    public User getUserByName(String name);

    public User modifyUser(User user);

    public User deleteUser(int userId);

    public User addUser(User user);
    public  boolean isExist(User user);
    
    /**
     * 将一组角色赋给一个用户
     * @param user 用户
     * @param rolerList 角色数组
     * @return ArrayList-Roler 更新后用户的角色列表
     */
    public ArrayList<Roler> addRolersToUser(User user,ArrayList<Roler> rolerList);

    /**
     * 取得所有的用户名称和编号
     * @return ArrayList 返回用户对象数组
     */
    public ArrayList<User> getAllUserNameAndId();

    // 取得所有的用户的所有信息
    public ArrayList<User> getAllUserInformation();

    // get the roler list by user
    public ArrayList<Roler> getRolerByUser(User user);

    // 添加角色到用户
    public int addRolerToUser(User user, Roler roler);
    
    /**
     * 去掉指定用户的某些角色
     * @param user 指定用户编号
     * @param rolerID  角色编号
     * @return 更新后用户角色列表
     */
    public int removeRolersFromUser(User user,int rolerID); 

}
