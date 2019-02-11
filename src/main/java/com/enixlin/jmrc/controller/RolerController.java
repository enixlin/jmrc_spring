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
import com.enixlin.jmrc.mapper.RolerMapper;
import com.enixlin.jmrc.service.RolerService;

@RestController
@RequestMapping("/roler")
public class RolerController {

    @Autowired
    private RolerService rolerService;

    @RequestMapping("/getAllRolers")
    public ArrayList<Roler> getAllRolers() {
	return rolerService.getAllRolers();
    }

    @RequestMapping("/addRoler")
    public Roler addRoler(HttpServletRequest req, HttpServletResponse res) {
	Roler roler = new Roler();
	roler.setId(Integer.parseInt(req.getParameter("id")));
	roler.setName(req.getParameter("name"));
	return rolerService.addRoler(roler);

    }

    @RequestMapping("/updateRoler")
    public Roler updateRoler(HttpServletRequest req, HttpServletResponse res) {
	Roler roler = new Roler();
	roler.setId(Integer.parseInt(req.getParameter("id")));
	roler.setName(req.getParameter("name"));
	return rolerService.updateRoler(roler);

    }

    @RequestMapping("/deleteRoler")
    public int deleteRoler(HttpServletRequest req, HttpServletResponse res) {
	Roler roler = new Roler();
	roler.setId(Integer.parseInt(req.getParameter("id")));
	roler.setName(req.getParameter("name"));
	return rolerService.deleteRoler(roler);
    }

    @RequestMapping("/getRolerById")
    public Roler getRolerById(HttpServletRequest req, HttpServletResponse res) {
	Roler roler = new Roler();
	roler.setId(Integer.parseInt(req.getParameter("id")));
	return rolerService.getRolerById(roler);
    }

    @RequestMapping("/getAllRuleByRoler")
    public ArrayList<Rule> getAllRuleByRoler(HttpServletRequest req, HttpServletResponse res,HttpSession session) {
	Roler roler = new Roler();
	roler.setId(Integer.parseInt(req.getParameter("id")));
	session.setAttribute("roler", roler.getId());
	return rolerService.getAllRuleByRoler(roler);
    }

    @RequestMapping("/addRolerRule")
    public int addRolerRule(HttpServletRequest req, HttpServletResponse res) {
	Roler roler=new Roler();
	Rule rule=new Rule();
	roler.setId(Integer.parseInt(req.getParameter("roler_id")));
	rule.setId(Integer.parseInt(req.getParameter("rule_id")));
	return rolerService.addRolerRule(roler, rule) ;

    }

    @RequestMapping("/deleteRolerRule")
    public int deleteRolerRule(HttpServletRequest req, HttpServletResponse res) {
	Roler roler=new Roler();
	Rule rule=new Rule();
	roler.setId(Integer.parseInt(req.getParameter("roler_id")));
	rule.setId(Integer.parseInt(req.getParameter("rule_id")));
	return rolerService.deleteRolerRule(roler, rule) ;
    }
}
