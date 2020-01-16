package org.chee.parsingdb;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.chee.parsingdb.data.FileLine;
import org.chee.parsingdb.data.TableMsg;
import org.chee.parsingdb.data.XlsMsg;
import org.chee.parsingdb.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Log
public class ReadExcel {
    
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    
    public static XlsMsg getXlsMsg(String excelPath) throws IOException {
        log.info("解析excel，路径为：" + excelPath);
        if (StringUtils.isEmpty(excelPath)) {
            return null;
        }
        // 读取excel信息
        File file = new File(excelPath);
        if (!file.exists()) {
            log.info("excel文件不存在");
            return null;
        }
        
        // 解析得到workbook对象
        Workbook workbook = null;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            workbook = getWorkbook(inputStream, file.getName().substring(file.getName().indexOf('.') + 1));
        } catch (Exception e) {
            LogUtils.printExeptionLog(log, e);
            return null;
        }
        if (workbook == null) {
            log.info("解析workbook对象失败");
            return null;
        }
        
        int numberOfSheets = workbook.getNumberOfSheets();
        XlsMsg xlsMsg = XlsMsg.builder().filePath(excelPath).sheetCount(numberOfSheets).build();
        
        List<TableMsg> tableMsgList = new ArrayList<>();
        for (int i = 0; i < numberOfSheets; i++) {
            log.info("开始解析sheet[" + i + "]");
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet == null) {
                break;
            }
            tableMsgList.add(getTableMsg(sheet));
            log.info("sheet[" + sheet.getSheetName() + "]解析完毕");
        }
        xlsMsg.setTableMsgList(tableMsgList);
        return xlsMsg;
    }

    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }
    
    public static TableMsg getTableMsg(Sheet sheet) {
        String tableName = sheet.getRow(0).getCell(0).getStringCellValue();
        String tableCode = sheet.getRow(1).getCell(2).getStringCellValue();
        TableMsg tableMsg = TableMsg.builder().tableName(tableName).tableCode(tableCode).build();
        List<FileLine> fileLineList = new ArrayList<>();
        int rowNum = 4;
        while (rowNum <= sheet.getLastRowNum() && sheet.getRow(rowNum) != null && StringUtils.isNotEmpty(getCellString(sheet.getRow(rowNum).getCell(1)))) {
            Row row = sheet.getRow(rowNum);
            String code = getCellString(row.getCell(1));
            String name = getCellString(row.getCell(2));
            String defaultValue = getCellString(row.getCell(5));
            String comment = getCellString(row.getCell(6));
            String primaryKey = getCellString(row.getCell(7));
            String dataType = getCellString(row.getCell(8));
            String canNull = getCellString(row.getCell(9));
            
            FileLine fileLine = FileLine.builder().code(code).name(name).defaultValue(defaultValue).comment(comment)
                    .isPrimaryKey(getIsTrue(primaryKey)).dataType(dataType).canBeNull(getIsTrue(canNull)).build();
            log.info("解析完成列【" + fileLine + "】");
            fileLineList.add(fileLine);
            rowNum++;
        }
        tableMsg.setFileLineList(fileLineList);
        return tableMsg;
    }
    
    public static boolean getIsTrue(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        value = value.replace(" ", "");
        return "Y".equals(value) || "是".equals(value);
        
    }
    
    public static String getCellString(Cell cell) {
        if (cell == null) {
            return null;
        }
        return StringUtils.trimToNull(cell.getStringCellValue());
    }
    
}
