package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.BiographyDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Biography;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 10:06:34 AM
 * Responsibility:
 */
@Repository("biographyDao")
public class BiographyDaoImpl extends AbstractHibernateDao <Biography, String> implements BiographyDao {
    @Override
    public Biography getBiography(String personId) throws PersistenceException {
        Criteria c = getSession().createCriteria(Biography.class);
        c.createAlias("person" , "p").add(Restrictions.eq("p.id", personId));

        return (Biography) c.uniqueResult();
    }
}
