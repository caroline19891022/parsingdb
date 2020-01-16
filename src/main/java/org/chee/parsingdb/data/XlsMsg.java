package org.chee.parsingdb.data;

import lombok.Builder;
import lombok.Data;
import org.chee.parsingdb.enums.DatabaseEnum;

import java.util.List;

@Data
@Builder
public class XlsMsg {
    private Integer sheetCount;
    private String filePath;
    private DatabaseEnum databaseType;
    private List<TableMsg> tableMsgList;
}
