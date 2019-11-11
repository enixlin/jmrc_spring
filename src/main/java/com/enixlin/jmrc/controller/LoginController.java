package com.enixlin.jmrc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import smartbi.net.sf.json.JSONArray;
//import smartbi.sdk.ClientConnector;
//import smartbi.sdk.ClientConnec
//import smartbi.sdk.service.businessview.BusinessViewService;
//import smartbi.sdk.service.businessview.BusinessViewService;
//import smartbi.sdk.service.businessview.BusinessViewService;

/**
 * className LoginController 这是整个应用户的唯一入口,所有控制器中<br>
 * 只有这个控制可以输出一个前端的视图
 * <p>
 * ======= import java.io.IOException;
 * <p>
 * import javax.servlet.http.HttpServletResponse;
 * <p>
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * <p>
 * import com.enixlin.jmrc.service.AuthService;
 * <p>
 * <p>
 * <p>
 * /** className LoginController 这是整个应用户的唯一入口,所有控制器中<br>
 * 只有这个控制可以输出一个前端的视图
 *
 * @author enixl
 * @version 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

	/**
	 * 这个方法输出一个html视图到客户端,这个视图就是用户登录界面
	 *
	 * @param hsr 这是一个response
	 */
	@RequestMapping("/index")
	public void showLogin(HttpServletResponse hsr) {

//    	Excel excel=new Excel();
//    	String path="d:/yq.xls";
//    	String sheetName="sheet1";
//		excel.toArrayList(path,sheetName);

		/*
		 * 以下的内容是输出一个html视图到客户端,这个视图就是用户登录界面
		 */
//        String location = "http://110.0.6.100:8888/jmrc/index.html";
//		String location = "/jmrc/build/production/jmrc/index.html";
 String location = "/jmrc/index.html";
		try {
			hsr.sendRedirect(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //
		}

	}

	@RequestMapping("/showint")
	public int showint(HttpServletRequest req, HttpServletResponse res) {
		return 1;
	}

	@RequestMapping("/update")
	public void update() {
		System.out.println(
				"开始下载升级包 :............................................................");
		String path = "http://www.chinaforex.com.cn/download/lrprc/201901.law";

		try {

			/**
			 * 获取外部文件流
			 */

			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			InputStream inputStream = conn.getInputStream();
			System.out.println(
					"获得升级包的大小 :............................................................");
			int fileLenght = conn.getContentLength();

			System.out.println(fileLenght);

			/*
			 * 输出文件到服务器
			 *
			 *
			 */

			/**
			 * 输出文件到服务文件
			 */
			int len = 0;
			int so = 34;

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
				System.out.println(
						"下载了-------> " + len * 100 / fileLenght + "%\n");
			}

			if (file.length() >= fileLenght) {
				System.out.println(
						"文件下载完成:............................................................");
			}
			out.close();
			String str = "";

			if (1 == 1) {
				System.out.println();

			}

			if (2 == 2) {

			}

			/*
			 * 
			 * ctrl + y 删除一整行 ctrl + d 复制一行粘贴到下一行去
			 */
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**/

	/*
	 * @RequestMapping("/tests") public void testss() {
	 * 
	 * String smartbiUrl = "http://110.0.170.88:9083/smartbi"; ClientConnector
	 * clientConnector = new ClientConnector(smartbiUrl); String user = "32311";
	 * String password = "123"; // // // String user="rpt"; // // String
	 * password=""; boolean flag = clientConnector.open(user, password); //
	 * 
	 * if (flag) { BusinessViewService bvService = new
	 * BusinessViewService(clientConnector); String datasourceId = "DS.新会特色报表";
	 * JSONArray ja =
	 * bvService.openBusinessView("Iee801fbd227e43eb01583d989ca32e84");
	 * System.out.println("ja ");
	 * 
	 * }
	 * 
	 * System.out.println("connect..."); try { File file = new
	 * File("/dbTable.txt"); FileOutputStream fos = new FileOutputStream(file);
	 * file.createNewFile(); // List tables= result.getData(); // for (int i =
	 * 0; i < tables.size(); i++) { // tables.get(i). // } // fos.write(b);
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } // SDKGridData result2 =
	 * dss.executeNoCacheable(dataSourceID, sql2); // // SDKGridData result=
	 * dss.getSampleTableData("T121576", maxRows); // //result=null;
	 * clientConnector.close(); System.out.println("connect..."); }
	 */

}
