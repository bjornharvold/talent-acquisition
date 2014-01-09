package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.SkilledRoleDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.SkilledRole;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 12, 2009
 * Time: 11:54:46 AM
 * Responsibility:
 */
@Repository("skilledRoleDao")
public class SkilledRoleDaoImpl extends AbstractHibernateDao<SkilledRole, String> implements SkilledRoleDao {
    @Override
    public SkilledRole getProfessionalRoleByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(SkilledRole.class);
        c.add(Restrictions.eq("code", code));

        return (SkilledRole) c.uniqueResult();
    }

    @Override
    public List<SkilledRole> getSkilledRoles(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(SkilledRole.class);

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
}
