package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.CompanyIndustryDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.CompanyIndustry;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Jan 2, 2010
 * Time: 2:34:14 AM
 * Responsibility:
 */
@Repository("companyIndustryDao")
public class CompanyIndustryDaoImpl extends AbstractHibernateDao<CompanyIndustry, String> implements CompanyIndustryDao {
    @Override
    public CompanyIndustry getCompanyIndustryByCode(String companyId, String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(CompanyIndustry.class);
        c.createAlias("company", "co").add(Restrictions.eq("co.id", companyId));
        c.createAlias("industry", "ind").add(Restrictions.eq("ind.code", code));

        return (CompanyIndustry) c.uniqueResult();
    }
}
