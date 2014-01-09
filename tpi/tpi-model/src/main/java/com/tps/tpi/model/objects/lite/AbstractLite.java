/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.model.objects.lite;

/**
 * User: Bjorn Harvold
 * Date: Feb 5, 2007
 * Time: 1:06:46 AM
 */
public abstract class AbstractLite {
    private String id = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id='").append(id).append('\'');
        return sb.toString();
    }
}