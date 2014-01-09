package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.SkilledRoleGroup;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 12, 2009
 * Time: 11:53:11 AM
 * Responsibility:
 */
public interface SkilledRoleGroupDao extends GenericDao<SkilledRoleGroup, String> {
    SkilledRoleGroup getProfessionalRoleGroupByCode(String code) throws PersistenceException;
    List<SkilledRoleGroup> getSkilledRoleGroups(String name, Integer index, Integer maxResults) throws PersistenceException;
}
