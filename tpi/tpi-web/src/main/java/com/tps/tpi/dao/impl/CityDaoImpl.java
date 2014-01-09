package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.CityDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.City;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:46:45 PM
 * Responsibility: Retrieves city entities
 */
@Repository("cityDao")
public class CityDaoImpl extends AbstractHibernateDao<City, String> implements CityDao {

    @Override
    public City getCityByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(City.class);
        c.add(Restrictions.eq("code", code));

        return (City) c.uniqueResult();
    }

    @Override
    public List<City> getCities(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(City.class);

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