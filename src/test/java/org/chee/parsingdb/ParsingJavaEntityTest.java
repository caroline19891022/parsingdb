package org.chee.parsingdb;

import org.chee.parsingdb.data.FileLine;
import org.chee.parsingdb.data.TableMsg;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * ParsingJavaEntity Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/16/2020</pre>
 */
public class ParsingJavaEntityTest {

    private ParsingJavaEntity parsingJavaEntity;

    @Before
    public void before() throws Exception {
        parsingJavaEntity = new ParsingJavaEntity();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: parsingJavaEntity(String packagePath, TableMsg tableMsg)
     */
    @Test
    public void testParsingJavaEntity() throws Exception {
        String packagePath = "D:\\workspace\\parsingdb\\src\\main\\java\\org\\chee\\parsingdb\\data";
        FileLine line1 = FileLine.builder().code("id").name("id").isPrimaryKey(true).dataType("varchar(36)").build();
        FileLine line2 = FileLine.builder().code("rce_flag").name("是否合同收款").dataType("varchar(10)").canBeNull(false).comment("Y：合同收款\nN:无合同收款").build();
        FileLine line3 = FileLine.builder().code("contract_id").name("合同编号").dataType("varchar(36)").defaultValue("0").canBeNull(true).build();
        List<FileLine> fileLineList = new ArrayList<>();
        fileLineList.add(line1);
        fileLineList.add(line2);
        fileLineList.add(line3);
        TableMsg tableMsg = TableMsg.builder().tableCode("receivable_info").tableName("待收款记录").fileLineList(fileLineList).build();
        
        parsingJavaEntity.parsingJavaEntity(packagePath, tableMsg);
    }

    /**
     * Method: outString(String packagePath, TableMsg tableMsg)
     */
    @Test
    public void testOutString() throws Exception {
    }


} 
