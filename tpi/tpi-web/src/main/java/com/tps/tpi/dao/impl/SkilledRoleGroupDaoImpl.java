package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.SkilledRoleGroupDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.SkilledRoleGroup;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 12, 2009
 * Time: 11:53:57 AM
 * Responsibility:
 */
@Repository
public class SkilledRoleGroupDaoImpl extends AbstractHibernateDao<SkilledRoleGroup, String> implements SkilledRoleGroupDao {
    @Override
    public SkilledRoleGroup getProfessionalRoleGroupByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(SkilledRoleGroup.class);
        c.add(Restrictions.eq("code", code));

        return (SkilledRoleGroup) c.uniqueResult();
    }

    @Override
    public List<SkilledRoleGroup> getSkilledRoleGroups(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(SkilledRoleGroup.class);

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
