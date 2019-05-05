package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.Rule;
import com.enixlin.jmrc.entity.User;
import com.enixlin.jmrc.mapper.RolerMapper;
import com.enixlin.jmrc.mapper.UserMapper;
import com.enixlin.jmrc.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RolerMapper rolerMapper;

    //
    @Override
    public User authLogin(User u,HttpSession session) {
	ArrayList<User> userList=userMapper.getAllUserInformation();
	User authUser=new User();
	for (int i = 0; i < userList.size(); i++) {
	    authUser=userList.get(i);
	    if(authUser.getId()==u.getId() && authUser.getPassword().equals(u.getPassword())) {
		this.setSession(authUser, session);
		return authUser;
	    }
	}
	return null;
    }


    @Override
    public ArrayList<Rule> getRuleByRoler(Roler roler) {
	ArrayList<Rule> rulerList= rolerMapper.getAllRuleByRoler(roler);
	return rulerList; 
    }

    @Override
    public boolean checkLogin(HttpSession session) {
	if(session.getAttribute("logined")!=null && (boolean)session.getAttribute("logined")==true) {
		User u=new User();
		u.setName((String) session.getAttribute("name"));
		u.setId((int) session.getAttribute("id"));
	    return true;
	}
	return false;
    }

    @Override
    public User setSession(User user,HttpSession session) {
	// TODO Auto-generated method stub
	session.setAttribute("name", user.getName());
	session.setAttribute("id", user.getId());
	session.setAttribute("logined", true);
	return user;
    }

    @Override
    public void doLogout(HttpSession session) {
	// TODO Auto-generated method stub
	System.out.println("doLogout is run............");
	session.invalidate();
	//session=null;
//	
    }
    
    





}
