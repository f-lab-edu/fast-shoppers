package com.fastshoppers.exception.logger;

import com.fastshoppers.common.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionLogger {

    public void log(LogLevel logLevel, String message, StackTraceElement[] stackTraceElements) {
        StringBuffer logMessage = new StringBuffer(message);
        if (stackTraceElements != null && stackTraceElements.length > 0) {
            StackTraceElement thrower = stackTraceElements[0]; // 몇 번째 Element 호출할지는 추후 개발 되는 depth보고 수정
            String className = thrower.getClassName();
            String methodName = thrower.getMethodName();
            logMessage.append("[Exception Class and Name is : ").append(className).append(".").append(methodName).append("]");
        }

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
