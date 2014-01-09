package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.PositionEndorsementDao;
import com.tps.tpi.model.objects.entity.PositionEndorsement;
import com.tps.tpi.exception.PersistenceException;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 2:55:15 PM
 * Responsibility:
 */
@Repository("positionEndorsementDao")
public class PositionEndorsementDaoImpl extends AbstractHibernateDao<PositionEndorsement, String> implements PositionEndorsementDao {
    @Override
    public PositionEndorsement getPositionEndorsement(String endorsedEntityId, String endorserPersonId) throws PersistenceException {
        Criteria c = getSession().createCriteria(PositionEndorsement.class);
        c.createAlias("position", "c").add(Restrictions.eq("c.id", endorsedEntityId));
        c.createAlias("endorsement", "e").createAlias("e.endorser", "p").add(Restrictions.eq("p.id", endorserPersonId));

        return (PositionEndorsement) c.uniqueResult();
    }
}
