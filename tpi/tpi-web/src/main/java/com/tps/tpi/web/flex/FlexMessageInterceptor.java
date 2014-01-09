/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.web.flex;

import org.springframework.flex.core.MessageInterceptor;
import org.springframework.flex.core.MessageProcessingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import flex.messaging.messages.Message;

/**
 * User: Bjorn Harvold
 * Date: Aug 23, 2009
 * Time: 2:10:29 PM
 * Responsibility:
 */
public class FlexMessageInterceptor implements MessageInterceptor {
    private static final Logger log = LoggerFactory.getLogger(FlexMessageInterceptor.class);

    @Override
    public Message postProcess(MessageProcessingContext ctx, Message request, Message response) {
//        if (log.isTraceEnabled()) {
//            log.trace("AMF channel post-process");
//        }
        return response;
    }

    @Override
    public Message preProcess(MessageProcessingContext ctx, Message msg) {
//        if (log.isTraceEnabled()) {
//            log.trace("AMF channel pre-process");
//        }

        return msg;
    }
}