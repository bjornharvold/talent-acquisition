package com.tps.tpi.service;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Dec 11, 2009
 * Time: 11:58:00 PM
 * Responsibility:
 */
public interface LoggingService {
    void trace(String message);
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
}
