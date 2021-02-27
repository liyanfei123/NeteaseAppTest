package com.liyanfei.util;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DataFromExcel {
    public static Logger logger = Logger.getLogger(DataFromExcel.class.getName());

    private static XSSFWorkbook book;
    private static XSSFSheet sheet;
    private static XSSFCell cell;

    /**
     * 读取.xlsx的Excel文件
     * @param dir Excel文件目录
     * @param file 文件名
     * @throws Exception
     */
    public static void readExcel(String dir, String file) throws Exception {
        String filePath = dir + File.separator + file;
        if (file.endsWith(".xlsx")) {
            book = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        } else {
            logger.info("Excel文件格式不对");
            throw new Exception("文件格式错误");
        }
        if (book == null) {
            logger.info("数据文件为空");
            throw new Exception("Excel文件为空");
        }
    }

    /**
     * 读取指定等表
     * @param sheetName 表名
     * @throws Exception
     */
    public static void readSheet(String sheetName) throws Exception {
        sheet = book.getSheet(sheetName);
    }

    /**
     * 关闭数据文件
     * @throws Exception
     */
    public static void closeExcel() throws Exception {
        book.close();
    }

    /**
     * 给定单元格位置内的数据
     * @param sheetName 表名
     * @param rowNum 行号
     * @param colNum 列号
     * @return 单元格数据
     * @throws Exception
     */
    public static String getCellData(String sheetName, int rowNum, int colNum) throws Exception {
        readSheet(sheetName);
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            cell.setCellType(CellType.STRING);
//            String cellData = getCellValue(cell);
            String cellData = cell.getStringCellValue();
            return cellData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(sheetName + "中，第 " + rowNum + " 行， 第 " + colNum + " 列，单元格内容获取错误");
        }
    }

    /**
     * 根据Excel中格式的不同读取不同格式的值
     * @param cell 指定的单元格
     * @return
     */
    private static String getCellValue(XSSFCell cell) {
        String strCell = "";
        if (cell.getCellType() == CellType.STRING) {
            strCell = cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            strCell = String.valueOf(cell.getNumericCellValue());
            strCell = strCell.split(".")[0];
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            strCell = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.BLANK) {
            strCell = "";
        } else {
            strCell = "";
        }
        return strCell;
    }

    /**
     * 向指定的文件的指定单元格写入数据
     * @param filePath 文件路径
     * @param sheetName 表名
     * @param rowNum 行号
     * @param colNum 列号
     * @param result 测试结果
     * @throws Exception
     */
    public static void setCellData(String filePath, String sheetName, int rowNum, int colNum,
                                   boolean result) throws Exception {
        try {
            readSheet(sheetName);
            cell = sheet.getRow(rowNum).getCell(colNum);
            if (cell == null) {
                cell = sheet.getRow(rowNum).createCell(colNum);
            }
            cell.setCellValue(result);
            book.write(new FileOutputStream(filePath));
            logger.info("数据写入成功！");
        } catch (Exception e) {
            logger.info("数据写入失败！");
            throw new Exception("数据写入发生错误");
        }
    }

    /**
     * 获取数据表中数据的总行数
     * @param sheetName 表名
     * @return 总行数
     */
    public static int getAllRowNum(String sheetName) throws Exception {
        readSheet(sheetName);
        return sheet.getLastRowNum();
    }


    /**
     * 以Map的形式获取给定的数据表中的所有数据
     * @param dir 文件目录
     * @param file 文件名
     * @param sheetName 表名
     * @return
     * @throws Exception
     */
    public static ArrayList<HashMap<String, String>> getAllDataByMaps(String dir, String file, String sheetName)
        throws Exception {
        ArrayList<HashMap<String, String>> datas = new ArrayList<>();
        try {
            readExcel(dir, file);
            readSheet(sheetName);
            HashMap<String, String> dict;
            XSSFRow titles = sheet.getRow(0);
            if (sheet.getLastRowNum() != 0) {
                // 具有有效数据
                for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                    dict = new HashMap<String, String>();
                    for (int j = 0; j < titles.getLastCellNum(); j++) {
                        dict.put(titles.getCell(j).getStringCellValue(),
                                getCellData(sheetName, i, j));
                    }
                    datas.add(dict);
                }
            } else {
                // 只有标题行
                dict = new HashMap<String, String>();
                for (int j = 0; j < titles.getLastCellNum(); j++) {
                    dict.put(titles.getCell(j).getStringCellValue(), "");
                }
                datas.add(dict);
            }
        } catch (Exception e) {
            logger.info("所有数据获取失败");
            e.printStackTrace();
        }
        book.close();
        return datas;
    }
}
