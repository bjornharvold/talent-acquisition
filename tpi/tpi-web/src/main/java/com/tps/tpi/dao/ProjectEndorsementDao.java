package com.tps.tpi.dao;

import com.tps.tpi.model.objects.entity.ProjectEndorsement;
import com.tps.tpi.exception.PersistenceException;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 2:56:24 PM
 * Responsibility:
 */
public interface ProjectEndorsementDao extends GenericDao<ProjectEndorsement, String> {
    ProjectEndorsement getProjectEndorsement(String endorsedEntityId, String endorserPersonId) throws PersistenceException;
}
