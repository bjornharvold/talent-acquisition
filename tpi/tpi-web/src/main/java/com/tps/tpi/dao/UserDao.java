/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Aug 25, 2009
 * Time: 7:12:59 PM
 * Responsibility:
 */

public interface UserDao extends GenericDao<User, String> {
    User getUser(String username, String password) throws PersistenceException;

    User isUserUniqueByUsername(String userId, String username) throws PersistenceException;

    User isUserUniqueByEmail(String userId, String email) throws PersistenceException;

    User getUserByUsername(String username) throws PersistenceException;

    User getUserBySecurityCode(String securityCode) throws PersistenceException;

    List<User> getUsers(String name, Integer index, Integer maxResults) throws PersistenceException;

    List<User> getUsers(List<String> userIds, Integer index, Integer maxResults) throws PersistenceException;

    User getUserById(String id) throws PersistenceException;

    Integer getUserCount(String name) throws PersistenceException;

    List<User> getLastModifiedUsers(Integer maxResults) throws PersistenceException;

    List<User> searchForUsersWithExcludes(List<String> excludedUserIds, String name, Integer index, Integer maxResults) throws PersistenceException;

    Integer searchForUsersWithExcludesCount(List<String> excludedUserIds, String name) throws PersistenceException;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException;

    User getUserByEmail(String email) throws PersistenceException;
}
