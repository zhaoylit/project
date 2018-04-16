package com.zyl.export.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * <p>Description: </p>
 * @author zyl
 * @date 2018年1月26日
 * @version 1.0
 */
public class ExportExcel<T> {
  //显示的导出表的标题
 private String title;
 //导出表的列名
 private String[] rowName ;
 
 private List<Object[]>  dataList = new ArrayList<Object[]>();
 
 HttpServletResponse  response;
 
 public ExportExcel(){}
 //构造方法，传入要导出的数据
 public ExportExcel(String title,String[] rowName,List<Object[]>  dataList){
     this.dataList = dataList;
     this.rowName = rowName;
     this.title = title;
 }
 /*
  * 导出数据
  * */
 public XSSFWorkbook export() throws Exception{
     try{
         XSSFWorkbook  workBook = new XSSFWorkbook ();                        // 创建工作簿对象
         XSSFSheet sheet = workBook.createSheet();
         workBook.setSheetName(0,title);
         
         // 产生表格标题行
         XSSFRow rowm = sheet.createRow(0);
         XSSFCell cellTiltle = rowm.createCell(0);
         
         //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
         XSSFCellStyle columnTopStyle = workBook.createCellStyle();  //获取列头样式对象
         XSSFCellStyle style = workBook.createCellStyle();                   //单元格样式对象
//         sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));  
//         cellTiltle.setCellStyle(columnTopStyle);
//         cellTiltle.setCellValue(title);
         
         // 定义所需列数
         int columnNum = rowName.length;
         XSSFRow rowRowName = sheet.createRow(0);                // 在索引2的位置创建行(最顶端的行开始的第二行)
         
         // 将列头设置到sheet的单元格中
         for(int n=0;n<columnNum;n++){
             XSSFCell  cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
             cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
             XSSFRichTextString text = new XSSFRichTextString(rowName[n]);
             cellRowName.setCellValue(text);                                    //设置列头单元格的值
             cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
         }
         
         //将查询出的数据设置到sheet对应的单元格中
         for(int i=0;i<dataList.size();i++){
             
             Object[] obj = dataList.get(i);//遍历每个对象
             XSSFRow row = sheet.createRow(i+1);//创建所需的行数
             
             for(int j=0; j<obj.length; j++){
                 XSSFCell  cell = null;   //设置单元格的数据类型
                 if(j == 0){
                     cell = row.createCell(j,XSSFCell.CELL_TYPE_NUMERIC);
                     cell.setCellValue(i+1);    
                 }else{
                     cell = row.createCell(j,XSSFCell.CELL_TYPE_STRING);
                     if(!"".equals(obj[j]) && obj[j] != null){
                         String content = obj[j].toString();
                         if(content.contains("://") && !StringUtils.isContainsChinese(content)){
                             if(content.length() <255){
                                 cell.setCellFormula("HYPERLINK(\""+content+"\",\""+content+"\")");
                             }else{
                                 cell.setCellValue(content);  
                             }
                         }else{
                             cell.setCellValue(content);                        //设置单元格的值
                         }
                     }
                 }
                 cell.setCellStyle(style);                                    //设置单元格样式
             }
         }
         
         //让列宽随着导出的列长自动适应
         for (int colNum = 0; colNum < columnNum; colNum++) {
             int colWidth = sheet.getColumnWidth(colNum)*2;
             for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                 XSSFRow currentRow;
                 //当前行未被使用过
                 if (sheet.getRow(rowNum) == null) {
                     currentRow = sheet.createRow(rowNum);
                 } else {
                     currentRow = sheet.getRow(rowNum);
                 }
                 if (currentRow.getCell(colNum) != null) {
                     XSSFCell currentCell = currentRow.getCell(colNum);
                     if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                         int length = currentCell.getStringCellValue().getBytes().length;
                         if (colWidth < length) {
                             colWidth = length;
                         }
                     }
                 }
             }
             if(colWidth<255*256){
                 sheet.setColumnWidth(colNum, colWidth < 3000 ? 3000 : colWidth);    
             }else{
                 sheet.setColumnWidth(colNum,6000 );
             }
         }
         return workBook;
     }catch(Exception e){
         e.printStackTrace();
     }
    return null;
 }
}
