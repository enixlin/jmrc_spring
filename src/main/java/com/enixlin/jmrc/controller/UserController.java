package com.enixlin.jmrc.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.User;
import com.enixlin.jmrc.service.UserService;

/**
 * <h1>用户实体控制器</h1><br>
 * 这个控制器提供了用户的增删改查待功能<br>
 * 还提供了根据用户编号查用户所有角色的功能
 * 
 * @author enixl
 *
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/getUsers")
    public ArrayList<User> getUsers() {

		return userService.getAllUserNameAndId();
    }

    /**
     * 
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("/addUser")
    public com.enixlin.jmrc.entity.User addUser(HttpServletRequest req, HttpServletResponse res) {
	com.enixlin.jmrc.entity.User user = new com.enixlin.jmrc.entity.User();
	user.setName(req.getParameter("name"));
	user.setPassword(req.getParameter("password"));
	user.setStatus(Integer.parseInt(req.getParameter("status")));
	com.enixlin.jmrc.entity.User u = userService.addUser(user);
		System.out.println("user");
		return u;
    }

    @RequestMapping("/modifyUser")
    public com.enixlin.jmrc.entity.User modifyUser(HttpServletRequest req, HttpServletResponse res) {
	com.enixlin.jmrc.entity.User user = new com.enixlin.jmrc.entity.User();
	user.setName(req.getParameter("name"));
	user.setPassword(req.getParameter("password"));
	user.setId(Integer.parseInt(req.getParameter("id")));
	user.setStatus(Integer.parseInt(req.getParameter("status")));
	com.enixlin.jmrc.entity.User u = userService.modifyUser(user);
	return u;
    }

    /**
     * 取得所有的用户名称和编号<br>
     * 该方法只要是用来获取用户编号和用户名称供登录窗口用
     * 
     * @return ArrayList 返回用户对象数组
     */
    @RequestMapping("/getAllUserNameAndId")
    public ArrayList<User> getAllUserNameAndId() {
	return userService.getAllUserNameAndId();
    }

    // 取得所有的用户的所有信息
    @RequestMapping("/getAllUserInformation")
    public ArrayList<User> getAllUserInformation(HttpServletRequest req, HttpServletResponse res) {
	return userService.getAllUserInformation();
    }

    // 取得所有的用户的所有信息
    @RequestMapping("/getRolerByUser")
    public ArrayList<Roler> getRolerByUser(HttpServletRequest req, HttpServletResponse res) {
	User user = new User();
	user.setId(Integer.parseInt(req.getParameter("user_id")));

	return userService.getRolerByUser(user);
    }

    /**
     * 添加角色到指定的用户
     * 
     * @param req
     *            请求体参数,就是客户端传递过来的用户编号,需要使用Integer.parseInt(req.getParameter("user_id"))获取
     * @param res
     *            没有使响应头和主体
     * @return int 返回一个整数结果,1为成功,没有添加成功返回0
     */
    @RequestMapping("/addRolerToUser")
    public int addRolerToUser(HttpServletRequest req, HttpServletResponse res) {
	User user = new User();
	user.setId(Integer.parseInt(req.getParameter("user_id")));
	Roler roler = new Roler();
	roler.setId(Integer.parseInt(req.getParameter("roler_id")));
	return userService.addRolerToUser(user, roler);
    }

    /**
     * 将一组角色添加到指定用户上
     * 
     * @param req
     * @param res
     * @return 返回的是一个int整形的数据
     */
    @RequestMapping("/addRolersToUser")
    public ArrayList<Roler>  addRolersToUser(HttpServletRequest req, HttpServletResponse res) {
	Map<String, String[]> params=req.getParameterMap();
	User user;
	ArrayList<Roler> rolerList = new ArrayList<>();
	user = new User();
	user.setId(Integer.parseInt(req.getParameter("userID")));
	
	String[] list=params.get("rolers");
	for (int i = 0; i < list.length; i++) {
	    Roler temp=new Roler();
	    temp.setId(Integer.parseInt(list[i]));
	    rolerList.add(temp);
	}
	  return  userService.addRolersToUser(user, rolerList);

    }
    
    
    @RequestMapping("/checkName")
    public boolean checkName(HttpServletRequest req) {
	boolean flag=true;
	String userName=req.getParameter("name");
	ArrayList<User> userList=userService.getAllUserNameAndId();
	for (User user : userList) {
	    if(userName.equals(user.getName())) {
		flag=false;
	    }
	}
	return flag;
    }
    
    
    

}
