package org.chee.parsingdb.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileLine {
    private String name;
    private String code;
    private String defaultValue;
    private Boolean isPrimaryKey;
    private String dataType;
    private Boolean canBeNull;
    private String comment;
}
