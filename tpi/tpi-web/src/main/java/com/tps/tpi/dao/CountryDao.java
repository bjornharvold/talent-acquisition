package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Country;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:56:50 PM
 * Responsibility:
 */
public interface CountryDao extends GenericDao<Country, String> {
    Country getCountryByCode(String code) throws PersistenceException;
    List<Country> getCountries(String name, Integer index, Integer maxResults) throws PersistenceException;
}