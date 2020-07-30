package com.enixlin.jmrc.entity;


//CREATE TABLE `roler` (
//	  `id` int(10) NOT NULL AUTO_INCREMENT,
//	  `name` varchar(200) NOT NULL,
//	  PRIMARY KEY (`id`,`name`),
//	  UNIQUE KEY `id` (`id`,`name`)
//	) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 |


public class Roler {
    private int id;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    

}
