package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.History;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 10:26:37 AM
 * Responsibility:
 */
public interface HistoryDao extends GenericDao<History, String> {
    History getHistory(String personId) throws PersistenceException;
}
