package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Title;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:56:50 PM
 * Responsibility:
 */
public interface TitleDao extends GenericDao<Title, String> {
    Title getTitleByShortName(String shortName) throws PersistenceException;
    Title getTitleByCode(String code) throws PersistenceException;
    List<Title> getTitles(String name, Integer index, Integer maxResults) throws PersistenceException;
}