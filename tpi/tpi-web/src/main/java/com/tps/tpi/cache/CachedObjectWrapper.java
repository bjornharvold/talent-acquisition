package com.tps.tpi.cache;

import java.io.Serializable;
import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Nov 13, 2009
 * Time: 9:43:11 PM
 * Responsibility:
 */
public class CachedObjectWrapper implements Serializable {
    private static final long serialVersionUID = -8634951770322486669L;

    // instantiation time in milliseconds
    private long startOfLife = -1;
    private Object object = null;

    public CachedObjectWrapper(Object object) {
        this.object = object;
        startOfLife = new Date().getTime();
    }

    public Object getObject() {
        return object;
    }
}
