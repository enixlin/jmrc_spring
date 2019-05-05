package com.enixlin.jmrc.service;

import com.enixlin.jmrc.entity.Rule;

public interface RuleService {

    public Rule addRule(Rule rule);

    public Rule saveRule(Rule rule);

    public boolean removeRule(Rule rule);

    public Rule getRule(Rule rule);

    public boolean isExist(Rule rule);
}
