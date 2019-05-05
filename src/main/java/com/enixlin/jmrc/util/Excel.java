package com.enixlin.jmrc.util;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import flex.messaging.io.ArrayList;

public class Excel {
	
	
	public void read(String path) {
		
	
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
	// 获得工作表个数
	int sheetCount = workbook.getNumberOfSheets();
	// 遍历工作表
	for(
	int i = 0;i<sheetCount;i++)
	{
		Sheet sheet = workbook.getSheetAt(i);
		// 获得行数
		int rows = sheet.getLastRowNum() + 1;
		// 获得列数，先获得一行，在得到改行列数
		Row tmp = sheet.getRow(0);
		if (tmp == null) {
			continue;
		}
		int cols = tmp.getPhysicalNumberOfCells();
		// 读取数据
		for (int row = 0; row < rows; row++) {
			Row r = sheet.getRow(row);
			for (int col = 0; col < cols; col++) {
					System.out.printf("%10s", r.getCell(col).getStringCellValue());
				}
			System.out.println();
		}
	}
	}
}
