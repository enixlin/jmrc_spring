package com.enixlin.jmrc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.policydocument.DataFileAccess;

//import smartbi.net.sf.json.JSONArray;
//import smartbi.sdk.ClientConnector;
//import smartbi.sdk.service.businessview.BusinessViewService;

/**
 * className LoginController 这是整个应用户的唯一入口,所有控制器中<br>
 * 只有这个控制可以输出一个前端的视图
 * 
=======
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.service.AuthService;



/**
 * className LoginController
 * 这是整个应用户的唯一入口,所有控制器中<br>
 * 只有这个控制可以输出一个前端的视图

 * @version 1.0
 * @author enixl
 * 
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {




	/**
	 * 这个方法输出一个html视图到客户端,这个视图就是用户登录界面
	 * 
	 * @param hsr
	 *            这是一个response
	 */
	@RequestMapping("/index")
	public void showLogin(HttpServletResponse hsr) {

		/*
		 * 以下的内容是输出一个html视图到客户端,这个视图就是用户登录界面
		 */
		String location = "http://localhost:8080/jmrc/index.html";
		try {
			hsr.sendRedirect(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/update")
	public void update() {
		System.out.println("开始下载升级包 :............................................................");
		String path = "http://www.chinaforex.com.cn/download/lrprc/201901.law";

		try {

			/**
			 * 获取外部文件流
			 */
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			InputStream inputStream = conn.getInputStream();
			System.out.println("获得升级包的大小 :............................................................");
			int fileLenght = conn.getContentLength();

			System.out.println(fileLenght);

			/**
			 * 输出文件到服务文件
			 */
			int len = 0;

			String fileName = "MYpackage";
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream out = new FileOutputStream(file);

			byte[] buffer = new byte[1024];

			int size = 0;

			while ((size = inputStream.read(buffer)) != -1) {
				len += size;
				out.write(buffer, 0, size);
				// 打印下载百分比fileLenght254 MB (267,104,256 字节)
				System.out.println("下载了-------> " + len * 100 / fileLenght + "%\n");
			}

			if (file.length() >= fileLenght) {
				System.out.println("文件下载完成:............................................................");
			}
			out.close();

			// DataFileAccess dfa = new DataFileAccess();
			// dfa.fileToObject(fileName);
			//
			// dfa.saveObjectToDB("localhost", "3306", "jmrc", "linzhenhuan",
			// "enixlin1981");
			// // dfa.createDBTable("localhost", "3306", "jmrc", "linzhenhuan",
			// "enixlin1981");
			//
			// System.out.println("升级完成");
			//

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/update1")
	public void update1() {
		String fileName = "MYpackage";
		DataFileAccess dfa = new DataFileAccess();
		dfa.fileToObject(fileName);

		dfa.saveObjectToDB("localhost", "3306", "jmrc", "linzhenhuan", "enixlin1981");
		// dfa.createDBTable("localhost", "3306", "jmrc", "linzhenhuan", "enixlin1981");

		System.out.println("升级完成");

	}

	
	/*
	@RequestMapping("/test")
	public void test() {

		String smartbiUrl = "http://110.0.170.88:9083/smartbi";
		ClientConnector clientConnector = new ClientConnector(smartbiUrl);
		String user = "32311";
		String password = "123";
		//
		// // String user="rpt";
		// // String password="";
		boolean flag = clientConnector.open(user, password);
		//

		if (flag) {
			BusinessViewService bvService = new BusinessViewService(clientConnector);
			String datasourceId = "DS.新会特色报表";
			JSONArray ja = bvService.openBusinessView("Iee801fbd227e43eb01583d989ca32e84");
			System.out.println("ja ");

		}

		System.out.println("connect...");
		try {
			File file = new File("/dbTable.txt");
			FileOutputStream fos = new FileOutputStream(file);
			file.createNewFile();
			// List tables= result.getData();
			// for (int i = 0; i < tables.size(); i++) {
			// tables.get(i).
			// }
			// fos.write(b);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// SDKGridData result2 = dss.executeNoCacheable(dataSourceID, sql2);
		// // SDKGridData result= dss.getSampleTableData("T121576", maxRows);
		// //result=null;
		clientConnector.close();
		System.out.println("connect...");
	}
*/

}
