/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.UserRoleDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.UserRole;
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
 * Time: 7:20:51 PM
 * Responsibility:
 */
@Repository
public class UserRoleDaoImpl extends AbstractHibernateDao<UserRole, String> implements UserRoleDao {

        public List<UserRole> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(UserRole.class);

        c = c.createAlias("user", "u");
        c = c.createAlias("role", "r");
        c = c.add(Restrictions.eq("u.id", id));

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("r.name", name + "%"));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("r.name"));

        return c.list();
    }

    public Integer searchForUserRolesCount(String id, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(UserRole.class);

        c = c.createAlias("user", "u");
        c = c.createAlias("role", "r");
        c = c.add(Restrictions.eq("u.id", id));

        if (StringUtils.isNotBlank(name)) {
            c = c.add(Restrictions.ilike("r.name", name + "%"));
        }

        c = c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public UserRole getUserRoleByUserIdAndRoleId(String userId, String roleId) {
        Criteria c = getSession().createCriteria(UserRole.class);

        c = c.createAlias("user", "u");
        c = c.createAlias("role", "r");
        c = c.add(Restrictions.eq("u.id", userId));
        c = c.add(Restrictions.eq("r.id", roleId));

        return (UserRole) c.uniqueResult();
    }

    public List<UserRole> getUserRolesByRoleId(String roleId) throws PersistenceException {
        Criteria c = getSession().createCriteria(UserRole.class);

        c = c.createAlias("role", "r");
        c = c.add(Restrictions.eq("r.id", roleId));

        return c.list();
    }
}
