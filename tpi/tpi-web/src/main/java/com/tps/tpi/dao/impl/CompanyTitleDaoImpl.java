package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.CompanyTitleDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.CompanyTitle;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:46:45 PM
 * Responsibility:
 */
@Repository("companyTitleDao")
public class CompanyTitleDaoImpl extends AbstractHibernateDao<CompanyTitle, String> implements CompanyTitleDao {
    @Override
    public CompanyTitle getCompanyTitleByCode(String companyId, String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(CompanyTitle.class);
        c.createAlias("title", "t").add(Restrictions.eq("t.code", code));
        c.createAlias("company", "c").add(Restrictions.eq("c.id", companyId));

        return (CompanyTitle) c.uniqueResult();
    }
}