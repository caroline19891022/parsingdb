package org.chee.parsingdb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * MySQL与java类型映射枚举
 */
@AllArgsConstructor
@Getter
public enum MySqlJavaTypeEnum {
    /**
     * bigint
     */
    LONG("bigint", "Long"),
    INT("int", "Integer"),
    VARCHAR("varchar", "String"),
    CHAR("char", "String"),
    FLOAT("float", "BigDecimal"),
    DOUBLE("double", "BigDecimal"),
    DECIMAL("decimal", "BigDecimal"),
    DATE("date", "Date"),
    TIMESTAMP("timestamp", "Date"),
    UNKNOWN("", "//todo unknown mysql type");
    
    
    private String mySqlType;
    private String javaType;

    /**
     * 根据mysql类型获取java类型名称
     * @param mySqlType
     * @return
     */
    public static String getJavaType(String mySqlType) {
        String lowCaseMySqlType = mySqlType.toLowerCase();
        return Stream.of(MySqlJavaTypeEnum.values()).filter(typeEnum -> lowCaseMySqlType.contains(typeEnum.getMySqlType()))
                .findFirst().orElse(UNKNOWN).getJavaType();
    }
    
}