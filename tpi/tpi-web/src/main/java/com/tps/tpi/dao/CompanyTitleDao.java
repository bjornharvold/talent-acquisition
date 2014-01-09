package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.CompanyTitle;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:56:50 PM
 * Responsibility:
 */
public interface CompanyTitleDao extends GenericDao<CompanyTitle, String> {
    CompanyTitle getCompanyTitleByCode(String companyId, String code) throws PersistenceException;
}