package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.CompanyIndustry;

/**
 * User: Bjorn Harvold
 * Date: Jan 2, 2010
 * Time: 2:33:32 AM
 * Responsibility:
 */
public interface CompanyIndustryDao extends GenericDao<CompanyIndustry, String> {
    CompanyIndustry getCompanyIndustryByCode(String companyId, String code) throws PersistenceException;
}
