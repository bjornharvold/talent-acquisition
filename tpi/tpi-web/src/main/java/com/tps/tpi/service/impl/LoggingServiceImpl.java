package com.tps.tpi.service.impl;

import com.tps.tpi.service.LoggingService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Dec 11, 2009
 * Time: 11:58:51 PM
 * Responsibility:
 */
@Service("loggingService")
@RemotingDestination(channels = {"my-amf", "my-secure-amf"})
public class LoggingServiceImpl implements LoggingService {
    private static final Logger log = LoggerFactory.getLogger("flash");

    @Override
    @RemotingInclude
    public void trace(String message) {
        if (StringUtils.isNotBlank(message) && log.isTraceEnabled()) {
            log.trace(message);
        }
    }

    @Override
    @RemotingInclude
    public void debug(String message) {
        if (StringUtils.isNotBlank(message) && log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    @Override
    @RemotingInclude
    public void info(String message) {
        if (StringUtils.isNotBlank(message) && log.isInfoEnabled()) {
            log.info(message);
        }
    }

    @Override
    @RemotingInclude
    public void warn(String message) {
        if (StringUtils.isNotBlank(message) && log.isWarnEnabled()) {
            log.warn(message);
        }
    }

    @Override
    @RemotingInclude
    public void error(String message) {
        if (StringUtils.isNotBlank(message) && log.isErrorEnabled()) {
            log.error(message);
        }
    }
}
