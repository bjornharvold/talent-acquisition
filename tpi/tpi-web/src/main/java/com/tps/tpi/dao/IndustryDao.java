package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Industry;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 15, 2009
 * Time: 12:50:38 PM
 * Responsibility:
 */
public interface IndustryDao extends GenericDao<Industry, String> {
    List<Industry> getIndustries(String name, Integer index, Integer maxResults) throws PersistenceException;
    Industry getIndustryByCode(String code) throws PersistenceException;
}