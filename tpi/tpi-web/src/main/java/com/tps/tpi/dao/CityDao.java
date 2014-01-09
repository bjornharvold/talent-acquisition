package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.City;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:56:50 PM
 * Responsibility:
 */
public interface CityDao extends GenericDao<City, String> {
    City getCityByCode(String code) throws PersistenceException;
    List<City> getCities(String name, Integer index, Integer maxResults) throws PersistenceException;
}
