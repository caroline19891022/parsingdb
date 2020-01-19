package org.chee.parsingdb;

import lombok.extern.java.Log;
import org.chee.parsingdb.data.XlsMsg;
import org.chee.parsingdb.enums.DatabaseEnum;

import java.io.IOException;

/**
 * 串连读取与输出流程
 * @author Caroline
 */
@Log
public class ReadAndParse {
    
    public static void excelToJavaEntity(String excelPath, String javaPackagePath, DatabaseEnum databaseType) throws IOException {
        XlsMsg xlsMsg = ReadExcel.getXlsMsg(excelPath);
        if (xlsMsg == null) {
            log.info("excel解析失败");
            return;
        }
        xlsMsg.setDatabaseType(databaseType);
        ParsingJavaEntity.parsingJavaEntity(javaPackagePath, xlsMsg);
    }
    
    public static void excelToSql(String excelPath, String sqlPath, DatabaseEnum databaseType) throws IOException {
        XlsMsg xlsMsg = ReadExcel.getXlsMsg(excelPath);
        if (xlsMsg == null) {
            log.info("excel解析失败");
            return;
        }
        xlsMsg.setDatabaseType(databaseType);
        ParsingSQL.writeSqlFile(sqlPath, xlsMsg);
    }
}
