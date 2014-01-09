package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Region;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:56:50 PM
 * Responsibility:
 */
public interface RegionDao extends GenericDao<Region, String> {
    Region getRegionByCode(String code) throws PersistenceException;
    List<Region> getRegions(String name, Integer index, Integer maxResults) throws PersistenceException;
    List<String> getRegionIds(String name, Integer index, Integer maxResults) throws PersistenceException;
    List<Region> getRegions(List<String> ids) throws PersistenceException;
}