package org.chee.parsingdb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum TransTypeEnum {
    TRANS_JAVA(1, "转为Java实体类"),
    TRANS_SQL(2, "转为SQL文件"),
    BOTH(3, "我全都要");
    
    private Integer code;
    private String des;
    
    public static TransTypeEnum forCode(int code) {
        return Stream.of(TransTypeEnum.values()).filter(type -> code == type.getCode()).findAny().orElse(TransTypeEnum.BOTH);
    }
}
