package com.tps.tpi.dao;

import com.tps.tpi.model.objects.entity.LanguageEndorsement;
import com.tps.tpi.exception.PersistenceException;

/**
 * User: Bjorn Harvold
 * Date: Oct 27, 2009
 * Time: 3:41:10 PM
 * Responsibility:
 */
public interface LanguageEndorsementDao extends GenericDao<LanguageEndorsement, String> {
    LanguageEndorsement getLanguageEndorsementDao(String endorsedEntityId, String endorserPersonId) throws PersistenceException;
}
