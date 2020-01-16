package org.chee.parsingdb;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.chee.parsingdb.data.TableMsg;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
public class ParsingSQL {
    
    public void parsingMySQL(TableMsg tableMsg) {
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
}
