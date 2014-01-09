/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.service.impl;

import com.tps.tpi.dao.RoleDao;
import com.tps.tpi.dao.UserDao;
import com.tps.tpi.dao.UserRoleDao;
import com.tps.tpi.email.MailServiceException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Role;
import com.tps.tpi.model.objects.entity.User;
import com.tps.tpi.model.objects.entity.UserRole;
import com.tps.tpi.model.objects.dto.PrincipalData;
import com.tps.tpi.model.objects.enums.UserStatusCd;
import com.tps.tpi.service.MailService;
import com.tps.tpi.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.jasypt.util.password.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * User: Bjorn Harvold
 * Date: Aug 25, 2009
 * Time: 8:45:08 PM
 * Responsibility:
 */
@Service("userService")
@RemotingDestination(channels = {"my-amf", "my-secure-amf"})
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final UserRoleDao userRoleDao;
    private final RoleDao roleDao;
    private final PasswordEncryptor passwordEncryptor;
    private final MailService mailService;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserRoleDao userRoleDao,
                           RoleDao roleDao, PasswordEncryptor passwordEncryptor, MailService mailService) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
        this.roleDao = roleDao;
        this.passwordEncryptor = passwordEncryptor;
        this.mailService = mailService;
    }

    @Override
    public User getUser(String username, String password) throws DomainException {
        User result = null;
        boolean passwordOk = false;

        try {
            result = userDao.getUserByUsername(username);
            passwordOk = passwordEncryptor.checkPassword(password, result.getPassword());
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return passwordOk ? result : null;
    }

    @Override
    public User isUserUniqueByUsername(String userId, String username) throws DomainException {
        try {
            return userDao.isUserUniqueByUsername(userId, username);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public User isUserUniqueByEmail(String userId, String email) throws DomainException {
        try {
            return userDao.isUserUniqueByEmail(userId, email);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    @RemotingInclude
    public User getUserByUsername(String username) throws DomainException {
        try {
            return userDao.getUserByUsername(username);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws DomainException {
        if (StringUtils.isBlank(email)) {
            throw new DomainException("error.missing.data", email);
        }

        try {
            return userDao.getUserByEmail(email);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public User getUserBySecurityCode(String securityCode) throws DomainException {
        try {
            return userDao.getUserBySecurityCode(securityCode);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public User getUserById(String id) throws DomainException {
        try {
            return userDao.getUserById(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getUsers(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return userDao.getUsers(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getUsers(List<String> userIds, Integer index, Integer maxResults) throws DomainException {
        try {
            return userDao.getUsers(userIds, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Integer getUserCount(String name) throws DomainException {
        try {
            return userDao.getUserCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getLastModifiedUsers(Integer maxResults) throws DomainException {
        try {
            return userDao.getLastModifiedUsers(maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> searchForUsersWithExcludes(List<String> excludedUserIds, String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return userDao.searchForUsersWithExcludes(excludedUserIds, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Integer searchForUsersWithExcludesCount(List<String> excludedUserIds, String name) throws DomainException {
        try {
            return userDao.searchForUsersWithExcludesCount(excludedUserIds, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public User saveUser(User u) throws DomainException {
        try {
            // encrypt password and save
            u.setPassword(passwordEncryptor.encryptPassword(u.getPassword()));
            userDao.save(u);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return u;
    }

    @Override
    public User activateUser(String id) throws DomainException {
        User result = null;

        try {
            if (!StringUtils.isBlank(id)) {
                // encrypt password and save
                result = getUserById(id);

                if (result != null && result.getStatus().equals(UserStatusCd.REGISTERED)) {
                    result.setStatus(UserStatusCd.ACTIVE);
                    userDao.update(result);
                } else {
                    result = null;
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public User registerUser(User u, Locale l) throws DomainException {
        User result = null;

        try {
            u.setStatus(UserStatusCd.REGISTERED);
            result = saveUser(u);

            // send activation email
            mailService.sendActivationEmail(u.getEmail(), u.getId(), l);
        } catch (MailServiceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    public void sendPasswordReminder(String email, Locale l) throws DomainException {

        if (StringUtils.isBlank(email)) {
            log.error("Email is null");
            throw new DomainException("Email is null");
        }

        try {
            User u = userDao.getUserByEmail(email);

            if (u != null) {
                // reset password
                String newPassword = Long.toString(Math.abs(new Random().nextLong()), 36);
                u.setPassword(passwordEncryptor.encryptPassword(newPassword));

                mailService.sendPasswordReminderEmail(email, newPassword, l);
            }

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        } catch (MailServiceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public User mergeUser(User u) throws DomainException {
        try {
            return userDao.merge(u);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public User updateUser(User u) throws DomainException {
        try {
            return userDao.update(u);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(String id) throws DomainException {
        try {
            userDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Role getRoleByStatusCode(String statusCode) throws DomainException {
        try {
            return roleDao.getRoleByStatusCode(statusCode);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Role> searchForRoles(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return roleDao.searchForRoles(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Integer searchForRolesCount(String name) throws DomainException {
        try {
            return roleDao.searchForRolesCount(name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Role> getLastModifiedRoles(Integer maxResults) throws DomainException {
        try {
            return roleDao.getLastModifiedRoles(maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Role saveRole(Role r) throws DomainException {
        try {
            return roleDao.save(r);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<UserRole> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return userRoleDao.searchForUserRoles(id, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Integer searchForUserRolesCount(String id, String name) throws DomainException {
        try {
            return userRoleDao.searchForUserRolesCount(id, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public UserRole getUserRoleByUserIdAndRoleId(String userId, String roleId) throws DomainException {
        try {
            return userRoleDao.getUserRoleByUserIdAndRoleId(userId, roleId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<UserRole> getUserRolesByRoleId(String roleId) throws DomainException {
        try {
            return userRoleDao.getUserRolesByRoleId(roleId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public UserRole saveUserRole(UserRole ur) throws DomainException {
        try {
            return userRoleDao.save(ur);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @RemotingInclude
    @Override
    public PrincipalData getPrincipal() throws DomainException {
        GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        int numAuthorities = authorities.length;
        String[] grantedRoles = new String[numAuthorities];
        for (int counter = 0; counter < numAuthorities; counter++) {
            grantedRoles[counter] = authorities[counter].getAuthority();
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return new PrincipalData(username, grantedRoles);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        UserDetails result = null;

        result = userDao.loadUserByUsername(username);

        if (result != null) {
            log.info("Found user with username: " + username + ". Validating password...");
        }
        return result;
    }
}
