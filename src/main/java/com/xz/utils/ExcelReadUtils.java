package com.xz.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

/**
 * excel读取工具
 * @author dean
 *
 */
public class ExcelReadUtils {
	
	public static Workbook getWorkbook(String excelFile) throws IOException {
		FileInputStream is = null;
		Workbook workbook = null;
		try {
			is = new FileInputStream(excelFile);
			workbook = getWorkbook(is);
		} finally {
			
		}
		return workbook;
	}
	
	public static Workbook getWorkbook(InputStream is) throws IOException {
		Workbook wb = null;
		ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
		ByteArrayInputStream byteArrayInputStream = new ByteInputStream();
		try {
			byte[] buffer = new byte[512];
			int count = -1;
			while ((count = is.read(buffer)) != -1){
				byteOS.write(buffer, 0, count);
			}
			byte[] allBytes = byteOS.toByteArray();
			byteArrayInputStream = new ByteArrayInputStream(allBytes);
			wb = new XSSFWorkbook(byteArrayInputStream);
		} catch (Exception ex) {
			wb = new HSSFWorkbook(byteArrayInputStream);
		}finally{
			if(byteOS != null){
				byteOS.close();
			}
			if(byteArrayInputStream != null){
				byteArrayInputStream.close();
			}
		}
		return wb;
	}
	public static int getSheetNums(String excelFile) throws IOException{
		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(excelFile);
			wb = getWorkbook(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(is != null){
				is.close();
			}
		}
		return wb.getNumberOfSheets();
	}
	
	public static ArrayList<ArrayList<Object>> readAllRows(String excelFile) throws IOException {
		FileInputStream is = null;
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
		try {
			is = new FileInputStream(excelFile);
			list = readAllRows(is);
		} finally {
			if(is != null){
				is.close();
			}
		}
		return list;
	}
	public static ArrayList<ArrayList<Object>> readAllRows(File file) throws IOException {
		FileInputStream is = null;
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
		try {
			is = FileUtils.openInputStream(file);
			list = readAllRows(is);
		} finally {
			if(is != null){
				is.close();
			}
		}
		return list;
	}
	
	public static ArrayList<ArrayList<Object>> readAllRowsBySheet(String excelFile,int sheetNum) throws IOException {
		FileInputStream is =null;
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
		try {
			is = new FileInputStream(excelFile);
			list = readAllRowsBySheet(is, sheetNum);
		} finally {
			if(is != null){
				is.close();
			}
		}
		return list;
	}
	
	public static ArrayList<ArrayList<Object>> readAllRowsBySheet(File file,int sheetNum) throws IOException {
		FileInputStream is = null;
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
		try {
			is = FileUtils.openInputStream(file);
			list = readAllRowsBySheet(is,sheetNum);
		} finally {
			if(is != null){
				is.close();
			}
		}
		return list;
	}
	
	public static ArrayList<ArrayList<Object>> readAllRows(InputStream is) throws IOException {
		ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
		Workbook wb = getWorkbook(is);
	    for (int i = 0; i < wb.getNumberOfSheets(); i++) {//获取每个Sheet表
	    	if(i == 0){
		    	Sheet sheet = wb.getSheetAt(i);
				if (sheet.getLastRowNum() > 0) {
					rowList.addAll(readRows(sheet));
				}
	    	}
        }
	    wb = null;
		return rowList;
	}
	public static ArrayList<ArrayList<Object>> readAllRowsBySheet(InputStream is,int sheetNum) throws IOException {
		Workbook wb = getWorkbook(is);
		ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
		
//		for (int i = 0; i < wb.getNumberOfSheets(); i++) {//获取每个Sheet表
//			Sheet sheet = wb.getSheetAt(i);
//			if (sheet.getLastRowNum() > 0) {
//				rowList.addAll(readRows(sheet));
//			}
//		}
		Sheet sheet = wb.getSheetAt(sheetNum);
		if (sheet.getLastRowNum() > 0) {
			rowList.addAll(readRows(sheet));
		}
		return rowList;
	}

	public static ArrayList<ArrayList<Object>> readRows(String excelFile,
			int startRowIndex, int rowCount) throws IOException {
		FileInputStream is = null;
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
		try {
			is = new FileInputStream(excelFile);
			list = readRows(is, startRowIndex, rowCount);
		} finally {
			if(is != null){
				is.close();
			}
		}
		return list;
	}
	
	public static ArrayList<ArrayList<Object>> readRows(String excelFile) throws IOException {
		FileInputStream is = null;
		ArrayList<ArrayList<Object>> list  = new ArrayList<ArrayList<Object>>();
		try {
			is = new FileInputStream(excelFile);
			list = readRows(is);
		} finally {
			if(is != null){
				is.close();
			}
		}
		return list;
	}
	
	public static ArrayList<ArrayList<Object>> readRows(String excelFile, int i) throws IOException {
		FileInputStream is = null;
		ArrayList<ArrayList<Object>> list =new ArrayList<ArrayList<Object>>();
		try {
			is = new FileInputStream(excelFile);
			list = readRows(is, i);
		} finally {
			if(is != null)
				is.close();
		}	
		return list;
	}

	public static ArrayList<ArrayList<Object>> readRows(InputStream is,
			int startRowIndex, int rowCount) throws IOException {
		Workbook wb = getWorkbook(is);
		Sheet sheet = wb.getSheetAt(0);

		return readRows(sheet, startRowIndex, rowCount);
	}
	
	public static ArrayList<ArrayList<Object>> readRows(InputStream is) throws IOException {
		Workbook wb = getWorkbook(is);
		Sheet sheet = wb.getSheetAt(0);
		return readRows(sheet);
	}
	
	public static ArrayList<ArrayList<Object>> readRows(InputStream is, int i) throws IOException {
		Workbook wb = getWorkbook(is);
		Sheet sheet = wb.getSheetAt(i);
		return readRows(sheet);
	}

	public static ArrayList<ArrayList<Object>> readRows(Sheet sheet,
			int startRowIndex, int rowCount) {
		ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
		int totalCellNum = sheet.getRow(0).getLastCellNum();
		for (int i = 0; i <= (startRowIndex + rowCount); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				break;
			}
			
			ArrayList<Object> cellList = new ArrayList<Object>();
//			for (Cell cell : row) {
//				cellList.add(readCell(cell));
//			}
			for (int j = 0; j < totalCellNum; j++) {
				cellList.add(readCell(row.getCell(j)));
			}

			rowList.add(cellList);
		}
		
		return rowList;
	}
	
	public static ArrayList<ArrayList<Object>> readRows(Sheet sheet) {
		int rowCount = sheet.getLastRowNum();
		return readRows(sheet, 0, rowCount);
	}
	//execel自定义时间格式
	private static short[]  dateFormat = new short[]{14,15,16,17,18,19,20,21,30,31,32,57,58};
	private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Object readCell(Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			String str = cell.getRichStringCellValue().getString();
			return str == null ? null : str.trim();

		case Cell.CELL_TYPE_NUMERIC:
//			System.out.println(cell.getCellStyle().getDataFormat());
			if (ArrayUtils.contains(dateFormat,cell.getCellStyle().getDataFormat())) {
				cell.getCellStyle().getDataFormat();
				return sFormat.format(cell.getDateCellValue());
			} else {
//				String value = "";
//				String msg = null;
//				try {
//					value = String.valueOf(cell.getNumericCellValue());
//				} catch (Exception e) {
//					e.printStackTrace();
//					msg = e.getMessage();
//				}
//				if(StringUtils.isNotBlank(msg)){
//					return 0;
//				}else{
//					return String.valueOf(cell.getNumericCellValue());
//				}
				NumberFormat nf = NumberFormat.getInstance();
				String s = nf.format(cell.getNumericCellValue());
				if (s.indexOf(",") >= 0) {
				    s = s.replace(",", "");
				}
				return s;
			}

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_FORMULA:
//			if (DateUtil.isCellDateFormatted(cell)) {
//				return cell.getDateCellValue();
//			} else {
				return cell.getCellFormula();
//			}

		case Cell.CELL_TYPE_BLANK:
			return null;

		default:
			System.err.println("Data error for cell of excel: " + cell.getCellType());
			return null;
		}

	}
	
	public static int getLastRowNum(String path) throws IOException {
		Workbook wb = getWorkbook(path);
		Sheet sheet = wb.getSheetAt(0);
		return sheet.getLastRowNum();
	}
	
}
