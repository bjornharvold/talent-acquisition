package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.CountryDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Country;
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
 * Responsibility:
 */
@Repository
public class CountryDaoImpl extends AbstractHibernateDao<Country, String> implements CountryDao {
    @Override
    public Country getCountryByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(Country.class);
        c.add(Restrictions.eq("code", code));

        return (Country) c.uniqueResult();
    }

    @Override
    public List<Country> getCountries(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Country.class);

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