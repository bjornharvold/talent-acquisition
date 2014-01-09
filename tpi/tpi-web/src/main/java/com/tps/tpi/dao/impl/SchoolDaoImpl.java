package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.SchoolDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.School;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:46:45 PM
 * Responsibility:
 */
@Repository("schoolDao")
public class SchoolDaoImpl extends AbstractHibernateDao<School, String> implements SchoolDao {
    @Override
    public School getSchoolByShortName(String shortName) throws PersistenceException {
        Criteria c = getSession().createCriteria(School.class);
        c.add(Restrictions.eq("shortName", shortName));

        return (School) c.uniqueResult();
    }
}