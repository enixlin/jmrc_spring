/**
 * 
 */
package com.enixlin.jmrc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author linzhenhuan
 *
 */
public class ExcelTool {

	/**
	 * 将数据写入excel文件中
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param allUnitPerformance
	 * @param excelPath void 创建时间：2019年8月29日
	 */
	public void exportToexcel(ArrayList<LinkedHashMap<String, Object>> allUnitPerformance,
			String excelPath, String start, String end, String unit,
			String title) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelPath);
		int recordColumns = allUnitPerformance.get(0).size();
		if (recordColumns <= 1) {
			recordColumns = 1;
		}
		if (allUnitPerformance.size() == 0) {
			return;
		}
		int nRowNum = 0;

		// 这里要实现一个表格标题
		HSSFRow rowTitle = sheet.createRow(nRowNum);
		CellRangeAddress region1 = new CellRangeAddress(nRowNum, (short) nRowNum,0,
				(short) recordColumns - 1);
		sheet.addMergedRegion(region1);
		HSSFCell cellTitle = rowTitle.createCell(0);
		CellStyle cs = wb.createCellStyle();
		// 水平居中对齐
		cs.setAlignment(HorizontalAlignment.CENTER);
		cellTitle.setCellStyle(cs);
		cellTitle.setCellValue(title);
		nRowNum++;

		// 添加表格日期时间和单位
		HSSFRow rowDate = sheet.createRow(nRowNum);
		HSSFCell cellDate = rowDate.createCell(0);
		CellRangeAddress region2 = new CellRangeAddress(nRowNum, nRowNum,
				(short) 0, (short) 2);
		sheet.addMergedRegion(region2);
		cellDate.setCellValue("统计时段：" + start + "-" + end);
		HSSFCell cellUnit = rowDate.createCell(recordColumns - 1);
		cellUnit.setCellValue(unit);
		nRowNum++;

		// 先将hashmap的键值取出来，做表头
		HSSFRow row = sheet.createRow(nRowNum);
		nRowNum++;
		int nColumnNum = 0;
		for (String key : allUnitPerformance.get(0).keySet()) {
			HSSFCell cell = row.createCell(nColumnNum);
			CellStyle cellStyle = wb.createCellStyle(); // 创建单元格样式
			cell.setCellValue(key);
			// 添加边框
			// 水平居中对齐
			this.setAllBorder(cellStyle);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cell.setCellStyle(cellStyle);
			nColumnNum++;
		}
		// 将数据写入
		CellStyle cellStyle = wb.createCellStyle(); // 创建单元格样式
		for (HashMap<String, Object> record : allUnitPerformance) {
			HSSFRow rowData = sheet.createRow(nRowNum);
			nColumnNum = 0;
			for (String key : record.keySet()) {
				HSSFCell cell = rowData.createCell(nColumnNum);
				// 在这里可以做格式化判断，将数值格式化
				if (key.contains("金额") || key.contains("笔数")
						|| key.contains("度任务")) {

					if (key.contains("金额")) {
						cell.setCellValue(
								Double.valueOf(record.get(key).toString()));
						
						cellStyle.setAlignment(HorizontalAlignment.RIGHT); // 设置单元格水平方向对其方式
						cellStyle
								.setVerticalAlignment(VerticalAlignment.CENTER); // 设置单元格垂直方向对其方式
						HSSFDataFormat format = wb.createDataFormat();
						// 添加边框
						this.setAllBorder(cellStyle);
						cellStyle.setDataFormat(
								format.getFormat("#,##0.00_);[Red](#,##0.00)"));
						cell.setCellStyle(cellStyle); // 设置单元格样式
					}
					if (key.contains("笔数")) {
						cell.setCellValue(
								Double.valueOf(record.get(key).toString()));
//						CellStyle cellStyle = wb.createCellStyle(); // 创建单元格样式
						cellStyle.setAlignment(HorizontalAlignment.RIGHT); // 设置单元格水平方向对其方式
						cellStyle
								.setVerticalAlignment(VerticalAlignment.CENTER); // 设置单元格垂直方向对其方式
						HSSFDataFormat format = wb.createDataFormat();
						// 添加边框
						this.setAllBorder(cellStyle);
						cellStyle.setDataFormat(
								format.getFormat("#,##0_);[Red](#,##0)"));
						cell.setCellStyle(cellStyle); // 设置单元格样式
					}
					if (key.contains("度任务")) {
						cell.setCellValue(
								Double.valueOf(record.get(key).toString()));
//						CellStyle cellStyle = wb.createCellStyle(); // 创建单元格样式
						cellStyle.setAlignment(HorizontalAlignment.RIGHT); // 设置单元格水平方向对其方式
						cellStyle
								.setVerticalAlignment(VerticalAlignment.CENTER); // 设置单元格垂直方向对其方式
						HSSFDataFormat format = wb.createDataFormat();
						// 添加边框
						this.setAllBorder(cellStyle);
						cellStyle.setDataFormat(
								format.getFormat("#,##0_);[Red](#,##0)"));
						cell.setCellStyle(cellStyle); // 设置单元格样式
					}

				} else if (key.contains("完成率")) {
					cell.setCellValue(
							Double.valueOf(record.get(key).toString()));
//					CellStyle cellStyle = wb.createCellStyle(); // 创建单元格样式
					cellStyle.setAlignment(HorizontalAlignment.RIGHT); // 设置单元格水平方向对其方式
					cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 设置单元格垂直方向对其方式
					// 添加边框
					this.setAllBorder(cellStyle);

					if (cell.getNumericCellValue() >= 100) {
						HSSFDataFormat format = wb.createDataFormat();
						cellStyle.setDataFormat(
								format.getFormat("[green]#,##0.00"));
					} else if (cell.getNumericCellValue() >= 50) {
						HSSFDataFormat format = wb.createDataFormat();
						cellStyle.setDataFormat(
								format.getFormat("[blue]#,##0.00"));
					} else {
						HSSFDataFormat format = wb.createDataFormat();
						cellStyle.setDataFormat(
								format.getFormat("[Red]#,##0.00"));
					}
					cell.setCellStyle(cellStyle); // 设置单元格样式
					
				} else {
//					CellStyle cellStyle = wb.createCellStyle(); // 创建单元格样式
					// 添加边框
					this.setAllBorder(cellStyle);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(String.valueOf(record.get(key)));
				}

				nColumnNum++;
			}
			nRowNum++;
		}

		this.autoSizeColumn(sheet, recordColumns);

		try {
			File f = new File("输出文件\\");
			if (!f.exists()) {
				f.mkdirs();
			}
			wb.write(new File("输出文件\\"+excelPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 下载文件，通过OutputStream流
	 * 
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public void downloadFileByOutputStream(String file,
			HttpServletResponse response)
			throws FileNotFoundException, IOException {
		// 1.获取要下载的文件的绝对路径
		String realPath = "输出文件\\"+file;
		// 2.获取要下载的文件名
		String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
		// 3.设置content-disposition响应头控制浏览器以下载的形式打开文件
		response.setHeader("content-disposition",
				"attachment;filename=" +URLEncoder.encode(fileName, "utf-8") );
		// 4.获取要下载的文件输入流
		InputStream in = new FileInputStream(realPath);
		int len = 0;
		// 5.创建数据缓冲区
		byte[] buffer = new byte[1024];
		// 6.通过response对象获取OutputStream流
		OutputStream out = response.getOutputStream();
		// 7.将FileInputStream流写入到buffer缓冲区
		while ((len = in.read(buffer)) > 0) {
			// 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
			out.write(buffer, 0, len);
		}
		in.close();
	}

	public void setAllBorder(CellStyle cellStyle) {
		cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
	}

	public void autoSizeColumn(HSSFSheet sheet, int nColumns) {
		int j = 0;
		while (j < nColumns) {
			sheet.autoSizeColumn((short) j);
			j++;
		}
	}

}
