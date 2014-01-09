/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.exception;

/**
 * User: Bjorn Harvold
 * Date: Aug 25, 2009
 * Time: 8:57:43 PM
 * Responsibility:
 */
public class DomainException extends Exception {
    String[] params;

    public DomainException() {
        super();
    }

    public DomainException(String msg) {
        super(msg);
    }

    public DomainException(String msg, Throwable t) {
        super(msg, t);
    }

    public DomainException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public DomainException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
