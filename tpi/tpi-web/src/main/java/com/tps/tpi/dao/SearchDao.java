package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Search;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 4:05:47 PM
 * Responsibility:
 */
public interface SearchDao extends GenericDao<Search, String> {
    void purgeSearchIndex(Class aClass) throws PersistenceException;
    void optimize();
    Integer searchCount(Search search) throws PersistenceException;
    Search searchEntities(Search search, Integer index, Integer maxResults) throws PersistenceException;
    Search searchIds(Search search, Integer index, Integer maxResults) throws PersistenceException;
}
