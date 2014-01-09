/*
 * Copyright (c) 2007, Health XCEL Inc. All Rights Reserved.
 */

package com.tps.tpi.email;

/**
 * User: Bjorn Harvold
 * Date: May 5, 2006
 * Time: 11:13:52 AM
 */
public class MailServiceException extends Exception {
    String[] params;

    public MailServiceException() {
        super();
    }

    public MailServiceException(String msg) {
        super(msg);
    }

    public MailServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public MailServiceException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;

    }

    public MailServiceException(String key, String... params) {
        super(key);
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }
}
