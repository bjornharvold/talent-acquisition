package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.DivisionDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Division;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 15, 2009
 * Time: 1:47:55 PM
 * Responsibility:
 */
@Repository
public class DivisionDaoImpl extends AbstractHibernateDao<Division, String> implements DivisionDao {
    @Override
    public List<Division> getDivisions(String companyId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Division.class);

        c.createAlias("company", "c").add(Restrictions.eq("c.id", companyId));

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
    public Division getDivisionByName(String companyId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(Division.class);
        c.add(Restrictions.eq("shortName", name));
        c.createAlias("company", "c").add(Restrictions.eq("c.id", companyId));

        return (Division) c.uniqueResult();
    }
}
