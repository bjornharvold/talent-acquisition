package com.tps.tpi.cache;

import com.tps.tpi.exception.CacheException;
import com.tps.tpi.model.objects.entity.AbstractEntity;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Nov 13, 2009
 * Time: 9:40:04 PM
 * Responsibility:
 */
public interface CacheManager {
    void putInCache(String[] groups, String key, Object value) throws CacheException;
    void putInCache(String key, Object value) throws CacheException;
    <T> T mapEntityToDtoAndCache(String cacheKeyPrefix, AbstractEntity obj, Class<T> c) throws CacheException;
    <T> List<T> mapEntityCollectionToDtoAndCache(String cacheKeyPrefix, List<AbstractEntity> list, Class<T> c) throws CacheException;
	<T> T getFromCache(String key, Class<T> c) throws CacheException;
    void flush(String[] groups) throws CacheException;
    void flush() throws CacheException;
	void invalidateObject(String key) throws CacheException;
}
