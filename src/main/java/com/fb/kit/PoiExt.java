package com.fb.kit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoiExt {

	public static List<Map<Integer, String>> ReadExcel(String path) {

		XSSFWorkbook hwb;
		// 处理导入数据
		List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
		try {
			hwb = new XSSFWorkbook(new FileInputStream(new File(path)));
			XSSFSheet sheet = hwb.getSheetAt(0); // 获取到第一个sheet中数据
			for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {// 第二行开始取值，第一行为标题行
				XSSFRow row = sheet.getRow(i);// 获取到第i列的行数据(表格行)
				Map<Integer, String> map = new HashMap<Integer, String>();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j);// 获取到第j行的数据(单元格)
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					map.put(j, cell.getStringCellValue());
				}
				list.add(map);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
