package com.tps.tpi.dao.impl;

import com.tps.tpi.model.objects.entity.LanguageEndorsement;
import com.tps.tpi.dao.LanguageEndorsementDao;
import com.tps.tpi.exception.PersistenceException;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * User: Bjorn Harvold
 * Date: Oct 27, 2009
 * Time: 3:41:39 PM
 * Responsibility:
 */
@Repository("languageEndorsementDao")
public class LanguageEndorsementDaoImpl extends AbstractHibernateDao<LanguageEndorsement, String> implements LanguageEndorsementDao {
    @Override
    public LanguageEndorsement getLanguageEndorsementDao(String endorsedEntityId, String endorserPersonId) throws PersistenceException {
        Criteria c = getSession().createCriteria(LanguageEndorsement.class);
        c.createAlias("language", "c").add(Restrictions.eq("c.id", endorsedEntityId));
        c.createAlias("endorsement", "e").createAlias("e.endorser", "p").add(Restrictions.eq("p.id", endorserPersonId));

        return (LanguageEndorsement) c.uniqueResult();
    }
}
