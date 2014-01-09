/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold.
 */

package com.tps.tpi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>Title: PageController</p>
 * <p>Description: Dispatches pages.</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String show() throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("Dispatching view: 'index'");
        }

        return "index";
    }

    /**
     * Displays the login box on the index page.
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String showLogin() throws Exception {
        String result = "login.form";

        if (log.isTraceEnabled()) {
            log.trace("Dispatching view: '" + result + "'");
        }

        return result;
    }
}