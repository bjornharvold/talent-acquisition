package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Biography;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 10:06:09 AM
 * Responsibility:
 */
public interface BiographyDao extends GenericDao<Biography, String> {

    Biography getBiography(String personId) throws PersistenceException;
}
