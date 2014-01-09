/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:18:03 AM
 */
public class DataCreatorException extends Exception {
    String[] params;

    public DataCreatorException() {
        super();
    }

    public DataCreatorException(String msg) {
        super(msg);
    }

    public DataCreatorException(String msg, Throwable t) {
        super(msg, t);
    }

    public DataCreatorException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;
    }

    public DataCreatorException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
