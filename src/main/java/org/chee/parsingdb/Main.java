package org.chee.parsingdb;

import org.chee.parsingdb.enums.DatabaseEnum;
import org.chee.parsingdb.enums.TransTypeEnum;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int menuNum = 101;
        TransTypeEnum transType = TransTypeEnum.BOTH;
        String excelPath = "";
        DatabaseEnum databaseType = DatabaseEnum.ORACLE;
        String javaOutPath = "C:\\Users\\Public\\Downloads";
        String sqlOutPath = "C:\\Users\\Public\\Downloads";
        while (true) {
            switch (menuNum) {
                case 101:
                    int mainMenuChoose = showMainMenu();
                    transType = TransTypeEnum.forCode(mainMenuChoose);
                    break;
                case 102:
                    excelPath = showExcelPath();
                    break;
                case 103:
                    databaseType = showDatabaseType();
                    break;
                case 104:
                    javaOutPath = showJavaPath();
                    break;
                case 105:
                    sqlOutPath = showSqlPath();
                    break;
                case 201:
                    try {
                        switch (transType) {
                            case TRANS_SQL:
                                ReadAndParse.excelToSql(excelPath, sqlOutPath, databaseType);
                                break;
                            case TRANS_JAVA:
                                ReadAndParse.excelToJavaEntity(excelPath, javaOutPath, databaseType);
                                break;
                            case BOTH:
                                ReadAndParse.excelToJavaAndSql(excelPath, sqlOutPath, javaOutPath, databaseType);
                                break;
                            default:
                                break;
                        }
                        System.out.println("===========转换成功===========");
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                default:
                    break;
            }
            menuNum = showNowConfig(transType, excelPath, databaseType, javaOutPath, sqlOutPath);
        }
    }

    public static int showMainMenu() {
        System.out.println("===========Excel 数据库转换工具 Main Menu===========");
        System.out.println("请选择转换类型");
        System.out.println("[1] 转成 Java 实体类");
        System.out.println("[2] 转成 SQL 文件");
        System.out.println("[3] 我全都要");
        System.out.println("===========Excel 数据库转换工具 Main Menu===========");
        int result = sc.nextInt();
        sc.nextLine();
        return result;
    }

    public static String showExcelPath() {
        System.out.println("===========Excel 数据库转换工具 输入路径===========");
        System.out.println("请输入 excel 所在路径：");
        return sc.nextLine();
    }

    public static DatabaseEnum showDatabaseType() {
        System.out.println("===========Excel 数据库转换工具 数据库类型===========");
        System.out.println("请选择数据库类型");
        System.out.println("[1] MySQL");
        System.out.println("[2] ORACLE");
        System.out.println("===========Excel 数据库转换工具 数据库类型===========");
        int type = sc.nextInt();
        sc.nextLine();
        if (type == 1) {
            return DatabaseEnum.MY_SQL;
        }
        return DatabaseEnum.ORACLE;
    }

    public static String showJavaPath() {
        System.out.println("===========Excel 数据库转换工具 Java输出路径===========");
        System.out.println("请输入 Java 输出包路径：");
        return sc.nextLine();
    }

    public static String showSqlPath() {
        System.out.println("===========Excel 数据库转换工具 SQL输出路径===========");
        System.out.println("请输入 SQL 文件输出路径：");
        return sc.nextLine();
    }

    public static int showNowConfig(TransTypeEnum transType, String excelPath, DatabaseEnum databaseEnum, String javaOutPath, String sqlOutPath) {
        System.out.println();
        System.out.println("===========Excel 数据库转换工具 当前配置===========");
        System.out.println("[101] 转换类型          " + transType.getDes());
        System.out.println("[102] excel路径         " + excelPath);
        System.out.println("[103] 数据库类型         " + databaseEnum);
        System.out.println("[104] java类输出路径     " + javaOutPath);
        System.out.println("[105] sql文件输出路径    " + sqlOutPath);
        System.out.println("");
        System.out.println("输入数字修改配置");
        System.out.println("[201] 确认，开始转换");
        System.out.println("===========Excel 数据库转换工具 当前配置===========");
        int result = sc.nextInt();
        sc.nextLine();
        return result;
    }
}
