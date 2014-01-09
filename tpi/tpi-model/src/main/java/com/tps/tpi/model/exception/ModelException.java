/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.model.exception;

public class ModelException extends Exception {
    String[] params;

    public ModelException() {
        super();
    }

    public ModelException(String msg) {
        super(msg);
    }

    public ModelException(String msg, Throwable t) {
        super(msg, t);
    }

    public ModelException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public ModelException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}