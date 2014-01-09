package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.TitleDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Title;
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
@Repository("titleDao")
public class TitleDaoImpl extends AbstractHibernateDao<Title, String> implements TitleDao {
    @Override
    public Title getTitleByShortName(String shortName) throws PersistenceException {
        Criteria c = getSession().createCriteria(Title.class);
        c.add(Restrictions.eq("shortName", shortName));

        return (Title) c.uniqueResult();
    }

    @Override
    public Title getTitleByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(Title.class);
        c.add(Restrictions.eq("code", code));

        return (Title) c.uniqueResult();
    }

    @Override
    public List<Title> getTitles(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Title.class);

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