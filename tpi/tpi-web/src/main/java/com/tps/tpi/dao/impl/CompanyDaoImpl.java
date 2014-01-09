package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.CompanyDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Company;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 13, 2009
 * Time: 12:04:29 PM
 * Responsibility:
 */
@Repository("companyDao")
public class CompanyDaoImpl extends AbstractHibernateDao<Company, String> implements CompanyDao {
    @Override
    public Company getCompanyByShortName(String shortName) throws PersistenceException {
        Criteria c = getSession().createCriteria(Company.class);
        c.add(Restrictions.eq("shortName", shortName));

        return (Company) c.uniqueResult();
    }

    @Override
    public Company getCompanyByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(Company.class);
        c.add(Restrictions.eq("code", code));

        return (Company) c.uniqueResult();
    }
}
