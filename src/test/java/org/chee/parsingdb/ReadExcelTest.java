package org.chee.parsingdb;

import lombok.extern.java.Log;
import org.chee.parsingdb.data.XlsMsg;
import org.chee.parsingdb.enums.DatabaseEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * ReadExcel Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/16/2020</pre>
 */
@Log
public class ReadExcelTest {


    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getXlsMsg(String excelPath)
     */
    @Test
    public void testGetXlsMsg() throws Exception {
        XlsMsg xlsMsg = ReadExcel.getXlsMsg("D:\\workspace\\parsingdb\\src\\test\\java\\org\\chee\\parsingdb\\file\\白条账户批扣数据库设计.xlsx");
        log.info(xlsMsg.toString());
    }

    /**
     * Method: getWorkbook(InputStream inputStream, String fileType)
     */
    @Test
    public void testGetWorkbook() throws Exception {
    }


} 
