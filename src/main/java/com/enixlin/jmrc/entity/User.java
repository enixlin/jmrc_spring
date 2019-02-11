package com.enixlin.jmrc.entity;

public class User {

    private String name;
    private String password;
    private int id;
    private int status;
    private int rolerId;
    private String rolerName;
    public String getRolerName() {
        return rolerName;
    }
    public void setRolerName(String rolerName) {
        this.rolerName = rolerName;
    }
    public int getRolerId() {
        return rolerId;
    }
    public void setRolerId(int rolerId) {
        this.rolerId = rolerId;
    }
    
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getName() {
        return name;
    }
    public User() {
	super();
	// TODO Auto-generated constructor stub
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
   
    
}
