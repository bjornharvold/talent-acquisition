package com.tps.tpi.dao;

import com.tps.tpi.model.objects.entity.BiographySkillEndorsement;
import com.tps.tpi.exception.PersistenceException;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 11:52:42 AM
 * Responsibility:
 */
public interface BiographySkillEndorsementDao extends GenericDao<BiographySkillEndorsement, String> {
    BiographySkillEndorsement getBiographySkillEndorsement(String endorsedEntityId, String endorserPersonId) throws PersistenceException;
}
