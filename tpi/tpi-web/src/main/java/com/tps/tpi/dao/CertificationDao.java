package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Certification;

/**
 * User: Bjorn Harvold
 * Date: Oct 12, 2009
 * Time: 4:23:19 PM
 * Responsibility:
 */
public interface CertificationDao extends GenericDao<Certification, String> {
    Certification getCertificationByShortName(String shortName) throws PersistenceException;
}
