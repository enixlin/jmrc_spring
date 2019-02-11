package com.enixlin.jmrc.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.Rule;
import com.enixlin.jmrc.entity.User;

public interface AuthService {

   

    public User authLogin(User u, HttpSession session);

    public ArrayList<Rule> getRuleByRoler(Roler roler);

    public boolean checkLogin(HttpSession session);

    public User setSession(User user, HttpSession session);

    public void doLogout(HttpSession session);

}
