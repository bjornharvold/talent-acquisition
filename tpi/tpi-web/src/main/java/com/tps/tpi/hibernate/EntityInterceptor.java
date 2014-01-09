/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold.
 */

package com.tps.tpi.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;

import com.tps.tpi.model.objects.entity.AbstractEntity;

/**
 * User: Bjorn Harvold
 * Date: Jan 24, 2007
 * Time: 2:02:25 PM
 */
public class EntityInterceptor extends EmptyInterceptor {
    private final static Logger log = LoggerFactory.getLogger(EntityInterceptor.class);

    public Boolean isTransient(Object entity) {
        Boolean result = null;

        if (entity instanceof AbstractEntity) {
            result = !((AbstractEntity) entity).isSaved();
        }
//
//        if (log.isTraceEnabled()) {
//            log.trace("Is object " + entity.getClass().getSimpleName() + " transient: " + result);
//        }

        return result;
    }

    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof AbstractEntity) ((AbstractEntity) entity).onLoad();
        return false;
    }

    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        boolean result = false;

        if (entity instanceof AbstractEntity) {
            AbstractEntity abo = ((AbstractEntity) entity);
            // abo.onSave();

            for (int i = 0; i < propertyNames.length; i++) {
                if ("lastUpdate".equals(propertyNames[i])) {
                    state[i] = new Date();
                    result = true;
                } else if ("createdDate".equals(propertyNames[i])
                        && abo.getCreatedDate() == null) {
                    state[i] = new Date();
                    result = true;
                } else if ("version".equals(propertyNames[i])
                        && abo.getVersion() == null) {
                    state[i] = 0;
                    result = true;
                }
            }
        }

        return result;
    }

    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
                         Object[] previousState, String[] propertyNames,
                         Type[] types) {
        boolean result = false;

        if (entity instanceof AbstractEntity) {
            AbstractEntity abo = ((AbstractEntity) entity);

            for (int i = 0; i < propertyNames.length; i++) {
                if ("lastUpdate".equals(propertyNames[i])) {
                    currentState[i] = new Date();
                    result = true;
                }
            }
        }

        return result;
    }

}
