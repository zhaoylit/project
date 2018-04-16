package com.zkkj.backend.service.biz.info;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.zkkj.backend.common.util.WDWUtil;

public class ReadExcel {
	Logger logger = LoggerFactory.getLogger(ReadExcel.class);
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcel(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;} 
    //获取总列数
    public int getTotalCells() {  return totalCells;} 
    //获取错误信息
    public String getErrorInfo() { return errorMsg; } 
    
    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath){
          if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){  
              errorMsg = "文件名不是excel格式";  
              return false;  
          }  
          return true;
    }
    
    /**
     * 读EXCEL文件，获取客户信息集合
     * @param fielName
     * @return
     */
    public List<Map<String,Object>> getExcelInfo(String fileName,String pathStr,MultipartFile Mfile){
        
        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
         CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; //获取本地存储路径
         String[] pathStrings=pathStr.split(",");
         File file = new  File(pathStrings[0]);
         logger.info("路径是：" + pathStrings[0]);
         //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
         if (!file.exists()) {
        	 file.mkdirs();
         }
         //新建一个文件
         File file1 = new File(pathStrings[0]+pathStrings[1]); 
         //将上传的文件写入新建的文件中
         try {
             cf.getFileItem().write(file1); 
         } catch (Exception e) {
             e.printStackTrace();
         }
         
         //初始化客户信息的集合    
         List<Map<String, Object>> flightList = new ArrayList<Map<String,Object>>();
         //初始化输入流
         InputStream is = null;  
         try{
            //验证文件名是否合格
            if(!validateExcel(fileName)){
                return null;
            }
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true; 
            if(WDWUtil.isExcel2007(fileName)){
                isExcel2003 = false;  
            }
            //根据新建的文件实例化输入流
            is = new FileInputStream(file1);
            //根据excel里面的内容读取客户信息
            flightList = getExcelInfo(is, isExcel2003); 
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        } finally{
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;    
                    e.printStackTrace();  
                }
            }
        }
        return flightList;
    }
    /**
     * 根据excel里面的内容读取信息
     * @param is 输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public  List<Map<String, Object>> getExcelInfo(InputStream is,boolean isExcel2003){
         List<Map<String, Object>> customerList=null;
         try{
             /** 根据版本选择创建Workbook的方式 */
             Workbook wb = null;
             //当excel是2003时
             if(isExcel2003){
                wb = new HSSFWorkbook(is); 
             }
             else{//当excel是2007时
                wb = new XSSFWorkbook(is); 
             }
             //读取Excel里面客户的信息
             customerList=readExcelValue(wb);
         }
         catch (IOException e)  {  
             e.printStackTrace();  
         }  
         return customerList;
    }
    
    /**
     * 读取Excel里面的信息
     * @param wb
     * @return
     */
    private List<Map<String, Object>> readExcelValue(Workbook wb){
    	//得到第一个shell
    	Sheet sheet = wb.getSheetAt(0);
    	//得到Excel的行数
    	this.totalRows = sheet.getPhysicalNumberOfRows();
    	//得到Excel的列数(前提是有行数)
    	if(totalRows >= 1&&sheet.getRow(0)!=null){
    		this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
    	}
    	List<Map<String, Object>> maplistList = new ArrayList<Map<String,Object>>();
    	
    	Map<String, Object> readMap;
    	Row firtRow = sheet.getRow(0);
    	//循环Excel行数 
    	for (int i = 1; i < totalRows; i++) {
			Row row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			readMap = new HashMap<String, Object>();
			//循环Cell的列
			for (int j = 0; j < totalCells; j++) {
				Cell cell = row.getCell(j);
				if (null != cell){
	                readMap.put(firtRow.getCell(j).getStringCellValue(), cell.getStringCellValue());
	               }
			}
			maplistList.add(readMap);
		}
    	return maplistList;
    }
}
