/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold.
 */

package com.tps.tpi.web;

import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * User: Bjorn Harvold
 * Date: Aug 20, 2009
 * Time: 2:05:03 PM
 * Responsibility:
 */
@Component("webInitializer")
public class WebInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
