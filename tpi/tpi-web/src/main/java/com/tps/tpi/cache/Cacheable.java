package com.tps.tpi.cache;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * User: Bjorn Harvold
 * Date: Dec 18, 2009
 * Time: 10:21:04 PM
 * Responsibility:
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Cacheable {
    String cacheStore();
}
