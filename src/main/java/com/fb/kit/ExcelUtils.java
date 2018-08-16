package com.fb.kit;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils{
//	private static final String EXTENSION_XLS="xls";
//	private static final String EXTENSION_XLSX="xlsx";
	public static final String EXTENSION_XLS="xls";
	public static final String EXTENSION_XLSX="xlsx";
	/**
	 * 判断xml版本
	 * @param in
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbook(InputStream in,String fileName) throws IOException{
		Workbook workbook=null;
		if(fileName.endsWith(EXTENSION_XLS)){
			workbook=new HSSFWorkbook(in);
		}else if(fileName.endsWith(EXTENSION_XLSX)) {
			workbook=new XSSFWorkbook(in);
		}
		return workbook;
	}
	
	private void preReadCheck(String filePath) throws FileNotFoundException,FileFormatException{
		File file=new File(filePath);
		if(!file.exists())
			throw new FileNotFoundException("传入文件不存在:"+filePath);
		if(!(filePath.endsWith(EXTENSION_XLS)||filePath.endsWith(EXTENSION_XLSX)))
			throw new FileFormatException("传入文件不是excel");
	}

    public Map<Integer, String> getSheetTitle(String path) throws FileNotFoundException, FileFormatException{
        Map<Integer, String> map = new HashMap<Integer, String>();
        // 工作簿
        Workbook workbook = null;
        InputStream is=null;
        //检查
        preReadCheck(path);
        try {
            is=new FileInputStream(path);
            workbook=getWorkbook(is, path);
            Sheet sheet= workbook.getSheetAt(0);	// 获取到第一个sheet中数据
            Row row1 = sheet.getRow(0);    //获取表头的数据
            for(int j=0;j<row1.getLastCellNum(); j++) {
                Cell cell = row1.getCell(j);	// 获取到第j行的数据(单元格)
                if(cell!=null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if(!"".equals(cell.getStringCellValue())){
                    	map.put(j, cell.getStringCellValue());
					}
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

	public List<Map<Integer, String>> dealDataByPath(String path) throws FileNotFoundException, FileFormatException {
		List<Map<Integer,String>> list = new ArrayList<Map<Integer,String>>();
		// 工作簿
		Workbook workbook = null;
		InputStream is=null;
		//检查
		preReadCheck(path);
		try {
			is=new FileInputStream(path);
			workbook=getWorkbook(is, path);
			Sheet sheet= workbook.getSheetAt(0);	// 获取到第一个sheet中数据
			for(int i = 1;i<sheet.getLastRowNum()+1; i++) {// 第二行开始取值，第一行为标题行
				Row row = sheet.getRow(i);		// 获取到第i列的行数据(表格行)
				Map<Integer, String> map = new HashMap<Integer, String>();
				if(row!=null){					
					for(int j=0;j<row.getLastCellNum(); j++) {
						Cell cell = row.getCell(j);	// 获取到第j行的数据(单元格)
						if(cell!=null){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							map.put(j, cell.getStringCellValue());
						}
					}
					list.add(map);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}



	public static String getFileMd5(String path) {
		String md5Str= StringUtils.EMPTY;
		FileInputStream fis=null;
		try {
			if(StringUtils.isEmpty(path))
				throw new IllegalArgumentException("path is null");
			File file = new File(path);
			fis = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = fis.read(buffer, 0, 1024)) != -1) {
				md.update(buffer, 0, length);
			}
			BigInteger bigInt = new BigInteger(1, md.digest());
			md5Str= bigInt.toString(16);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return md5Str;
	}

}
