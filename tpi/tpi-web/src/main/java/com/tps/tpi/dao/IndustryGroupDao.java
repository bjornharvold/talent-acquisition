package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.IndustryGroup;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 15, 2009
 * Time: 12:50:38 PM
 * Responsibility:
 */
public interface IndustryGroupDao extends GenericDao<IndustryGroup, String> {
    List<IndustryGroup> getIndustryGroups(String name, Integer index, Integer maxResults) throws PersistenceException;
    IndustryGroup getIndustryByCode(String code) throws PersistenceException;
}
