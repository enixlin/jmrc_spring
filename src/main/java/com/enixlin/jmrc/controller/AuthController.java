package com.enixlin.jmrc.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.Rule;
import com.enixlin.jmrc.entity.User;
import com.enixlin.jmrc.service.AuthService;
import com.enixlin.jmrc.service.RolerService;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private RolerService rolerService;



    @RequestMapping("/authLogin")
    public User authLogin(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
        User u = new User();
        u.setId(Integer.parseInt(req.getParameter("id")));
        u.setPassword(req.getParameter("password"));
        User authUser = authService.authLogin(u, session);
        //System.out.println(authUser.getName());
        return authUser;
    }

    @RequestMapping("/getRuleByRoler")
    public ArrayList<Rule>  getRuleByRoler(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
        Roler roler = new Roler();
        roler.setId(Integer.parseInt(req.getParameter("id")));
        roler.setName(req.getParameter("rolerName"));
        //将选择的角色写入服务器的session中
        session.setAttribute("roler_id", roler.getId());
        session.setAttribute("roler_name", roler.getName());
        ArrayList<Rule> ruleList = rolerService.getAllRuleByRoler(roler);
        return ruleList;
    }

    /**
     * 检测用户是否已登录<br>
     * 如果session中有用户编号,用户名称,用户选择的角色的话 就返回一个用户对象实体,否则就返回一个空的用户对象
     *
     * @param session
     * @return User 用户对象实体
     */
    @RequestMapping("/checkLogin")
    public User checkLogin(HttpSession session) {
//        if (session.getAttribute("id") != null && session.getAttribute("roler_id") != null) {
        if (session.getAttribute("id") != null ) {
            User user = new User();
            user.setId((int) session.getAttribute("id"));
            user.setName((String) session.getAttribute("name"));
            //user.setRolerId((int) session.getAttribute("roler_id"));
            //user.setRolerName((String) session.getAttribute("roler_name"));
            return authService.checkLogin(session) ? user : null;
        } else {
            System.out.println("return user null");
            return new User();
        }

    }

    /**
     * 退出登录
     *
     * @param session
     */
    @RequestMapping("/doLogout")
    public void doLogout(HttpSession session) {
        authService.doLogout(session);
        
    }
}
