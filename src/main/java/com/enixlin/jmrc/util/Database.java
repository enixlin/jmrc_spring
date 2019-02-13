package com.enixlin.jmrc.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mysql.jdbc.Connection;

@Component
public class Database {
    private java.sql.Connection conn;

    public Connection setConnection() throws SQLException {
	// 注册jdbc驱动
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	// 建立连接
	String url = "jdbc:mysql://localhost:3306/jmrc";
	String user = "linzhenuan";
	String password = "enixlin1981";
	Connection conn = (Connection) DriverManager.getConnection(url, user, password);
	return conn;
    }

    public Object query(String sql, ArrayList<String> list) throws SQLException {
	if(this.conn!=null) {
	    // 创建语句
//	    java.sql.Statement st=this.conn.createStatement();
	}
	return list;

    }

}
