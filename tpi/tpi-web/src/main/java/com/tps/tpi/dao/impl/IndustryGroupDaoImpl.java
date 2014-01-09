package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.IndustryGroupDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.IndustryGroup;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 15, 2009
 * Time: 12:51:14 PM
 * Responsibility:
 */
@Repository
public class IndustryGroupDaoImpl extends AbstractHibernateDao<IndustryGroup, String> implements IndustryGroupDao {
    @Override
    public List<IndustryGroup> getIndustryGroups(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(IndustryGroup.class);

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("shortName", name + "%"));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("shortName"));

        return c.list();
    }

    @Override
    public IndustryGroup getIndustryByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(IndustryGroup.class);
        c.add(Restrictions.eq("code", code));

        return (IndustryGroup) c.uniqueResult();
    }
}
