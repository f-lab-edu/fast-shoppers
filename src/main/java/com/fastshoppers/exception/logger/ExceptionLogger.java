package com.fastshoppers.exception.logger;

import com.fastshoppers.common.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionLogger {

    public void log(LogLevel logLevel, String message, StackTraceElement[] stackTraceElements) {
        StringBuffer logMessage = new StringBuffer();

        if (message != null && !message.isEmpty()) {
            logMessage.append("[Custom Message: ").append(message).append("]\n");
        }

        if (stackTraceElements != null && stackTraceElements.length > 0) {
            // StackTraceElement 받아와서 한 줄씩 돌면서 로그 찍기
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                String fileName = stackTraceElement.getFileName();
                int lineNumber = stackTraceElement.getLineNumber();
                logMessage.append("[Stack Trace Info : ")
                        .append(className)
                        .append(".")
                        .append(methodName)
                        .append("]")
                        .append(" at ")
                        .append(fileName)
                        .append(", LineNumber : ")
                        .append(lineNumber)
                        .append("\n");
            }
        }

        // logLevel 동적으로 받아 상이하게 처리
        switch (logLevel) {
            case DEBUG:
                log.debug(logMessage.toString());
                break;
            case INFO:
                log.info(logMessage.toString());
                break;
            case WARN:
                log.warn(logMessage.toString());
                break;
            case ERROR:
                log.error(logMessage.toString());
                break;
            default:
        }
    }


}
