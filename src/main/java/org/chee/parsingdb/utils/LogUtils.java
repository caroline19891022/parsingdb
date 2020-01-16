package org.chee.parsingdb.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * 日志打印工具
 * @author Caroline
 */
public class LogUtils {
    private LogUtils() {
        throw new IllegalStateException("Utility class"); 
    }
    
    public static void printExeptionLog(Logger log, Throwable e) {
        Stream.of(e.getStackTrace()).forEachOrdered(stackTraceElement -> log.log(Level.WARNING, stackTraceElement.toString()));
    }
}
