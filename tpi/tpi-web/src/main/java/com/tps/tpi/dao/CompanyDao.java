package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Company;

/**
 * User: Bjorn Harvold
 * Date: Oct 13, 2009
 * Time: 12:03:46 PM
 * Responsibility:
 */
public interface CompanyDao extends GenericDao<Company, String> {
    Company getCompanyByShortName(String shortName) throws PersistenceException;
    Company getCompanyByCode(String code) throws PersistenceException;
}
