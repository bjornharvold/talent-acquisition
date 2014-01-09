package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.EducationDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Education;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 10:48:52 AM
 * Responsibility:
 */
@Repository("educationDao")
public class EducationDaoImpl extends AbstractHibernateDao<Education, String> implements EducationDao {
    @Override
    public Education getEducation(String personId) throws PersistenceException {
        Criteria c = getSession().createCriteria(Education.class);
        c.createAlias("person" , "p").add(Restrictions.eq("p.id", personId));

        return (Education) c.uniqueResult();
    }
}
