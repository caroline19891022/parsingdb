package org.chee.parsingdb.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyStringUtils {

    /**
     * 下划线转驼峰
     * @param underLine
     * @return
     */
    public static String underLineToCamel(String underLine) {
        if (StringUtils.isEmpty(underLine)) {
            return "";
        }
        underLine = StringUtils.lowerCase(underLine);
        char[] chars = underLine.toCharArray();
        List<Character> resultList = new ArrayList<>();
        boolean isAfterUnderScore = false;
        for (char aChar : chars) {
            if (Objects.equals(aChar, '_')) {
                isAfterUnderScore = true;
            } else {
                if (isAfterUnderScore) {
                    resultList.add(Character.toUpperCase(aChar));
                } else {
                    resultList.add(aChar);
                }
                isAfterUnderScore = false;
            }
        }
        StringBuilder sb = new StringBuilder();
        resultList.forEach(sb::append);
        return sb.toString();
    }
    
    public static String underLineToClassName(String underLine) {
        String result = underLineToCamel(underLine);
        if (StringUtils.isNotEmpty(result)) {
            return result.substring(0, 1).toUpperCase() + result.substring(1);
        }
        return result;
    }
}
