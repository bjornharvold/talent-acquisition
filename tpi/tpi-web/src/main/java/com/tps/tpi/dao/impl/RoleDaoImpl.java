/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.RoleDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Role;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Aug 25, 2009
 * Time: 7:25:49 PM
 * Responsibility:
 */
@Repository
public class RoleDaoImpl extends AbstractHibernateDao<Role, String> implements RoleDao {
    
    public Role getRoleByStatusCode(String statusCode) throws PersistenceException {
        Criteria c = getSession().createCriteria(Role.class);
        c.add(Restrictions.eq("code", statusCode));

        return (Role) c.uniqueResult();
    }

    public List<Role> searchForRoles(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Role.class);

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("code", name + "%"));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("code"));

        return c.list();
    }

    public Integer searchForRolesCount(String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(Role.class);

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("code", name + "%"));
        }
        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public List<Role> getLastModifiedRoles(Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Role.class);

        if (maxResults != null) {
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.desc("lastUpdate"));

        return c.list();
    }
}
