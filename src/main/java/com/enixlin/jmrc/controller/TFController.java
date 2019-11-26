

/**
 * 贸易融资业务控制器

 */
package com.enixlin.jmrc.controller;

import com.enixlin.jmrc.service.TFService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linzhenhuan
 *
 */
@RestController()
@EnableScheduling
@RequestMapping("TF")
public class TFController {
    @Autowired TFService tfs;
    
    public int getRecordCount(){
        return tfs.getRecordCount();
    }
}
