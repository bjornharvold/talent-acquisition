/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.exception;

public class PersistenceException extends Exception {
    String[] params;

    public PersistenceException() {
        super();
    }

    public PersistenceException(String msg) {
        super(msg);
    }

    public PersistenceException(String msg, Throwable t) {
        super(msg, t);
    }

    public PersistenceException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public PersistenceException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
