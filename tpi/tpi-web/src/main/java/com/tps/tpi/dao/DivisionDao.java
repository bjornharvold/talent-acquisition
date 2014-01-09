package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Division;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 15, 2009
 * Time: 1:47:23 PM
 * Responsibility:
 */
public interface DivisionDao extends GenericDao<Division, String> {
    List<Division> getDivisions(String companyId, String name, Integer index, Integer maxResults) throws PersistenceException;
    Division getDivisionByName(String companyId, String name) throws PersistenceException;
}
