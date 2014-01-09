package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.BiographySkillEndorsementDao;
import com.tps.tpi.model.objects.entity.BiographySkillEndorsement;
import com.tps.tpi.exception.PersistenceException;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 11:53:29 AM
 * Responsibility:
 */
@Repository("biographySkillEndorsementDao")
public class BiographySkillEndorsementDaoImpl extends AbstractHibernateDao<BiographySkillEndorsement, String> implements BiographySkillEndorsementDao {
    @Override
    public BiographySkillEndorsement getBiographySkillEndorsement(String endorsedEntityId, String endorserPersonId) throws PersistenceException {
        Criteria c = getSession().createCriteria(BiographySkillEndorsement.class);
        c.createAlias("biographySkill", "c").add(Restrictions.eq("c.id", endorsedEntityId));
        c.createAlias("endorsement", "e").createAlias("e.endorser", "p").add(Restrictions.eq("p.id", endorserPersonId));

        return (BiographySkillEndorsement) c.uniqueResult();
    }
}
