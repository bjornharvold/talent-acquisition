package com.tps.tpi.cache.impl;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Person;
import com.tps.tpi.model.objects.lite.PersonLite;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.tps.tpi.cache.CacheManager;
import com.tps.tpi.cache.CachedObjectWrapper;
import com.tps.tpi.exception.CacheException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: bjorn
 * Date: Jun 19, 2008
 * Time: 2:35:00 PM
 * Implmentation of caching using oscache (http://opensymphony.com/oscache)
 */
public class OSCacheManager implements CacheManager {
    private final static Log log = LogFactory.getLog(OSCacheManager.class);

    public void init() {
        log.info("OSCacheManager is enabled: " + enabled);
    }

    /**
     * Add object to cache specified by the given key
     *
     * @param key
     * @param value
     * @throws CacheException
     */
    public void putInCache(String[] groups, String key, Object value) throws CacheException {
        if (enabled) {
            if (StringUtils.isBlank(key)) {
                throw new CacheException("key cannot be null");
            }
            if (value == null) {
                throw new CacheException("value cannot be null");
            }

            try {
                key = createKey(key);
                if (log.isTraceEnabled()) {
                    log.trace("Adding object to cache with key: " + key + ", object is of type: " + value.getClass());
                }
                cacheAdministrator.putInCache(key, new CachedObjectWrapper(value), groups);
            } catch (Exception e) {
                cacheAdministrator.cancelUpdate(key);
                throw new CacheException("Could not update cache: " + e.getMessage(), e);
            }
        } else {
            log.info("Caching is disabled");
        }
    }


    public void putInCache(String key, Object value) throws CacheException {
        if (enabled) {
            if (StringUtils.isBlank(key)) {
                throw new CacheException("key cannot be null");
            }
            if (value == null) {
                throw new CacheException("value cannot be null");
            }

            try {
                key = createKey(key);
                if (log.isTraceEnabled()) {
                    log.trace("Adding object to cache with key: " + key + ", object is of type: " + value.getClass());
                }
                cacheAdministrator.putInCache(key, new CachedObjectWrapper(value));
            } catch (Exception e) {
                cacheAdministrator.cancelUpdate(key);
                throw new CacheException("Could not update cache: " + e.getMessage(), e);
            }
        } else {
            log.info("Caching is disabled");
        }
    }

    /**
     * Get object from cache specified by a given key
     *
     * @param key
     * @return
     * @throws CacheException
     */
    public <T> T getFromCache(String key, Class<T> c) throws CacheException {
        T result = null;
        boolean updated = false;

        if (enabled) {
            if (StringUtils.isBlank(key)) {
                throw new CacheException("key cannot be null");
            }

            try {
                key = createKey(key);
                CachedObjectWrapper cow = (CachedObjectWrapper) cacheAdministrator.getFromCache(key, cacheRefreshInSeconds);
                if (cow != null && !cow.getObject().equals(key)) {
                    result = (T) cow.getObject();
                    updated = true;
                }

                if (log.isTraceEnabled()) {
                    if (result != null) {
                        log.trace("Object with key: " + key + " already exists in cache. Returning previously fetched item: " + result);
                    } else {
                        log.trace("Object with key: " + key + " does not exist in cache.");
                    }
                }
            } catch (NeedsRefreshException e) {
                // happens when cache is empty
            } finally {
                if (!updated) {
                    if (log.isTraceEnabled()) {
                        log.trace("There is no object in cache for key: " + key + ". Releasing handle.");
                    }
                    // essential that this is called when no object exists in cache
                    cacheAdministrator.cancelUpdate(key);
                }
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Caching is disabled");
            }
        }

        return result;
    }

    /**
     * Put an arbitrary entity in the cache using generics and return the specified class
     *
     * @param cacheKeyPrefix
     * @param entity
     * @param c
     * @param <T>
     * @return
     * @throws CacheException
     */
    @Override
    public <T> T mapEntityToDtoAndCache(String cacheKeyPrefix, AbstractEntity entity, Class<T> c) throws CacheException {
        T result = null;

        if (entity != null) {

            // construct cache key
            String cacheKey = entity.getId();
            if (StringUtils.isNotBlank(cacheKeyPrefix)) {
                cacheKey = cacheKeyPrefix + cacheKey;
            }

            if (log.isDebugEnabled()) {
                log.debug("Checking to see if entity " + entity.getClass() + " with id: " + cacheKey + " is in cache...");
            }

            // retrieve from cache
            result = getFromCache(cacheKey, c);

            if (result == null) {
                if (log.isTraceEnabled()) {
                    log.debug("Entity " + entity.getClass() + " with id: " + cacheKey + " is NOT in cache.");
                    log.trace("Mapping entity " + entity.getClass() + " to " + c.getName());
                }

                // map to dto
                result = mapper.map(entity, c);

                if (result != null) {

                    if (log.isTraceEnabled()) {
                        log.trace("Converted " + entity.getClass() + " to " + c + " successfully: " + result);
                        log.trace("Adding dto to cache. Cache key: " + cacheKey);
                    }

                    // put dto in cache
                    putInCache(cacheKey, result);
                } else {
                    log.error("Conversion object of type " + entity.getClass() + " is null with id: " + entity.getId());
                }
            }
        } else {
            throw new CacheException("Entity is null");
        }

        return result;
    }

    /**
     * Put an arbitrary list of entities in the cache using generics and return the dto list
     *
     * @param list
     * @param c
     * @return
     * @throws CacheException
     * @throws DomainException
     */
    @Override
    public <T> List<T> mapEntityCollectionToDtoAndCache(String cacheKeyPrefix, List<AbstractEntity> list, Class<T> c) throws CacheException {
        List<T> result = null;

        if (list != null) {
            result = new ArrayList<T>(list.size());

            for (AbstractEntity entity : list) {
                result.add(mapEntityToDtoAndCache(cacheKeyPrefix, entity, c));
            }
        }

        return result;
    }

    /**
     * removes all unwanted characters from the incoming key to be cached
     *
     * @param key
     * @return
     */
    public String createKey(String key) {
        key = key.replace(" ", "_");
        key = key.replace("(", "_");
        key = key.replace(")", "_");
        key = key.replace("+", "_");
        key = key.replace("!", "_");
        key = key.replace("#", "_");
        key = key.replace("$", "_");
        key = key.replace("@", "_");
        key = key.replace("&", "_");
        key = key.replace("*", "_");
        key = key.replace("?", "_");
        key = key.replace(";", "_");

        return key;
    }

    /**
     * Kills object in cache
     *
     * @param key
     * @throws CacheException
     */
    public void invalidateObject(String key) throws CacheException {
        if (enabled) {
            if (StringUtils.isBlank(key)) {
                throw new CacheException("key cannot be null");
            }

            if (log.isTraceEnabled()) {
                log.trace("Removing object with key: " + key);
            }

            cacheAdministrator.removeEntry(key);
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Caching is disabled");
            }
        }
    }

    /**
     * Empties the cache
     *
     * @throws CacheException
     */
    public void flush(String[] groups) throws CacheException {

        for (String group : groups) {
            if (log.isTraceEnabled()) {
                log.trace("Flushing caches for group: " + group);
            }

            cacheAdministrator.flushGroup(group);
        }


    }


    public void flush() throws CacheException {
        cacheAdministrator.flushAll();
    }

    // Spring IoC
    @Autowired
    private GeneralCacheAdministrator cacheAdministrator;

    @Autowired
    private Mapper mapper;

    private Integer cacheRefreshInSeconds;
    private Boolean enabled;

    public void setCacheRefreshInSeconds(Integer cacheRefreshInSeconds) {
        this.cacheRefreshInSeconds = cacheRefreshInSeconds;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
