package org.chee.parsingdb;

import org.chee.parsingdb.enums.DatabaseEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * ReadAndParse Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/16/2020</pre>
 */
public class ReadAndParseTest {


    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: excelToJavaEntity(String excelPath, String javaPackagePath)
     */
    @Test
    public void testExcelToJavaEntity() throws Exception {
        ReadAndParse.excelToJavaEntity("D:\\workspace\\parsingdb\\src\\test\\java\\org\\chee\\parsingdb\\file\\白条账户批扣数据库设计.xlsx", "D:\\workspace\\parsingdb\\src\\main\\java\\org\\chee\\parsingdb\\data", DatabaseEnum.ORACLE);
    }


    @Test
    public void excelToSql() throws IOException {
        ReadAndParse.excelToSql("D:\\workspace\\parsingdb\\src\\test\\java\\org\\chee\\parsingdb\\file\\白条账户批扣数据库设计.xlsx", "D:\\workspace\\parsingdb\\src\\main\\java\\org\\chee\\parsingdb\\data", DatabaseEnum.MY_SQL);
    }
} 
