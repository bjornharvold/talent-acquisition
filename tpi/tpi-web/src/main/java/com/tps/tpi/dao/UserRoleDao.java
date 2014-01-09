/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.UserRole;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Aug 25, 2009
 * Time: 7:24:56 PM
 * Responsibility:
 */

public interface UserRoleDao extends GenericDao<UserRole, String>{
    List<UserRole> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForUserRolesCount(String id, String name) throws PersistenceException;
    UserRole getUserRoleByUserIdAndRoleId(String userId, String roleId) throws PersistenceException;
    List<UserRole> getUserRolesByRoleId(String roleId) throws PersistenceException;
}
