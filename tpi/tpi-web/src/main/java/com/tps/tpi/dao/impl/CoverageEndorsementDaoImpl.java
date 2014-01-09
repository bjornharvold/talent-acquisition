package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.CoverageEndorsementDao;
import com.tps.tpi.model.objects.entity.CoverageEndorsement;
import com.tps.tpi.exception.PersistenceException;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * User: Bjorn Harvold
 * Date: Oct 27, 2009
 * Time: 3:40:04 PM
 * Responsibility:
 */
@Repository("coverageEndorsementDao")
public class CoverageEndorsementDaoImpl extends AbstractHibernateDao<CoverageEndorsement, String> implements CoverageEndorsementDao {
    @Override
    public CoverageEndorsement getCoverageEndorsement(String endorsedEntityId, String endorserPersonId) throws PersistenceException {
        Criteria c = getSession().createCriteria(CoverageEndorsement.class);
        c.createAlias("coverage", "c").add(Restrictions.eq("c.id", endorsedEntityId));
        c.createAlias("endorsement", "e").createAlias("e.endorser", "p").add(Restrictions.eq("p.id", endorserPersonId));

        return (CoverageEndorsement) c.uniqueResult();
    }
}
