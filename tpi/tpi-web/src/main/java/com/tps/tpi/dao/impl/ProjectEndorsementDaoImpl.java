package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.ProjectEndorsementDao;
import com.tps.tpi.model.objects.entity.ProjectEndorsement;
import com.tps.tpi.exception.PersistenceException;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 2:56:49 PM
 * Responsibility:
 */
@Repository("projectEndorsementDao")
public class ProjectEndorsementDaoImpl extends AbstractHibernateDao<ProjectEndorsement, String> implements ProjectEndorsementDao {
    @Override
    public ProjectEndorsement getProjectEndorsement(String endorsedEntityId, String endorserPersonId) throws PersistenceException {
        Criteria c = getSession().createCriteria(ProjectEndorsement.class);
        c.createAlias("project", "c").add(Restrictions.eq("c.id", endorsedEntityId));
        c.createAlias("endorsement", "e").createAlias("e.endorser", "p").add(Restrictions.eq("p.id", endorserPersonId));

        return (ProjectEndorsement) c.uniqueResult();
    }
}
