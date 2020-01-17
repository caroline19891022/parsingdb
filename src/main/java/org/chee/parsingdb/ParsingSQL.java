package org.chee.parsingdb;

import lombok.extern.java.Log;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.chee.parsingdb.data.FileLine;
import org.chee.parsingdb.data.TableMsg;
import org.chee.parsingdb.data.XlsMsg;
import org.chee.parsingdb.enums.DatabaseEnum;
import org.chee.parsingdb.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
public class ParsingSQL {
    
    private static final String TWO_SPACE = "  ";
    
    public static void parsingMySQL(TableMsg tableMsg) {
        String createTableSql = tableMsg.getFileLineList().stream().map(fileLine -> {
            String line = fileLine.getCode() + " " + fileLine.getDataType();
            if (StringUtils.isNotEmpty(fileLine.getDefaultValue())) {
                line += " default " + fileLine.getDefaultValue();
            }
            if (Objects.equals(fileLine.getIsPrimaryKey(), true)) {
                line += " primary key ";
            } else if (Boolean.FALSE.equals(fileLine.getCanBeNull())) {
                line += " not null ";
            }
            return line;
        }).collect(Collectors.joining(",\n", "create table " + tableMsg.getTableCode() + "(", ");"));
        log.info("\n" + createTableSql);

        String commentSql = tableMsg.getFileLineList().stream().map(fileLine ->
                "alter table " + tableMsg.getTableCode() + " modify " + fileLine.getCode()
                        + " " + fileLine.getDataType() + " comment '" + fileLine.getName() + " " 
                        + Optional.ofNullable(fileLine.getComment()).orElse("") + "';")
                .collect(Collectors.joining("\n"));
        log.info("\n" + commentSql);
    }
    
    public static List<String> parsingOracle(TableMsg tableMsg) {
        List<String> result = new ArrayList<>();
        result.add("-- auto create table" + tableMsg.getTableCode().toUpperCase());
        result.add("CREATE TABLE " + tableMsg.getTableCode().toUpperCase());
        result.add("(");
        for (FileLine fileLine : tableMsg.getFileLineList()) {
            String lineStr = StringUtils.upperCase(fileLine.getCode());
            lineStr += " ";
            lineStr += StringUtils.upperCase(fileLine.getDataType());
            if (StringUtils.isNotBlank(fileLine.getDefaultValue())) {
                lineStr += " DEFAULT" + fileLine.getDefaultValue();
            }
            if (Boolean.FALSE.equals(fileLine.getCanBeNull())) {
                lineStr += " not null";
            }
            if (Boolean.TRUE.equals(fileLine.getIsPrimaryKey())) {
                lineStr += " primary key";
            }
            lineStr += ",";
            result.add(TWO_SPACE + lineStr);
        }
        result.add(");");
        result.add("");
        
        // 注释部分
        for (FileLine fileLine : tableMsg.getFileLineList()) {
            String commentStr = "comment on column " + tableMsg.getTableCode().toUpperCase() + ".";
            commentStr += fileLine.getCode().toUpperCase();
            commentStr += " is '";
            commentStr += fileLine.getName();
            if (StringUtils.isNotBlank(fileLine.getComment())) {
                commentStr += " " + fileLine.getComment();
            }
            commentStr += "';";
            result.add(commentStr);
        }
        result.add("");
        
        return result;
    }
    
    public static void writeSqlFile(String sqlFilePath, XlsMsg xlsMsg) throws IOException {
        String xlsFileName = xlsMsg.getFilePath().substring(xlsMsg.getFilePath().lastIndexOf(File.pathSeparator + 1));
        File file = new File(sqlFilePath, xlsFileName + ".sql");
        int fileNum = 0;
        while (file.exists()) {
            file = new File(sqlFilePath, xlsFileName + ++fileNum + ".sql");
        }
        file.createNewFile();
        DatabaseEnum databaseType = xlsMsg.getDatabaseType();
        List<String> printList = new ArrayList<>();
        switch (databaseType) {
            case ORACLE:
                printList = xlsMsg.getTableMsgList().stream().flatMap(tableMsg -> parsingOracle(tableMsg).stream()).collect(Collectors.toList());
                break;
            case MY_SQL:
                //todo
                break;
            default:
                return;
        }
        if (CollectionUtils.isEmpty(printList)) {
            return;
        }
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            for (String str : printList) {
                out.write(str);
                out.newLine();
            }
        } catch (Exception e) {
            LogUtils.printExeptionLog(log, e);
        }
    }
}
