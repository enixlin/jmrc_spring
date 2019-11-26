package com.enixlin.jmrc.service.impl;

import com.enixlin.jmrc.mapper.SubjectMapper;
import com.enixlin.jmrc.service.SubjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {
@Autowired SubjectMapper sm;

    @Override
    public String getLastReportDate() {
        // TODO Auto-generated method stub
        return sm.getLastReportDate();
    }

}