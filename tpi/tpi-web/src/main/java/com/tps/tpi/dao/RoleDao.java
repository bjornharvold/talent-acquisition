/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Role;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Aug 25, 2009
 * Time: 7:30:07 PM
 * Responsibility:
 */

public interface RoleDao extends GenericDao<Role, String> {
    Role getRoleByStatusCode(String statusCode) throws PersistenceException;

    List<Role> searchForRoles(String name, Integer index, Integer maxResults) throws PersistenceException;

    Integer searchForRolesCount(String name) throws PersistenceException;

    List<Role> getLastModifiedRoles(Integer maxResults) throws PersistenceException;
}
