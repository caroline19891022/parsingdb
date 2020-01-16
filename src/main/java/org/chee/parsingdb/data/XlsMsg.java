package org.chee.parsingdb.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class XlsMsg {
    private Integer sheetCount;
    private String filePath;
    private List<TableMsg> tableMsgList;
}
