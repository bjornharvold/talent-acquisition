package com.tps.tpi.dao;

import com.tps.tpi.model.objects.entity.CoverageEndorsement;
import com.tps.tpi.exception.PersistenceException;

/**
 * User: Bjorn Harvold
 * Date: Oct 27, 2009
 * Time: 3:39:39 PM
 * Responsibility:
 */
public interface CoverageEndorsementDao extends GenericDao<CoverageEndorsement, String> {
    CoverageEndorsement getCoverageEndorsement(String endorsedEntityId, String endorserPersonId) throws PersistenceException;
}
