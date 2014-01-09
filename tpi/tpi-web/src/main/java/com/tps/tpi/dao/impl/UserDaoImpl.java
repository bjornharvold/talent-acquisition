/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.UserDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Aug 25, 2009
 * Time: 6:10:22 PM
 * Responsibility:
 */
@Repository
public class UserDaoImpl extends AbstractHibernateDao<User, String> implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    /**
     * Returns a user and all required objects based on a username and password
     *
     * @param username
     * @param password
     * @return
     * @throws PersistenceException
     *
     */
    public User getUser(String username, String password) throws PersistenceException {
        if (StringUtils.isBlank(username)) {
            throw new PersistenceException("error.missing.argument.exception", "username cannot be null");
        }
        if (StringUtils.isBlank(password)) {
            throw new PersistenceException("error.missing.argument.exception", "password cannot be null");
        }

        Criteria c = getSession().createCriteria(User.class);
        c.add(Restrictions.eq("username", username));
        c.add(Restrictions.ne("password", password));

        return (User) c.uniqueResult();
    }

    /**
     * checks the db and the user_tbl that the unique id and the username coming in are truly unique
     * If a user is returned it means this user is not unique
     *
     * @param userId
     * @param username String
     * @return boolean
     * @throws PersistenceException
     */
    public User isUserUniqueByUsername(String userId, String username) throws PersistenceException {
        if (userId == null) {
            throw new PersistenceException("error.missing.argument.exception", "userId cannot be null");
        }
        if (StringUtils.isBlank(username)) {
            throw new PersistenceException("error.missing.argument.exception", "username cannot be null");
        }

        Criteria c = getSession().createCriteria(User.class);
        c.add(Restrictions.eq("username", username));
        c.add(Restrictions.ne("id", userId));

        return (User) c.uniqueResult();
    }

    /**
     * checks the db and the user_tbl that the unique id and the email coming in are truly unique
     * If a user is returned it means this user is not unique
     *
     * @param userId
     * @param email String
     * @return boolean
     * @throws PersistenceException
     */
    public User isUserUniqueByEmail(String userId, String email) throws PersistenceException {
        if (userId == null) {
            throw new PersistenceException("error.missing.argument.exception", "userId cannot be null");
        }
        if (StringUtils.isBlank(email)) {
            throw new PersistenceException("error.missing.argument.exception", "email cannot be null");
        }

        Criteria c = getSession().createCriteria(User.class);
        c.add(Restrictions.eq("email", email));
        c.add(Restrictions.ne("id", userId));

        return (User) c.uniqueResult();
    }

    /**
     * Get user by username
     *
     * @param username
     * @return boolean
     */
    public User getUserByUsername(String username) throws PersistenceException {
        if (StringUtils.isBlank(username)) {
            throw new PersistenceException("error.missing.argument.exception", "username cannot be null");
        }

        Criteria c = getSession().createCriteria(User.class);
        c.add(Restrictions.eq("username", username));

        return (User) c.uniqueResult();
    }

    /**
     * Get User by email!
     *
     * @param email
     * @return boolean
     */
    public User getUserByEmail(String email) throws PersistenceException {
        if (StringUtils.isBlank(email)) {
            throw new PersistenceException("error.missing.argument.exception", "email cannot be null");
        }

        Criteria c = getSession().createCriteria(User.class);
        c.add(Restrictions.eq("email", email));

        return (User) c.uniqueResult();
    }

    public User getUserBySecurityCode(String securityCode) throws PersistenceException {
        User result = null;

        if (StringUtils.isBlank(securityCode)) {
            throw new PersistenceException("error.missing.argument.exception", "security code cannot be null");
        }

        Criteria c = getSession().createCriteria(User.class);
        c.add(Restrictions.eq("securityCode", securityCode));

        result = (User) c.uniqueResult();

        return result;
    }

    /**
     * Returns a paged set of users. Name can be first name, last name
     *
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws PersistenceException
     */
    @SuppressWarnings("unchecked")
    public List<User> getUsers(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (index == null) {
            throw new PersistenceException("error.missing.argument.exception", "index");
        }
        if (maxResults == null) {
            throw new PersistenceException("error.missing.argument.exception", "maxResults");
        }

        if (StringUtils.isNotBlank(name)) {
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("username", name + "%"));
            d.add(Restrictions.ilike("email", name + "%"));
            c.add(d);
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("username"));

        return c.list();
    }

    public List<User> getUsers(List<String> userIds, Integer index, Integer maxResults) throws PersistenceException {
        List<User> result = null;

        if (userIds != null && userIds.size() > 0) {
            Criteria c = getSession().createCriteria(User.class);

            c.add(Restrictions.in("id", userIds));

            if (index != null && maxResults != null) {
                c.setFirstResult(index * maxResults);
                c.setMaxResults(maxResults);
            }

            result = c.list();
        }

        return result;
    }

    @Override
    public User getUserById(String id) throws PersistenceException {
        return get(User.class, id);
    }

    public Integer getUserCount(String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("username", name + "%"));
        }
        c.setProjection(Projections.rowCount());

        return (Integer) c.uniqueResult();
    }

    public List<User> getLastModifiedUsers(Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (maxResults != null) {
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.desc("lastUpdate"));

        return c.list();
    }

    public List<User> searchForUsersWithExcludes(List<String> excludedUserIds, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (StringUtils.isNotBlank(name)) {
            c.createAlias("userInfo", "ui");
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("username", name + "%"));
            d.add(Restrictions.ilike("ui.firstName", name + "%"));
            d.add(Restrictions.ilike("ui.lastName", name + "%"));
            c.add(d);
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        // here we exclude the user ids we don't want to have returned
        if (excludedUserIds != null) {
            Conjunction conj = Restrictions.conjunction();

            for (String id : excludedUserIds) {
                conj.add(Restrictions.ne("id", id));
            }

            c.add(conj);
        }
        c.addOrder(Order.asc("username"));

        return c.list();
    }

    public Integer searchForUsersWithExcludesCount(List<String> excludedUserIds, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (StringUtils.isNotBlank(name)) {
            c.createAlias("userInfo", "ui");
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("username", name + "%"));
            d.add(Restrictions.ilike("ui.firstName", name + "%"));
            d.add(Restrictions.ilike("ui.lastName", name + "%"));
            c.add(d);
        }

        // here we exclude the user ids we don't want to have returned
        if (excludedUserIds != null) {
            Conjunction conj = Restrictions.conjunction();

            for (String id : excludedUserIds) {
                conj.add(Restrictions.ne("id", id));
            }

            c.add(conj);
        }
        c.setProjection(Projections.rowCount());

        return (Integer) c.uniqueResult();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("error.missing.argument.exception", "username cannot be null");
        }

        Criteria c = getSession().createCriteria(User.class);
        c.add(Restrictions.eq("username", username));

        return (UserDetails) c.uniqueResult();
    }
}
