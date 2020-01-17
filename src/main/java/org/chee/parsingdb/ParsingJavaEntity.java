package org.chee.parsingdb;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.chee.parsingdb.data.FileLine;
import org.chee.parsingdb.data.TableMsg;
import org.chee.parsingdb.data.XlsMsg;
import org.chee.parsingdb.enums.DatabaseEnum;
import org.chee.parsingdb.enums.MySqlJavaTypeEnum;
import org.chee.parsingdb.enums.OracleJavaTypeEnum;
import org.chee.parsingdb.utils.LogUtils;
import org.chee.parsingdb.utils.MyStringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 生成java类
 * @author Caroline
 */
@Log
public class ParsingJavaEntity {

    private static final String FILE_PACKAGE_PREFIX = "src" + File.separator + "main" + File.separator + "java";
    private static final String FOUR_SPACE = "    ";

    public static void parsingJavaEntity(String packagePath, XlsMsg xlsMsg) throws IOException {
        log.info("开始生成java实体类，excel信息：" + xlsMsg.getFilePath());
        for (TableMsg tableMsg : xlsMsg.getTableMsgList()) {
            parsingJavaEntity(packagePath, tableMsg, xlsMsg.getDatabaseType());
        }
    }
    
    
    public static void parsingJavaEntity(String packagePath, TableMsg tableMsg, DatabaseEnum databaseType) throws IOException {
        if (!packagePath.contains(FILE_PACKAGE_PREFIX)) {
            log.info("包名校验失败：" + packagePath);
            return;
        }

        File file = new File(packagePath, MyStringUtils.underLineToClassName(tableMsg.getTableCode()) + ".java");
        int i = 0;
        while (file.exists()) {
            i++;
            file = new File(packagePath, MyStringUtils.underLineToClassName(tableMsg.getTableCode()) + i + ".java");
        }
        file.createNewFile();

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            List<String> outStringList = outString(packagePath, tableMsg, databaseType);
            for (String str : outStringList) {
                out.write(str);
                out.newLine();
            }
        } catch (Exception e) {
            LogUtils.printExeptionLog(log, e);
        }
    }

    public static List<String> outString(String packagePath, TableMsg tableMsg, DatabaseEnum databaseType) {
        List<String> result = new ArrayList<>();
        
        // package信息
        int packPrefixIndex = packagePath.indexOf(FILE_PACKAGE_PREFIX);
        String packageName = packagePath.substring(packPrefixIndex + FILE_PACKAGE_PREFIX.length() + 1);
        packageName = packageName.replace(File.separator, ".");
        if (packageName.endsWith(".")) {
            packageName = packageName.substring(0, packageName.length() - 1);
        }
        result.add("package " + packageName + ";");

        result.add("");
        result.add("import javax.persistence.Entity;");
        result.add("import javax.persistence.Id;");
        result.add("import javax.persistence.Table;");
        result.add("import lombok.Data;");
        result.add("");
        result.add("/**");
        result.add(" * " + tableMsg.getTableName());
        result.add(" */");
        result.add("@Data");
        result.add("@Entity");
        result.add("@Table(name = \"" + tableMsg.getTableCode() + "\")");
        result.add("public class " + MyStringUtils.underLineToClassName(tableMsg.getTableCode()) + " {");
        for (FileLine line : tableMsg.getFileLineList()) {
            result.add(FOUR_SPACE + "/**");
            result.add(FOUR_SPACE + " * " + line.getName());
            if (StringUtils.isNotEmpty(line.getComment())) {
                Stream.of(line.getComment().split("\\n")).forEach(str -> result.add(FOUR_SPACE + " * " + str));
            }
            result.add(FOUR_SPACE + " */");
            if (Boolean.TRUE.equals(line.getIsPrimaryKey())) {
                result.add(FOUR_SPACE + "@Id");
            }
            switch (databaseType) {
                case MY_SQL:
                    result.add(FOUR_SPACE + "private " + MySqlJavaTypeEnum.getJavaType(line.getDataType()) + " " + MyStringUtils.underLineToCamel(line.getCode()) + ";");
                    break;
                case ORACLE:
                    result.add(FOUR_SPACE + "private " + OracleJavaTypeEnum.getJavaType(line.getDataType()) + " " + MyStringUtils.underLineToCamel(line.getCode()) + ";");
                    break;
                default:
                    break;
            }
            result.add("");
        }
        result.add("}");
        return result;
    }
}
