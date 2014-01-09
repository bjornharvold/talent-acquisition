/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.web.flex;

/**
 * User: Bjorn Harvold
 * Date: Aug 23, 2009
 * Time: 1:58:39 PM
 * Responsibility:
 */
import org.springframework.flex.config.MessageBrokerConfigProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flex.messaging.MessageBroker;
import flex.messaging.services.RemotingService;

public class FlexMessageBrokerConfigProcessor implements MessageBrokerConfigProcessor {
    private final static Logger log = LoggerFactory.getLogger(FlexMessageBrokerConfigProcessor.class);

	public MessageBroker processAfterStartup(MessageBroker broker) {
		RemotingService remotingService =
			(RemotingService) broker.getServiceByType(RemotingService.class.getName());
		if (remotingService.isStarted()) {
			log.info("The Remoting Service has been started with " + remotingService.getDestinations().size() + " Destinations.");
		}
		return broker;
	}

	public MessageBroker processBeforeStartup(MessageBroker broker) {
		return broker;
	}
}
