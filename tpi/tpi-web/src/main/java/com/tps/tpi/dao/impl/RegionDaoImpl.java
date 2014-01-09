package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.RegionDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Person;
import com.tps.tpi.model.objects.entity.Region;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:46:45 PM
 * Responsibility:
 */
@Repository("regionDao")
public class RegionDaoImpl extends AbstractHibernateDao<Region, String> implements RegionDao {
    @Override
    public Region getRegionByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(Region.class);
        c.add(Restrictions.eq("code", code));

        return (Region) c.uniqueResult();
    }

    @Override
    public List<Region> getRegions(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Region.class);

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
    public List<String> getRegionIds(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Region.class);

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("shortName", name + "%"));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("shortName"));

        c.setProjection(Projections.property("id"));

        return c.list();
    }

    @Override
    public List<Region> getRegions(List<String> ids) throws PersistenceException {
        Criteria c = getSession().createCriteria(Region.class);

        Disjunction d =  Restrictions.disjunction();
        for (String id : ids) {
            d.add(Restrictions.eq("id", id));
        }

        c.add(d);

        return c.list();
    }
}