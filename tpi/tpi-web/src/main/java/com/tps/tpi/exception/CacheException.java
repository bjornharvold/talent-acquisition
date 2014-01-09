package com.tps.tpi.exception;

/**
 * User: Bjorn Harvold
 * Date: Nov 13, 2009
 * Time: 9:41:07 PM
 * Responsibility:
 */
public class CacheException extends Exception {
    public CacheException() {
        super();
    }

    public CacheException(String key) {
        super(key);
    }
    
    public CacheException(String key, Throwable t) {
        super(key, t);
    }
}
