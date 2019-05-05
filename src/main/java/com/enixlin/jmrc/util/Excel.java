package com.enixlin.jmrc.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import flex.messaging.io.ArrayList;

public class Excel {

	private java.util.ArrayList<HashMap<String, Object>> resultArrayList;

	public java.util.ArrayList<HashMap<String, String>> toArrayList(String path,String sheetName) {
		
	
	File xlsFile = new File(path);
	// 获得工作簿
	Workbook workbook = null;
	try {
		workbook = WorkbookFactory.create(xlsFile);
	} catch (EncryptedDocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	// 遍历工作表

		Sheet sheet =workbook.getSheet(sheetName);
		// 获得行数
		int rows = sheet.getLastRowNum() + 1;
		// 获得列数，先获得一行，在得到改行列数
		Row tmp = sheet.getRow(0);
		if (tmp == null) {
			return null;
		}
		int cols = tmp.getPhysicalNumberOfCells();
		// 读取数据
		java.util.ArrayList<String> colsName=new java.util.ArrayList<>();
		java.util.ArrayList<HashMap<String, Object>> records=new java.util.ArrayList<>();
		
		for (int row = 0; row < rows; row++) {
			Row r = sheet.getRow(row);
//			取得第一行的值作为　key
			if(row==0) {
				for(int col = 0; col < cols; col++) {
					colsName.add(r.getCell(col).getStringCellValue());
				}
			}else {
				HashMap<String, String> items=new HashMap<>();
				for (int col = 0; col < cols; col++) {
					
					items.put(colsName.get(col).toString(), r.getCell(col).getStringCellValue());
					System.out.printf("%10s", r.getCell(col).getCellType());
				}
				//records.add(items);
			}
			
			System.out.println();
		}
	
	return null;
	}
}
