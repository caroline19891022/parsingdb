package org.chee.parsingdb.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TableMsg {
    /**
     * 
     */
    private Integer sheetNum;
    private String tableName;
    private String tableCode;
    private List<FileLine> fileLineList;
}
