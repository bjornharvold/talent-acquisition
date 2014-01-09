package com.tps.tpi.dao;

import com.tps.tpi.model.objects.entity.PositionEndorsement;
import com.tps.tpi.exception.PersistenceException;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 2:54:48 PM
 * Responsibility:
 */
public interface PositionEndorsementDao extends GenericDao<PositionEndorsement, String> {
    PositionEndorsement getPositionEndorsement(String endorsedEntityId, String endorserPersonId) throws PersistenceException;
}
