package com.enixlin.jmrc.service;

import com.enixlin.jmrc.entity.Rule;
import com.enixlin.jmrc.mapper.RuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    RuleMapper ruleMapper;

    @Override
    public Rule addRule(Rule rule) {
        //在添加功能之前，要检测一下功能是否已存在
        if (!this.isExist(rule)) {
            if (ruleMapper.addRule(rule) == 1) {
                return this.getRule(rule);
            } else {
                return null;
            }
        } else {
            return null;
        }

    }


    @Override
    public Rule saveRule(Rule rule) {
        return null;
    }

    @Override
    public boolean removeRule(Rule rule) {
        if (ruleMapper.deleteRule(rule) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Rule getRule(Rule rule) {
        return ruleMapper.getRuleById(rule);
    }

    @Override
    public boolean isExist(Rule rule) {
        ArrayList<Rule> ruleArrayList = ruleMapper.getAllRules();
        boolean exist = false;
        for (Rule r : ruleArrayList) {
            if (r.getName().equals(rule.getName())) {
                exist = true;
            }
        }
        return exist;
    }
}
