package com.enixlin.jmrc.service.impl;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.User;
import com.enixlin.jmrc.mapper.UserMapper;
import com.enixlin.jmrc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author enixl
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMaper;

    @Override
    public List<Map<String, Object>> getUserNameAndId() {
        // 查找所有的用户,返回用户名称和用户编号
   
        return null;

    }

    @Override
    public List<Map<String, Object>> getUserAllInformation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override

    public User getUserById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return userMaper.getUserByName(name);
    }

    @Override
    public User addUser(User user) {
        if (!isExist(user)) {
            if (userMaper.addUser(user) == 1) {
                return this.getUserByName(user.getName());
            } else {
                return user ;
            }
        } else {
            user.setExist(true);
            return user;
        }

    }


    @Override
    public boolean isExist(User user) {
        ArrayList<User> userArrayList = userMaper.getAllUserInformation();
        boolean exist = false;
        for (User r : userArrayList) {
            if (r.getName().equals(user.getName())) {
                exist = true;
            }
        }
        return exist;
    }

    @Override
    public User modifyUser(User user) {
        int result = userMaper.updateUser(user);
        if (result > 0) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User deleteUser(int userId) {

        return null;
    }


    /**
     * 取得所有的用户名称和编号
     */
    @Override
    public ArrayList<User> getAllUserNameAndId() {
        return userMaper.getAllUserNameAndId();
    }

    // 取得所有的用户的所有信息
    @Override
    public ArrayList<User> getAllUserInformation() {
        return userMaper.getAllUserInformation();
    }

    @Override
    public ArrayList<Roler> getRolerByUser(User user) {
        // TODO Auto-generated method stub

        return userMaper.getRolerByUser(user);
    }

    @Override
    public int addRolerToUser(User user, Roler roler) {
        // TODO Auto-generated method stub
        ArrayList<Roler> rolerList = userMaper.getRolerByUser(user);
        boolean addflag = false;
        for (int i = 0; i < rolerList.size(); i++) {
            if (roler.getId() == rolerList.get(i).getId()) {
                addflag = true;
            }
        }
        return addflag ? userMaper.addRolerToUser(user, roler) : 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enixlin.jmrc.service.UserService#addRolersToUser(com.enixlin.jmrc.entity.
     * User, java.util.ArrayList)
     */
    @Override
    public ArrayList<Roler> addRolersToUser(User user, ArrayList<Roler> rolerList) {
        //System.out.println(rolerList);
        // 第一步,取得用户的当前拥有的角色列表
        ArrayList<Roler> arrUserRolerList = userMaper.getRolerByUser(user);

        boolean addflag = true;
        boolean removeflag = true;
        for (int i = 0; i < rolerList.size(); i++) {
            addflag = true;
            System.out.println(rolerList.get(i).getId());
            for (int j = 0; j < arrUserRolerList.size(); j++) {
                if (rolerList.get(i).getId() == arrUserRolerList.get(j).getId()) {
                    addflag = false;
                }
            }
            if (addflag) {
                System.out.println("add rolerList.get(i)" + rolerList.get(i));
                userMaper.addRolerToUser(user, rolerList.get(i));
            }
        }
        for (int j = 0; j < arrUserRolerList.size(); j++) {
            removeflag = true;
            for (int i = 0; i < rolerList.size(); i++) {
                if (rolerList.get(i).getId() == arrUserRolerList.get(j).getId()) {
                    removeflag = false;
                }
            }
            if (removeflag) {
                userMaper.removeRolersFromUser(user, arrUserRolerList.get(j).getId());
            }
        }
        // 第三步,遍用用户角色列表和添加的角色列表,
        // 外层先历遍参数rolerList，和用户当前的角色列表作比较
        // 如果有当前角色列表不存在角色,就添加，否则就
        return userMaper.getRolerByUser(user);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enixlin.jmrc.service.UserService#removeRolersFromUser(com.enixlin.jmrc.
     * entity.User, int)
     */
    @Override
    public int
    removeRolersFromUser(User user, int rolerID) {
        return userMaper.removeRolersFromUser(user, rolerID);
    }

}
