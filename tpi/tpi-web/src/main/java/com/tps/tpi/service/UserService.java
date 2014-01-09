/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.service;

import com.tps.tpi.model.objects.entity.User;
import com.tps.tpi.model.objects.entity.Role;
import com.tps.tpi.model.objects.entity.UserRole;
import com.tps.tpi.model.objects.dto.PrincipalData;
import com.tps.tpi.exception.DomainException;

import java.util.List;
import java.util.Locale;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.annotation.Secured;

/**
 * User: Bjorn Harvold
 * Date: Aug 25, 2009
 * Time: 8:50:18 PM
 * Responsibility:
 */
public interface UserService extends UserDetailsService {
    // users
    @Secured("ROLE_ADMINISTRATOR")
    User getUser(String username, String password) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    User isUserUniqueByUsername(String userId, String username) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    User isUserUniqueByEmail(String userId, String email) throws DomainException;

    @Secured(value = {"ROLE_USER", "ROLE_ADMINISTRATOR"})
    User getUserByUsername(String username) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    User getUserByEmail(String email) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    User getUserBySecurityCode(String securityCode) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    User getUserById(String id) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    List<User> getUsers(String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    List<User> getUsers(List<String> userIds, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    Integer getUserCount(String name) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    List<User> getLastModifiedUsers(Integer maxResults) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    List<User> searchForUsersWithExcludes(List<String> excludedUserIds, String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    Integer searchForUsersWithExcludesCount(List<String> excludedUserIds, String name) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    @Transactional
    User registerUser(User u, Locale l) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    @Transactional
    User saveUser(User u) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    @Transactional
    User mergeUser(User u) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    @Transactional
    User updateUser(User user) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    void deleteUser(String id) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    @Transactional
    User activateUser(String id) throws DomainException;

    // roles
    @Secured("ROLE_ADMINISTRATOR")
    Role getRoleByStatusCode(String statusCode) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    List<Role> searchForRoles(String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    Integer searchForRolesCount(String name) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    List<Role> getLastModifiedRoles(Integer maxResults) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    @Transactional
    Role saveRole(Role r) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    void sendPasswordReminder(String email, Locale l) throws DomainException;

    // userrole
    @Secured("ROLE_ADMINISTRATOR")
    List<UserRole> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    Integer searchForUserRolesCount(String id, String name) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    UserRole getUserRoleByUserIdAndRoleId(String userId, String roleId) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    List<UserRole> getUserRolesByRoleId(String roleId) throws DomainException;

    @Secured("ROLE_ADMINISTRATOR")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    UserRole saveUserRole(UserRole ur) throws DomainException;

    @Secured(value = {"ROLE_USER", "ROLE_ADMINISTRATOR"})
    PrincipalData getPrincipal() throws DomainException;
}
