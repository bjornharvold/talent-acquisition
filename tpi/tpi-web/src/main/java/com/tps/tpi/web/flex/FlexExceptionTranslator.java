/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.web.flex;

import org.springframework.flex.core.ExceptionTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import flex.messaging.MessageException;

/**
 * User: Bjorn Harvold
 * Date: Aug 23, 2009
 * Time: 2:06:57 PM
 * Responsibility:
 */
public class FlexExceptionTranslator implements ExceptionTranslator {
    private final static Logger log = LoggerFactory.getLogger(FlexExceptionTranslator.class);

    @Override
    public boolean handles(Class<?> aClass) {
        return true;
    }

    @Override
    public MessageException translate(Throwable t) {
        return new MessageException(t.getMessage(), t);
    }
}
