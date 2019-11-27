package com.enixlin.jmrc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.mapper.SubjectMapper;
import com.enixlin.jmrc.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
@Autowired SubjectMapper sm;

    public String getLastReportDate() {
        // TODO Auto-generated method stub
        return sm.getLastReportDate();
    }

}