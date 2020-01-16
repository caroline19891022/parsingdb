package org.chee.parsingdb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

/**
 * Oracle与java类型映射枚举
 */
@AllArgsConstructor
@Getter
public enum OracleJavaTypeEnum {
    /**
     * varchar2
     */
    VARCHAR2(   "varchar2",     "String"),
    CHAR(       "char",         "String"),
    LONG(       "long",         "Long"),
    NUMBER(     "number",       "BigDecimal"),
    DATE(       "date",         "Date"),
    TIMESTAMP(  "timestamp",    "Date"),
    UNKNOWN(    "",             "//todo unknown oracle type");


    private String oracleType;
    private String javaType;

    /**
     * 根据mysql类型获取java类型名称
     * @param oracleType
     * @return
     */
    public static String getJavaType(String oracleType) {
        String lowCaseOracleType = oracleType.toLowerCase();
        if (lowCaseOracleType.contains(NUMBER.getOracleType())) {
            if (lowCaseOracleType.contains(",")) {
                String trim = StringUtils.trim(lowCaseOracleType.substring(lowCaseOracleType.indexOf(",") + 1, lowCaseOracleType.length() - 1));
                Integer floatCount = Integer.valueOf(trim);
                if (floatCount > 0) {
                    return NUMBER.getJavaType();
                }
            }
            return "Integer";
        }
        return Stream.of(OracleJavaTypeEnum.values()).filter(typeEnum -> lowCaseOracleType.contains(typeEnum.getOracleType()))
                .findFirst().orElse(UNKNOWN).getJavaType();
    }
}
