package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.CertificationDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Certification;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 12, 2009
 * Time: 4:23:44 PM
 * Responsibility:
 */
@Repository("certificationDao")
public class CertificationDaoImpl extends AbstractHibernateDao<Certification, String> implements CertificationDao {
    @Override
    public Certification getCertificationByShortName(String shortName) throws PersistenceException {
        Criteria c = getSession().createCriteria(Certification.class);
        c.add(Restrictions.eq("shortName", shortName));

        return (Certification) c.uniqueResult();
    }
}
