package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.SkilledRole;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 12, 2009
 * Time: 11:53:38 AM
 * Responsibility:
 */
public interface SkilledRoleDao extends GenericDao<SkilledRole, String> {
    SkilledRole getProfessionalRoleByCode(String code) throws PersistenceException;
    List<SkilledRole> getSkilledRoles(String name, Integer index, Integer maxResults) throws PersistenceException;
}
