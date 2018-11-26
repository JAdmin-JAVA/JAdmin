package com.jadmin.util.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/** 
 * @Title:web框架
 * @Description:excel工具类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class ExcelUtil {

	private ExcelUtil() {
	}

	private static final ExcelUtil excelUtil = new ExcelUtil();

	public static ExcelUtil getInstance() {
		return excelUtil;
	}

	/****
	 * 遍历Excel2007及以上版本的Excel表格 返回值为List<List> 需要文件的绝对路径
	 * 
	 * @param filePath
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List readExcelXSSFGift(String filePath) {
		File excelFile = null;// Excel文件对象
		InputStream is = null;// 输入流对象
		
		List list = new ArrayList();
		
		try {
			excelFile = new File(filePath);
			is = new FileInputStream(excelFile);// 获取文件输入流
			XSSFWorkbook workbook = new XSSFWorkbook(is);// 创建Excel2007以上文件对象
			XSSFSheet sheet = workbook.getSheetAt(0);// 取出第一个工作表，索引是0
			// 开始循环遍历行，表头不处理，从1开始
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);// 获取行对象
				
               
				if (row == null) {// 如果为空，不处理
					continue;
				}
				
				
				Map map=new HashMap();
			
					
				
				map.put("name", cellCoverString(row.getCell(0)));
				map.put("giftCode", cellCoverString(row.getCell(1)));
				map.put("worth", cellCoverString(row.getCell(2)));
				map.put("modelType", cellCoverString(row.getCell(3)));
				
			    map.put("unit", row.getCell(4).getStringCellValue());
			    map.put("earlyStock", cellCoverString(row.getCell(5)));
			    map.put("dqNeedPoint", cellCoverString(row.getCell(6)));
			    map.put("hqNeedPoint", cellCoverString(row.getCell(7)));
			    
			    map.put("memo", cellCoverString(row.getCell(8)));
			    map.put("changeCount", cellCoverString(row.getCell(9)));
			    map.put("giftTypeId", cellCoverString(row.getCell(10)));
			    map.put("remainderStock", cellCoverString(row.getCell(11)));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public String cellCoverString(XSSFCell cell) {
		String cellStr = null;
		if (cell == null) {// 单元格为空设置cellStr为空串
			cellStr = "";
		} else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
			cellStr = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理
			cellStr = cell.getNumericCellValue() + "";
		} else {// 其余按照字符串处理
			cellStr = cell.getStringCellValue();
		}
		return cellStr;
	}

	/****
	 * 遍历Excel2003版本的Excel表格 返回值为List<List> 需要文件的绝对路径
	 * 
	 * @param filePath
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List readExcelHSSFGift(String filePath) {
		File excelFile = null;// Excel文件对象
		InputStream is = null;// 输入流对象
		String cellStr = null;// 单元格，最终按字符串处理
		List list = new ArrayList();
		try {
			excelFile = new File(filePath);
			is = new FileInputStream(excelFile);// 获取文件输入流
			HSSFWorkbook workbook = new HSSFWorkbook(is);// 创建Excel2003文件对象
			HSSFSheet sheet = workbook.getSheetAt(0);// 取出第一个工作表，索引是0
			// 开始循环遍历行，表头不处理，从1开始
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);// 获取行对象
				if (row == null) {// 如果为空，不处理
					continue;
				}
				// 循环遍历单元格
				Map map=new HashMap();
				map.put("name", cellCoversString(row.getCell(0)));
				map.put("giftCode", cellCoversString(row.getCell(1)));
				map.put("worth", cellCoversString(row.getCell(2)));
				map.put("modelType", cellCoversString(row.getCell(3)));
				
			    map.put("unit", row.getCell(4).getStringCellValue());
			    map.put("earlyStock", cellCoversString(row.getCell(5)));
			    map.put("dqNeedPoint", cellCoversString(row.getCell(6)));
			    map.put("hqNeedPoint", cellCoversString(row.getCell(7)));
			  
			    map.put("memo", cellCoversString(row.getCell(8)));
			    map.put("changeCount", cellCoversString(row.getCell(9)));
			    map.put("giftTypeId", cellCoversString(row.getCell(10)));
			    map.put("remainderStock", cellCoversString(row.getCell(11)));
			  
				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public String cellCoversString(HSSFCell cell) {
		String cellStr = null;
		if (cell == null) {// 单元格为空设置cellStr为空串
			cellStr = "";
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
			cellStr = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理
			cellStr = cell.getNumericCellValue() + "";
		} else {// 其余按照字符串处理
			cellStr = cell.getStringCellValue();
		}
		return cellStr;
	}
	/**
	 * 导出
	 * @param titles
	 * @param fields
	 * @param data
	 * @return
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static byte[] expMap(String[] titles, String[] fields, List<Map<String, Object>> data) 
			throws SecurityException, NoSuchMethodException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<Object> list = new ArrayList<Object>();
		for(int i=0; i<data.size(); i++) {
			list.add(data.get(i));
		}
		return exp(titles, fields, list);
	}
	
	/**
	 * 导出(双层表头)
	 * @param titles 第二层表头
	 * @param titlesHeader 第一层表头
	 * @param fields 
	 * @param resultlList
	 * @param mergedCell 合并行长度
	 * @return
	 */
	public static byte[] expMap(String[] titles, String[] titlesHeader,String[] fields, List<Map<String, Object>> data,int mergedCell) 
			throws SecurityException, NoSuchMethodException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		List<Object> list = new ArrayList<Object>();
		for(int i=0; i<data.size(); i++) {
			list.add(data.get(i));
		}
		return exp(titles, titlesHeader,fields, list,mergedCell);
	}
	
	/**
	 * 导出(双层表头)
	 * @param titles
	 * @param titlesHeader
	 * @param fields
	 * @param list
	 * @return
	 */
	private static byte[] exp(String[] titles, String[] titlesHeader,String[] fields, List<Object> data,int mergedCell) throws SecurityException, 
			NoSuchMethodException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = null;
		try {
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet();
			addTitle(wb, sheet, titles,titlesHeader,mergedCell);
			addDataDoule(wb, sheet, fields, data);
			baos = new ByteArrayOutputStream();
			wb.write(baos);
			return baos.toByteArray();
		} finally {
			if(baos != null) {
				try {baos.close();} catch (IOException e) {}
			}
		}
	}

	

	/**
	 * 导出
	 * @param titles
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("rawtypes")
	public static byte[] exp(String[] titles, String[] fields, List data) throws SecurityException, 
			NoSuchMethodException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ByteArrayOutputStream baos = null;
		try {
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet();
			addTitle(wb, sheet, titles);
			addData(wb, sheet, fields, data);
			baos = new ByteArrayOutputStream();
			wb.write(baos);
			return baos.toByteArray();
		} finally {
			if(baos != null) {
				try {baos.close();} catch (IOException e) {}
			}
		}
	}
	
	/**
	 * 根据模版导出EXCEL
	 * @param template 模版名称
	 * @param data  数据
	 * @return
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("rawtypes")
	public static byte[] exp(String template, List data) throws IOException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		try {
			 //URL url = ExcelUtil.class.getClassLoader().getResource("\\");
			//"/opt/weblogic/user_projects/domains/gsbank/servers/AdminServer/upload/WEB-INF/classes/com/bank/util/excel"
			//System.out.println("URL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+url);
			//String path = url.getPath();
			//String path ="/C:/javaweb/.metadata/.plugins/org.eclipse.wst.server.core/tmp9/wtpwebapps/pam/WEB-INF/classes/";
			String path ="/opt/weblogic/user_projects/domains/gsbank/servers/AdminServer/upload/WEB-INF/classes/";
			System.out.println("path>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			String fileName = path.substring(0, path.lastIndexOf("classes")) + "template" + 
					File.separator + template + ".xls";
			fis = new FileInputStream(fileName);
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			Workbook wb = new HSSFWorkbook(fs);
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(1);
			List<String> fieldList = new ArrayList<String>();
			for(Iterator<Cell> iter = row.cellIterator(); iter.hasNext(); ) {
				fieldList.add(iter.next().getStringCellValue());
			}
			String[] fields = new String[fieldList.size()];
			fieldList.toArray(fields);
			sheet.removeRow(row);
			addData(wb, sheet, fields, data);
			baos = new ByteArrayOutputStream();
			wb.write(baos);
			return baos.toByteArray();
		} finally {
			if(fis != null) {
				try {fis.close();} catch(IOException e){}
			}
			if(baos != null) {
				try {baos.close();} catch (IOException e) {}
			}
		}
	}
	
	/**
	 * 添加标题
	 * @param wb
	 * @param sheet
	 * @param titles
	 * @param data
	 */
	private  static void addTitle(Workbook wb, Sheet sheet, String[] titles) {
		Font titleFont = wb.createFont();
		CellStyle titleCellStyle = wb.createCellStyle();
		titleFont.setFontName("宋体");
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints((short)11);
		titleCellStyle.setFont(titleFont);
		titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		CreationHelper createHelper = wb.getCreationHelper();
		Row row = sheet.createRow(0);
		row.setHeight((short)340);
		for(int i=0; i<titles.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(createHelper.createRichTextString(titles[i]));
			cell.setCellStyle(titleCellStyle);
			sheet.setColumnWidth(i, 2000 + (titles[i].length() * 600));
		}
	}
	/**
	 * 添加标题(双层)
	 * @param wb
	 * @param sheet
	 * @param titles
	 * @param titlesHeader
	 */
	
	private static void addTitle(Workbook wb, Sheet sheet, String[] titles,
			String[] titlesHeader ,int mergedCell) {
		// TODO Auto-generated method stub
		Font titleFont = wb.createFont();
		CellStyle titleCellStyle = wb.createCellStyle();
		titleFont.setFontName("宋体");
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints((short)11);
		titleCellStyle.setFont(titleFont);
		titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		CreationHelper createHelper = wb.getCreationHelper();
		Row row = sheet.createRow(0);
		row.setHeight((short)340);
		for(int i=0; i<titlesHeader.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(createHelper.createRichTextString(titlesHeader[i]));
			cell.setCellStyle(titleCellStyle);
			sheet.setColumnWidth(i, 2000 + (titlesHeader[i].length() * 600));
			
		}
		for(int i=2; i<titlesHeader.length; i= i+mergedCell){
			sheet.addMergedRegion(new CellRangeAddress(0, 0, i, i+mergedCell-1));
		}
		Row rowNext = sheet.createRow(1);
		rowNext.setHeight((short)340);
		for(int i=0; i<titles.length; i++) {
			Cell cell = rowNext.createCell(i);
			cell.setCellValue(createHelper.createRichTextString(titles[i]));
			cell.setCellStyle(titleCellStyle);
			sheet.setColumnWidth(i, 2000 + (titles[i].length() * 600));
		}
	}
	/**
	 * 添加数据到EXCEL中(双层)
	 * @param data
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("rawtypes")
	private static void addDataDoule(Workbook wb, Sheet sheet, String fields[], List data) 
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		CreationHelper createHelper = wb.getCreationHelper();
		DataFormat df = wb.createDataFormat();
		CellStyle cs = wb.createCellStyle();
		int n = 1;
		for(int i=0; i<data.size(); i++) {
			Row row = sheet.createRow(n+1);
			for(int j=0; j<fields.length; j++) {
				Cell cell = row.createCell(j);
				Object value =getObjectValue(data.get(i), fields[j]);
				if(value instanceof String) {
					cell.setCellValue(createHelper.createRichTextString(value.toString()));
				} else if(value instanceof BigDecimal) {
					cs.setDataFormat(df.getFormat("##,###.####"));
					cell.setCellStyle(cs);
					cell.setCellValue(((BigDecimal)value).doubleValue());
				} else if(value instanceof Double) {
					cs.setDataFormat(df.getFormat("##,###.####"));
					cell.setCellStyle(cs);
					cell.setCellValue((Double)value);
				} else if(value instanceof Integer) {
					cs.setDataFormat(df.getFormat("##,###.####"));
					cell.setCellStyle(cs);
					cell.setCellValue((Integer)value);
				}
			}
			n++;
		}
	}
	
	/**
	 * 添加数据到EXCEL中
	 * @param data
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("rawtypes")
	private static void addData(Workbook wb, Sheet sheet, String fields[], List data) 
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		CreationHelper createHelper = wb.getCreationHelper();
		DataFormat df = wb.createDataFormat();
		CellStyle cs = wb.createCellStyle();
		for(int i=0; i<data.size(); i++) {
			Row row = sheet.createRow(i+1);
			for(int j=0; j<fields.length; j++) {
				Cell cell = row.createCell(j);
				Object value =getObjectValue(data.get(i), fields[j]);
				if(value instanceof String) {
					cell.setCellValue(createHelper.createRichTextString(value.toString()));
				} else if(value instanceof BigDecimal) {
					cs.setDataFormat(df.getFormat("##,###.####"));
					cell.setCellStyle(cs);
					cell.setCellValue(((BigDecimal)value).doubleValue());
				} else if(value instanceof Double) {
					cs.setDataFormat(df.getFormat("##,###.####"));
					cell.setCellStyle(cs);
					cell.setCellValue((Double)value);
				} else if(value instanceof Integer) {
					cs.setDataFormat(df.getFormat("##,###.####"));
					cell.setCellStyle(cs);
					cell.setCellValue((Integer)value);
				}
			}
		}
	}
	
	/**
	 * 根据字段名称获取值
	 * @param obj
	 * @param field
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("rawtypes")
	private static Object getObjectValue(Object obj, String field) throws SecurityException, 
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String dictCode = null;
		if(field.indexOf('#') != -1) {
			dictCode = field.substring(field.indexOf('#') + 1);
			field = field.substring(0, field.indexOf('#'));
		}
		String[] fieldArr = field.split("の");
		Object value = obj;
		for(int i=0; i<fieldArr.length; i++) {
			if(value == null) {
				return value;
			}
			if(value instanceof Map) {
				value = ((Map)value).get(fieldArr[i]);
			} else if(value instanceof Serializable) {
				value = value.getClass().getMethod("get" + fieldArr[i].substring(0, 1).toUpperCase() 
						+ fieldArr[i].substring(1)).invoke(value);
			}
		}
//		if(dictCode != null && value != null) {
//			value = DictionaryService.getByNickNameAndValue(dictCode, value.toString());
//			if(value != null) {
//				value = ((Dictionary)value).getName();
//			}
//		}
		return value;
	}

	/**
	 * 导出，能显示fields
	 * @param titles
	 * @param fields
	 * @param result
	 * @return
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static byte[] downloadMap(String[] titles, String[] fields,
			List<Map<String, Object>> data) throws SecurityException, NoSuchMethodException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<Object> list = new ArrayList<Object>();
		for(int i=0; i<data.size(); i++) {
			list.add(data.get(i));
		}
		return download(titles, fields, list);
	}
	
	/**
	 * 导出
	 * @param titles
	 * @param fields
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private static byte[] download(String[] titles, String[] fields,
			List<Object> data) throws IOException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ByteArrayOutputStream baos = null;
		try {
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet();
			downloadTitle(wb, sheet, titles ,fields);
			downloadData(wb, sheet, fields, data);
			baos = new ByteArrayOutputStream();
			wb.write(baos);
			return baos.toByteArray();
		} finally {
			if(baos != null) {
				try {baos.close();} catch (IOException e) {}
			}
		}
	}
	
	/**
	 * 添加标题和fields
	 * @param wb
	 * @param sheet
	 * @param titles
	 * @param fields
	 */
	private static void downloadTitle(Workbook wb, Sheet sheet, String[] titles,
			String[] fields) {
		Font titleFont = wb.createFont();
		CellStyle titleCellStyle = wb.createCellStyle();
		titleFont.setFontName("宋体");
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints((short)11);
		titleCellStyle.setFont(titleFont);
		titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		CreationHelper createHelper = wb.getCreationHelper();
		Row row = sheet.createRow(0);
		row.setHeight((short)340);
		
		for(int i=0; i<titles.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(createHelper.createRichTextString(titles[i]));
			cell.setCellStyle(titleCellStyle);
			sheet.setColumnWidth(i, 2000 + (titles[i].length() * 600));
		}
		row = sheet.createRow(1);
		row.setHeight((short)340);
		for(int i=0; i<fields.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(createHelper.createRichTextString(fields[i]));
			cell.setCellStyle(titleCellStyle);
			sheet.setColumnWidth(i, 2000 + (fields[i].length() * 600));
		}
	}

	/**
	 * 导出数据，从第三行开始
	 * @param wb
	 * @param sheet
	 * @param fields
	 * @param data
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private static void downloadData(Workbook wb, Sheet sheet, String[] fields,
			List<Object> data) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		CreationHelper createHelper = wb.getCreationHelper();
		DataFormat df = wb.createDataFormat();
		CellStyle cs = wb.createCellStyle();
		for(int i=0; i<data.size(); i++) {
			Row row = sheet.createRow(i+2);
			for(int j=0; j<fields.length; j++) {
				Cell cell = row.createCell(j);
				Object value =getObjectValue(data.get(i), fields[j]);
				if(value instanceof String) {
					cell.setCellValue(createHelper.createRichTextString(value.toString()));
				} else if(value instanceof BigDecimal) {
					cs.setDataFormat(df.getFormat("##,###.####"));
					cell.setCellStyle(cs);
					cell.setCellValue(((BigDecimal)value).doubleValue());
				} else if(value instanceof Double) {
					cs.setDataFormat(df.getFormat("##,###.####"));
					cell.setCellStyle(cs);
					cell.setCellValue((Double)value);
				} else if(value instanceof Integer) {
					cs.setDataFormat(df.getFormat("##,###.####"));
					cell.setCellStyle(cs);
					cell.setCellValue((Integer)value);
				}
			}
		}
	}
	
	/**
	 * 取cell的double值
	 * @param row
	 * @param index
	 * @return
	 */
	public static double getDoubleValue(Row row,int index){  
		double rtn = 0;  
		try {  
			Cell cell = row.getCell((short)index);  
			rtn = cell.getNumericCellValue();  
			
		} catch (RuntimeException e) {  
		}  
		return rtn;  
	}
	
	/**
	 * 取cell的String值
	 * @param row
	 * @param index
	 * @return
	 */
	public static String getStringValue(Row row,int index){  
		String rtn = "";  
		try {  
			Cell cell = row.getCell((short)index);  
			rtn = cell.getRichStringCellValue().getString();  
		} catch (RuntimeException e) { 
			
		}  
		return rtn;  
	}
	
	public static String getCellValue(Row row,int index){
		String rtn = "";  
		try {  
			Cell cell = row.getCell((short)index);  
			int cellType = cell.getCellType();
			/*
			 * CellType 类型 值
			 * CELL_TYPE_NUMERIC 数值型 
			 * CELL_TYPE_STRING 字符串型 
			 * CELL_TYPE_FORMULA 公式型 
			 * CELL_TYPE_BLANK 空值 
			 * CELL_TYPE_BOOLEAN 布尔型 
			 * CELL_TYPE_ERROR 错误 
			 * 
			 */
			NumberFormat format = NumberFormat.getInstance();
			format.setGroupingUsed(false);
			switch (cellType) {
			case Cell.CELL_TYPE_NUMERIC :
				//转换1.23E10科学计数法类型的数据
				rtn = format.format(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				rtn = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_FORMULA:
				rtn = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_BOOLEAN :
				rtn = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				break;
			} 
		} catch (RuntimeException e) { 
			
		}  
		return rtn; 
	}

}
