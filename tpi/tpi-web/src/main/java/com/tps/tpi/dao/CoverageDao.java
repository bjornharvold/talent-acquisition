package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Coverage;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:50:53 PM
 * Responsibility:
 */
public interface CoverageDao extends GenericDao<Coverage, String> {
    List<Coverage> getCoverages(String historyId, Integer index, Integer maxResults) throws PersistenceException;
}
