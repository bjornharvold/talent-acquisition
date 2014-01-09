/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.GenericDao;
import com.tps.tpi.exception.PersistenceException;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 28, 2005
 * Time: 9:15:42 AM
 * <p/>
 * <p/>
 * Description:
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao<T, ID extends Serializable> extends HibernateDaoSupport implements GenericDao<T, ID> {

    private Class<T> persistentClass;

    @Autowired
    public void setDaoSupport(HibernateTemplate hibernateTemplate) {
        super.setHibernateTemplate(hibernateTemplate);
    }

    public AbstractHibernateDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     * Hibernate expects the queryName to be fully qualified
     * E.g. com.hxcel.globalhealth.common.object.User.getUser
     * The application should only care about the actual query name since the DAO
     * knows itself we append the path here.
     *
     * @param queryName
     * @return String
     */
    public String getFullyQualifiedQueryName(String queryName) {
        StringBuffer sb = new StringBuffer();
//        sb.append(getPersistentClass().getName());
//        sb.append(".");
        sb.append(queryName);

        return sb.toString();
    }


    public T findObjectByExample(T exampleInstance) throws PersistenceException {
        if (exampleInstance == null) {
            throw new PersistenceException("error.missing.argument.exception", "exampleInstance");
        }

        List<T> list = getHibernateTemplate().findByExample(exampleInstance);

        return list.size() == 1 ? list.get(0) : null;
    }


    public T findById(final ID id, final boolean lock) throws PersistenceException {

        if (id == null) {
            throw new PersistenceException("error.missing.argument.exception", "id");
        }

        T entity;
        if (lock)
            entity = (T) getHibernateTemplate().load(getPersistentClass(), id, LockMode.UPGRADE);
        else
            entity = (T) getHibernateTemplate().load(getPersistentClass(), id);

        return entity;

    }

    public T findById(final ID id) throws PersistenceException {

        if (id == null) {
            throw new PersistenceException("error.missing.argument.exception", "id");
        }

        T entity = (T) getHibernateTemplate().load(getPersistentClass(), id);

        return entity;

    }


    public List<T> findAll() throws PersistenceException {
        return findByCriteria();
    }


    public List<T> findByExample(final T exampleInstance, final String[] excludeProperty) throws PersistenceException {

        if (exampleInstance == null) {

            throw new PersistenceException("error.missing.argument.exception", "exampleInstance");
        }
        return (List<T>) execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException {
                        Criteria crit = session.createCriteria(getPersistentClass());
                        Example example = Example.create(exampleInstance);
                        for (String exclude : excludeProperty) {
                            example.excludeProperty(exclude);
                        }
                        crit.add(example);
                        return crit.list();
                    }
                });

    }

    /**
     * Removes entity
     *
     * @param id
     */

    public void delete(ID id) throws PersistenceException {
        delete(findById(id, true));
    }

    /**
     * Returns an object based on a query name
     *
     * @param queryName
     * @return T
     */

    public T findObjectByNamedQuery(final String queryName) throws PersistenceException {
        if (queryName == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryName");
        }

        return (T) execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException {
                        Query q = session.getNamedQuery(getFullyQualifiedQueryName(queryName));

                        return q.uniqueResult();
                    }
                });
    }

    /**
     * Convenience method to call a simple named query without expecting anything back.
     * You can do a bulk update/delete here for example
     *
     * @param queryName
     */
    public void executeUpdate(final String queryName) throws PersistenceException {
        if (queryName == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryName");
        }

        try {
            execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session) throws HibernateException {
                            Query q = session.getNamedQuery(getFullyQualifiedQueryName(queryName));
                            q.executeUpdate();

                            return null;
                        }
                    });
        } catch (HibernateException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Returns a persisted object based on a query name with params. Note: This method will ONLY
     * work if the DAO implementation is the one suggested by Hibernate (one DAO per object). Else
     * you will most likely get a class cast exception.
     *
     * @param queryName
     * @param params
     * @param values
     * @param types
     * @return Persistent object
     */

    public T findObjectByNamedQueryAndNamedParam(final String queryName, final String[] params, final Object[] values, final Type[] types) throws PersistenceException {

        if (queryName == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryName");
        }

        try {
            return (T) execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session) throws HibernateException {
                            Query q = session.getNamedQuery(getFullyQualifiedQueryName(queryName));

                            if (params != null) {
                                for (int i = 0; i < params.length; i++) {
                                    q.setParameter(params[i], values[i], types[i]);
                                }
                            }

                            return q.uniqueResult();
                        }
                    });
        } catch (HibernateException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Use this inside subclasses as a convenience method.
     */

    protected List<T> findByCriteria(final Criterion... criterion) throws PersistenceException {

        try {
            return (List<T>) execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session) throws HibernateException {
                            Criteria crit = getSession().createCriteria(getPersistentClass());
                            for (Criterion c : criterion) {
                                crit.add(c);
                            }
                            return crit.list();
                        }
                    });
        } catch (HibernateException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Set if a new Session should be created when no transactional Session can be found for the current thread.
     * HibernateTemplate is aware of a corresponding Session bound to the current thread, for example when using HibernateTransactionManager. If allowCreate is true, a new non-transactional Session will be created if none found, which needs to be closed at the end of the operation. If false, an IllegalStateException will get thrown in this case.
     * See Also:
     * SessionFactoryUtils.getSession(SessionFactory, boolean)
     *
     * @param allowCreate
     */
    public void setAllowCreate(boolean allowCreate) {
        getHibernateTemplate().setAllowCreate(allowCreate);
    }

    /**
     * Return if a new Session should be created if no thread-bound found.
     *
     * @return boolean
     */
    public boolean isAllowCreate() {
        return getHibernateTemplate().isAllowCreate();
    }

    /**
     * Set whether to always use a new Hibernate Session for this template. Default is "false"; if activated, all operations on this template will work on a new Hibernate Session even in case of a pre-bound Session (for example, within a transaction or OpenSessionInViewFilter).
     * Within a transaction, a new Hibernate Session used by this template will participate in the transaction through using the same JDBC Connection. In such a scenario, multiple Sessions will participate in the same database transaction.
     * Turn this on for operations that are supposed to always execute independently, without side effects caused by a shared Hibernate Session.
     *
     * @param alwaysUseNewSession
     */
    public void setAlwaysUseNewSession(boolean alwaysUseNewSession) {
        getHibernateTemplate().setAlwaysUseNewSession(alwaysUseNewSession);
    }

    /**
     * Return whether to always use a new Hibernate Session for this template.
     *
     * @return boolean
     */
    public boolean isAlwaysUseNewSession() {
        return getHibernateTemplate().isAlwaysUseNewSession();
    }

    /**
     * Set whether to expose the native Hibernate Session to HibernateCallback code. Default is "false": a Session proxy will be returned, suppressing close calls and automatically applying query cache settings and transaction timeouts.
     * See Also:
     * HibernateCallback, Session, setCacheQueries(boolean), setQueryCacheRegion(java.lang.String), prepareQuery(org.hibernate.Query), prepareCriteria(org.hibernate.Criteria)
     *
     * @param exposeNativeSession
     */
    public void setExposeNativeSession(boolean exposeNativeSession) {
        getHibernateTemplate().setExposeNativeSession(exposeNativeSession);
    }

    /**
     * Return whether to expose the native Hibernate Session to HibernateCallback code, or rather a Session proxy.
     *
     * @return boolean
     */
    public boolean isExposeNativeSession() {
        return getHibernateTemplate().isExposeNativeSession();
    }

    /**
     * Set whether to check that the Hibernate Session is not in read-only mode in case of write operations (save/update/delete).
     * Default is "true", for fail-fast behavior when attempting write operations within a read-only transaction. Turn this off to allow save/update/delete on a Session with flush mode NEVER.
     * See Also:
     * HibernateAccessor.setFlushMode(int), checkWriteOperationAllowed(org.hibernate.Session), TransactionDefinition.isReadOnly()
     *
     * @param checkWriteOperations
     */
    public void setCheckWriteOperations(boolean checkWriteOperations) {
        getHibernateTemplate().setCheckWriteOperations(checkWriteOperations);
    }

    /**
     * Return whether to check that the Hibernate Session is not in read-only mode in case of write operations (save/update/delete).
     *
     * @return boolean
     */
    public boolean isCheckWriteOperations() {
        return getHibernateTemplate().isCheckWriteOperations();
    }

    /**
     * Set whether to cache all queries executed by this template. If this is true, all Query and Criteria objects created by this template will be marked as cacheable (including all queries through find methods).
     * To specify the query region to be used for queries cached by this template, set the "queryCacheRegion" property.
     * See Also:
     * setQueryCacheRegion(java.lang.String), Query.setCacheable(boolean), Criteria.setCacheable(boolean)
     *
     * @param cacheQueries
     */
    public void setCacheQueries(boolean cacheQueries) {
        getHibernateTemplate().setCacheQueries(cacheQueries);
    }

    /**
     * Return whether to cache all queries executed by this template.
     *
     * @return boolean
     */
    public boolean isCacheQueries() {
        return getHibernateTemplate().isCacheQueries();
    }

    /**
     * Set the name of the cache region for queries executed by this template. If this is specified, it will be applied to all Query and Criteria objects created by this template (including all queries through find methods).
     * The cache region will not take effect unless queries created by this template are configured to be cached via the "cacheQueries" property.
     * See Also:
     * setCacheQueries(boolean), Query.setCacheRegion(java.lang.String), Criteria.setCacheRegion(java.lang.String)
     *
     * @param queryCacheRegion
     */
    public void setQueryCacheRegion(String queryCacheRegion) {
        getHibernateTemplate().setQueryCacheRegion(queryCacheRegion);
    }

    /**
     * Return the name of the cache region for queries executed by this template.
     *
     * @return String
     */
    public String getQueryCacheRegion() {
        return getHibernateTemplate().getQueryCacheRegion();
    }

    /**
     * Set the fetch size for this HibernateTemplate. This is important for processing large result sets: Setting this higher than the default value will increase processing speed at the cost of memory consumption; setting this lower can avoid transferring row data that will never be read by the application.
     * Default is 0, indicating to use the JDBC driver's default.
     *
     * @param fetchSize
     */
    public void setFetchSize(int fetchSize) {
        getHibernateTemplate().setFetchSize(fetchSize);
    }

    /**
     * Return the fetch size specified for this HibernateTemplate.
     *
     * @return int
     */
    public int getFetchSize() {
        return getHibernateTemplate().getFetchSize();
    }

    /**
     * Set the maximum number of rows for this HibernateTemplate. This is important for processing subsets of large result sets, avoiding to read and hold the entire result set in the database or in the JDBC driver if we're never interested in the entire result in the first place (for example, when performing searches that might return a large number of matches).
     * Default is 0, indicating to use the JDBC driver's default.
     *
     * @param maxResults
     */
    public void setMaxResults(int maxResults) {
        getHibernateTemplate().setMaxResults(maxResults);
    }

    /**
     * Return the maximum number of rows specified for this HibernateTemplate.
     *
     * @return int
     */
    public int getMaxResults() {
        return getHibernateTemplate().getMaxResults();
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute the action specified by the given action object within a Session. Application exceptions thrown by the action object get propagated to the caller (can only be unchecked). Hibernate exceptions are transformed into appropriate DAO ones. Allows for returning a result object, i.e. a domain object or a collection of domain objects.
     * <p/>
     * Note: Callback code is not supposed to handle transactions itself! Use an appropriate transaction manager like HibernateTransactionManager. Generally, callback code must not touch any Session lifecycle methods, like close, disconnect, or reconnect, to let the template do its work.
     * <p/>
     * Specified by:
     * execute in interface HibernateOperations
     * <p/>
     * Parameters:
     * action - callback object that specifies the Hibernate action
     * Returns:
     * a result object returned by the action, or null
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * HibernateTransactionManager, org.springframework.dao, org.springframework.transaction, Session
     *
     * @param action
     */

    public T execute(HibernateCallback action) throws PersistenceException {
        T result = null;

        if (action == null) {

            throw new PersistenceException("error.missing.argument.exception", "callback action");
        }

        try {
            result = (T) getHibernateTemplate().execute(action);
        } catch (DataAccessException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute the specified action assuming that the result object is a List. This is a convenience method for executing Hibernate find calls or queries within an action.
     * <p/>
     * Specified by:
     * executeFind in interface HibernateOperations
     * <p/>
     * Parameters:
     * action - calback object that specifies the Hibernate action
     * Returns:
     * a List result returned by the action, or null
     * Throws:
     * PersistenceException - in case of Hibernate errors
     *
     * @param action
     */

    public List<T> executeFind(HibernateCallback action) throws PersistenceException {
        List<T> result = null;

        if (action == null) {

            throw new PersistenceException("error.missing.argument.exception", "callback action");
        }

        try {
            result = getHibernateTemplate().executeFind(action);
        } catch (DataAccessException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Return the persistent instance of the given entity class with the given identifier, or null if not found.
     * <p/>
     * Specified by:
     * get in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityClass - a persistent class
     * id - an identifier of the persistent instance
     * Returns:
     * the persistent instance, or null if not found
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.get(Class, java.io.Serializable)
     *
     * @param entityClass
     * @param id
     */

    public T get(Class entityClass, ID id) throws PersistenceException {
        T result = null;

        if (entityClass == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity class");
        }
        if (id == null) {

            throw new PersistenceException("error.missing.argument.exception", "id");
        }

        try {
            result = (T) getHibernateTemplate().get(entityClass, id);
        } catch (DataAccessException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Return the persistent instance of the given entity class with the given identifier, or null if not found. Obtains the specified lock mode if the instance exists.
     * <p/>
     * Specified by:
     * get in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityClass - a persistent class
     * id - an identifier of the persistent instance
     * lockMode - the lock mode to obtain
     * Returns:
     * the persistent instance, or null if not found
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.get(Class, java.io.Serializable, LockMode)
     *
     * @param entityClass
     * @param id
     * @param lockMode
     */

    public T get(Class entityClass, ID id, LockMode lockMode) throws PersistenceException {
        T result = null;

        if (entityClass == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity class");
        }
        if (id == null) {

            throw new PersistenceException("error.missing.argument.exception", "id");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }

        try {
            result = (T) getHibernateTemplate().get(entityClass, id, lockMode);
        } catch (DataAccessException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Return the persistent instance of the given entity class with the given identifier, or null if not found.
     * <p/>
     * Specified by:
     * get in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * id - an identifier of the persistent instance
     * Returns:
     * the persistent instance, or null if not found
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.get(Class, java.io.Serializable)
     *
     * @param entityName
     * @param id
     */

    public T get(String entityName, ID id) throws PersistenceException {
        T result = null;

        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (id == null) {

            throw new PersistenceException("error.missing.argument.exception", "id");
        }

        try {
            result = (T) getHibernateTemplate().get(entityName, id);
        } catch (DataAccessException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Return the persistent instance of the given entity class with the given identifier, or null if not found. Obtains the specified lock mode if the instance exists.
     * <p/>
     * Specified by:
     * get in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * id - an identifier of the persistent instance
     * lockMode - the lock mode to obtain
     * Returns:
     * the persistent instance, or null if not found
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.get(Class, java.io.Serializable, LockMode)
     *
     * @param entityName
     * @param id
     * @param lockMode
     */

    public T get(String entityName, ID id, LockMode lockMode) throws PersistenceException {
        T result = null;

        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (id == null) {

            throw new PersistenceException("error.missing.argument.exception", "id");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }

        try {
            result = (T) getHibernateTemplate().get(entityName, id, lockMode);
        } catch (DataAccessException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Return the persistent instance of the given entity class with the given identifier, throwing an exception if not found.
     * <p/>
     * Specified by:
     * load in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityClass - a persistent class
     * id - an identifier of the persistent instance
     * Returns:
     * the persistent instance
     * Throws:
     * ObjectRetrievalFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.load(Class, java.io.Serializable)
     *
     * @param entityClass
     * @param id
     */

    public T load(Class entityClass, ID id) throws PersistenceException {
        T result = null;

        if (entityClass == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (id == null) {

            throw new PersistenceException("error.missing.argument.exception", "id");
        }

        try {
            result = (T) getHibernateTemplate().load(entityClass, id);
        } catch (ObjectRetrievalFailureException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        catch (DataAccessException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Return the persistent instance of the given entity class with the given identifier, throwing an exception if not found. Obtains the specified lock mode if the instance exists.
     * <p/>
     * Specified by:
     * load in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityClass - a persistent class
     * id - an identifier of the persistent instance
     * lockMode - the lock mode to obtain
     * Returns:
     * the persistent instance
     * Throws:
     * ObjectRetrievalFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.load(Class, java.io.Serializable)
     *
     * @param entityClass
     * @param id
     * @param lockMode
     */

    public T load(Class entityClass, ID id, LockMode lockMode) throws PersistenceException {
        T result = null;

        if (entityClass == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity class");
        }
        if (id == null) {

            throw new PersistenceException("error.missing.argument.exception", "id");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }

        try {
            result = (T) getHibernateTemplate().load(entityClass, id, lockMode);
        } catch (ObjectRetrievalFailureException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Return the persistent instance of the given entity class with the given identifier, throwing an exception if not found.
     * <p/>
     * Specified by:
     * load in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * id - an identifier of the persistent instance
     * Returns:
     * the persistent instance
     * Throws:
     * ObjectRetrievalFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.load(Class, java.io.Serializable)
     *
     * @param entityName
     * @param id
     */

    public T load(String entityName, ID id) throws PersistenceException {
        T result = null;

        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (id == null) {

            throw new PersistenceException("error.missing.argument.exception", "id");
        }

        try {
            result = (T) getHibernateTemplate().load(entityName, id);
        } catch (ObjectRetrievalFailureException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Return the persistent instance of the given entity class with the given identifier, throwing an exception if not found. Obtains the specified lock mode if the instance exists.
     * <p/>
     * Specified by:
     * load in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * id - an identifier of the persistent instance
     * lockMode - the lock mode to obtain
     * Returns:
     * the persistent instance
     * Throws:
     * ObjectRetrievalFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.load(Class, java.io.Serializable)
     *
     * @param entityName
     * @param id
     * @param lockMode
     */

    public T load(String entityName, ID id, LockMode lockMode) throws PersistenceException {
        T result = null;

        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (id == null) {

            throw new PersistenceException("error.missing.argument.exception", "id");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }

        try {
            result = (T) getHibernateTemplate().load(entityName, id, lockMode);
        } catch (ObjectRetrievalFailureException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Load the persistent instance with the given identifier into the given object, throwing an exception if not found.
     * <p/>
     * Specified by:
     * load in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the object (of the target class) to load into
     * id - an identifier of the persistent instance
     * Throws:
     * ObjectRetrievalFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.load(Object, java.io.Serializable)
     *
     * @param entity
     * @param id
     */
    public void load(T entity, ID id) throws PersistenceException {

        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        if (id == null) {

            throw new PersistenceException("error.missing.argument.exception", "id");
        }

        try {
            getHibernateTemplate().load(entity, id);
        } catch (ObjectRetrievalFailureException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

    }

    /**
     * Description copied from interface: HibernateOperations
     * Re-read the state of the given persistent instance.
     * <p/>
     * Specified by:
     * refresh in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to re-read
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.refresh(Object)
     *
     * @param entity
     */
    public void refresh(T entity) throws PersistenceException {

        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        try {
            getHibernateTemplate().refresh(entity);
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Re-read the state of the given persistent instance. Obtains the specified lock mode for the instance.
     * <p/>
     * Specified by:
     * refresh in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to re-read
     * lockMode - the lock mode to obtain
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.refresh(Object, LockMode)
     *
     * @param entity
     * @param lockMode
     */
    public void refresh(T entity, LockMode lockMode) throws PersistenceException {
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }
        try {
            getHibernateTemplate().refresh(entity, lockMode);
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Check whether the given object is in the Session cache.
     * <p/>
     * Specified by:
     * contains in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistence instance to check
     * Returns:
     * whether the given object is in the Session cache
     * Throws:
     * PersistenceException - if there is a Hibernate error
     * See Also:
     * Session.contains(java.lang.Object)
     *
     * @param entity
     */
    public boolean contains(T entity) throws PersistenceException {
        boolean result = false;

        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }

        try {
            result = getHibernateTemplate().contains(entity);
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Remove the given object from the Session cache.
     * <p/>
     * Specified by:
     * evict in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to evict
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.evict(java.lang.Object)
     *
     * @param entity
     */
    public void evict(T entity) throws PersistenceException {

        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        try {
            getHibernateTemplate().evict(entity);
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Force initialization of a Hibernate proxy or persistent collection.
     * <p/>
     * Specified by:
     * initialize in interface HibernateOperations
     * <p/>
     * Parameters:
     * proxy - a proxy for a persistent object or a persistent collection
     * Throws:
     * PersistenceException - if we can't initialize the proxy, for example because it is not associated with an active Session
     * See Also:
     * Hibernate.initialize(java.lang.Object)
     *
     * @param proxy
     */
    public void initialize(Object proxy) throws PersistenceException {
        if (proxy == null) {

            throw new PersistenceException("error.missing.argument.exception", "proxy");
        }
        try {
            getHibernateTemplate().initialize(proxy);
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Return an enabled Hibernate Filter for the given filter name. The returned Filter instance can be used to set filter parameters.
     * <p/>
     * Specified by:
     * enableFilter in interface HibernateOperations
     * <p/>
     * Parameters:
     * filterName - the name of the filter
     * Returns:
     * the enabled Hibernate Filter (either already enabled or enabled on the fly by this operation)
     * Throws:
     * IllegalStateException - if we are not running within a transactional Session (in which case this operation does not make sense)
     *
     * @param filterName
     * @throws IllegalStateException
     */
    public Filter enableFilter(String filterName) throws PersistenceException {
        Filter result = null;

        if (filterName == null) {

            throw new PersistenceException("error.missing.argument.exception", "filterName");
        }

        try {
            result = getHibernateTemplate().enableFilter(filterName);
        } catch (IllegalStateException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Obtain the specified lock level upon the given object, implicitly checking whether the corresponding database entry still exists (throwing an OptimisticLockingFailureException if not found).
     * <p/>
     * Specified by:
     * lock in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to lock
     * lockMode - the lock mode to obtain
     * Throws:
     * ObjectOptimisticLockingFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.lock(Object, LockMode)
     *
     * @param entity
     * @param lockMode
     */
    public void lock(T entity, LockMode lockMode) throws PersistenceException {

        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }

        try {
            getHibernateTemplate().lock(entity, lockMode);
        } catch (ObjectOptimisticLockingFailureException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

    }

    /**
     * Description copied from interface: HibernateOperations
     * Obtain the specified lock level upon the given object, implicitly checking whether the corresponding database entry still exists (throwing an OptimisticLockingFailureException if not found).
     * <p/>
     * Specified by:
     * lock in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * entity - the persistent instance to lock
     * lockMode - the lock mode to obtain
     * Throws:
     * ObjectOptimisticLockingFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.lock(Object, LockMode)
     *
     * @param entityName
     * @param entity
     * @param lockMode
     */
    public void lock(String entityName, T entity, LockMode lockMode) throws PersistenceException {

        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }
        try {
            getHibernateTemplate().lock(entityName, entity, lockMode);
        } catch (ObjectOptimisticLockingFailureException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Persist the given transient instance.
     * <p/>
     * Specified by:
     * save in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the transient instance to persist
     * Returns:
     * the generated identifier
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.save(Object)
     *
     * @param entity
     */

    public T save(T entity) throws PersistenceException {

        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }

        try {
            getHibernateTemplate().save(entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Persist the given transient instance.
     * <p/>
     * Specified by:
     * save in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * entity - the transient instance to persist
     * Returns:
     * the generated identifier
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.save(Object)
     *
     * @param entityName
     * @param entity
     */

    public T save(String entityName, T entity) throws PersistenceException {

        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }

        try {
            getHibernateTemplate().save(entityName, entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Update the given persistent instance, associating it with the current Hibernate Session.
     * <p/>
     * Specified by:
     * update in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to update
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.update(Object)
     *
     * @param entity
     */
    public T update(final T entity) throws PersistenceException {
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        try {
            getHibernateTemplate().update(entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Update the given persistent instance, associating it with the current Hibernate Session.
     * <p/>
     * Obtains the specified lock mode if the instance exists, implicitly checking whether the corresponding database entry still exists (throwing an OptimisticLockingFailureException if not found).
     * <p/>
     * Specified by:
     * update in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to update
     * lockMode - the lock mode to obtain
     * Throws:
     * ObjectOptimisticLockingFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.update(Object)
     *
     * @param entity
     * @param lockMode
     */
    public T update(T entity, LockMode lockMode) throws PersistenceException {
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }
        try {
            getHibernateTemplate().update(entity, lockMode);
        } catch (ObjectOptimisticLockingFailureException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Update the given persistent instance, associating it with the current Hibernate Session.
     * <p/>
     * Specified by:
     * update in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * entity - the persistent instance to update
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.update(Object)
     *
     * @param entityName
     * @param entity
     */
    public T update(String entityName, T entity) throws PersistenceException {

        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }

        try {
            getHibernateTemplate().update(entityName, entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Update the given persistent instance, associating it with the current Hibernate Session.
     * <p/>
     * Obtains the specified lock mode if the instance exists, implicitly checking whether the corresponding database entry still exists (throwing an OptimisticLockingFailureException if not found).
     * <p/>
     * Specified by:
     * update in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * entity - the persistent instance to update
     * lockMode - the lock mode to obtain
     * Throws:
     * ObjectOptimisticLockingFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.update(Object)
     *
     * @param entityName
     * @param entity
     * @param lockMode
     */
    public T update(String entityName, T entity, LockMode lockMode) throws PersistenceException {
        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }
        try {
            getHibernateTemplate().update(entityName, entity, lockMode);
        } catch (ObjectOptimisticLockingFailureException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Save or update the given persistent instance, according to its id (matching the configured "unsaved-value"?). Associates the instance with the current Hibernate Session.
     * <p/>
     * Specified by:
     * saveOrUpdate in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to save or update (to be associated with the Hibernate Session)
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.saveOrUpdate(Object)
     *
     * @param entity
     */
    public T saveOrUpdate(T entity) throws PersistenceException {
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        try {
            getHibernateTemplate().saveOrUpdate(entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Save or update the given persistent instance, according to its id (matching the configured "unsaved-value"?). Associates the instance with the current Hibernate Session.
     * <p/>
     * Specified by:
     * saveOrUpdate in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * entity - the persistent instance to save or update (to be associated with the Hibernate Session)
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.saveOrUpdate(Object)
     *
     * @param entityName
     * @param entity
     */
    public T saveOrUpdate(String entityName, T entity) throws PersistenceException {
        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        try {
            getHibernateTemplate().saveOrUpdate(entityName, entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Save or update all given persistent instances, according to its id (matching the configured "unsaved-value"?). Associates the instances with the current Hibernate Session.
     * <p/>
     * Specified by:
     * saveOrUpdateAll in interface HibernateOperations
     * <p/>
     * Parameters:
     * entities - the persistent instances to save or update (to be associated with the Hibernate Session)
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.saveOrUpdate(Object)
     *
     * @param entities
     */
    public void saveOrUpdateAll(Collection<T> entities) throws PersistenceException {
        if (entities == null) {

            throw new PersistenceException("error.missing.argument.exception", "entitiesa are null");
        }
        try {
            getHibernateTemplate().saveOrUpdateAll(entities);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Persist the given transient instance. Follows JSR-220 semantics.
     * <p/>
     * Similar to save, associating the given object with the current Hibernate Session.
     * <p/>
     * Specified by:
     * persist in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to persist
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.persist(Object), HibernateOperations.save(java.lang.Object)
     *
     * @param entity
     */
    public T persist(T entity) throws PersistenceException {
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        try {
            getHibernateTemplate().persist(entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Persist the given transient instance. Follows JSR-220 semantics.
     * <p/>
     * Similar to save, associating the given object with the current Hibernate Session.
     * <p/>
     * Specified by:
     * persist in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * entity - the persistent instance to persist
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.persist(Object), HibernateOperations.save(java.lang.Object)
     *
     * @param entityName
     * @param entity
     */
    public T persist(String entityName, T entity) throws PersistenceException {
        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        try {
            getHibernateTemplate().persist(entityName, entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return entity;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Copy the state of the given object onto the persistent object with the same identifier. Follows JSR-220 semantics.
     * <p/>
     * Similar to saveOrUpdate, but never associates the given object with the current Hibernate Session. In case of a new entity, the state will be copied over as well.
     * <p/>
     * Note that merge will not update the identifiers in the passed-in object graph (in contrast to TopLink)! Consider registering Spring's IdTransferringMergeEventListener if you'd like to have newly assigned ids transferred to the original object graph too.
     * <p/>
     * Specified by:
     * merge in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the object to merge with the corresponding persistence instance
     * Returns:
     * the updated, registered persistent instance
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.merge(Object), HibernateOperations.saveOrUpdate(java.lang.Object), IdTransferringMergeEventListener
     *
     * @param entity
     */

    public T merge(T entity) throws PersistenceException {
        T result = null;

        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }

        try {
            result = (T) getHibernateTemplate().merge(entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Copy the state of the given object onto the persistent object with the same identifier. Follows JSR-220 semantics.
     * <p/>
     * Similar to saveOrUpdate, but never associates the given object with the current Hibernate Session. In case of a new entity, the state will be copied over as well.
     * <p/>
     * Note that merge will not update the identifiers in the passed-in object graph (in contrast to TopLink)! Consider registering Spring's IdTransferringMergeEventListener if you'd like to have newly assigned ids transferred to the original object graph too.
     * <p/>
     * Specified by:
     * merge in interface HibernateOperations
     * <p/>
     * Parameters:
     * entityName - the name of a persistent entity
     * entity - the object to merge with the corresponding persistence instance
     * Returns:
     * the updated, registered persistent instance
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.merge(Object), HibernateOperations.saveOrUpdate(java.lang.Object)
     *
     * @param entityName
     * @param entity
     */

    public T merge(String entityName, T entity) throws PersistenceException {
        T result = null;

        if (entityName == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity name");
        }
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }

        try {
            result = (T) getHibernateTemplate().merge(entityName, entity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Delete the given persistent instance.
     * <p/>
     * Specified by:
     * delete in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to delete
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.delete(Object)
     *
     * @param entity
     */
    public void delete(T entity) throws PersistenceException {
        if (entity == null) {
            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        try {
            getHibernateTemplate().delete(entity);
        } catch (DataAccessException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Delete the given persistent instance.
     * <p/>
     * Obtains the specified lock mode if the instance exists, implicitly checking whether the corresponding database entry still exists (throwing an OptimisticLockingFailureException if not found).
     * <p/>
     * Specified by:
     * delete in interface HibernateOperations
     * <p/>
     * Parameters:
     * entity - the persistent instance to delete
     * lockMode - the lock mode to obtain
     * Throws:
     * ObjectOptimisticLockingFailureException - if not found
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.delete(Object)
     *
     * @param entity
     * @param lockMode
     */
    public void delete(T entity, LockMode lockMode) throws PersistenceException {
        if (entity == null) {

            throw new PersistenceException("error.missing.argument.exception", "entity");
        }
        if (lockMode == null) {

            throw new PersistenceException("error.missing.argument.exception", "lockMode");
        }
        try {
            getHibernateTemplate().delete(entity, lockMode);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (DataAccessException ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {
            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Delete all given persistent instances.
     * <p/>
     * This can be combined with any of the find methods to delete by query in two lines of code.
     * <p/>
     * Specified by:
     * deleteAll in interface HibernateOperations
     * <p/>
     * Parameters:
     * entities - the persistent instances to delete
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.delete(Object)
     *
     * @param entities
     */
    public void deleteAll(Collection<T> entities) throws PersistenceException {
        if (entities == null) {

            throw new PersistenceException("error.missing.argument.exception", "entities");
        }
        try {
            getHibernateTemplate().deleteAll(entities);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Flush all pending saves, updates and deletes to the database.
     * <p/>
     * Only invoke this for selective eager flushing, for example when JDBC code needs to see certain changes within the same transaction. Else, it's preferable to rely on auto-flushing at transaction completion.
     * <p/>
     * Specified by:
     * flush in interface HibernateOperations
     * <p/>
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.flush()
     */
    public void flush() throws PersistenceException {
        try {
            getHibernateTemplate().flush();
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Remove all objects from the Session cache, and cancel all pending saves, updates and deletes.
     * <p/>
     * Specified by:
     * clear in interface HibernateOperations
     * <p/>
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.clear()
     */
    public void clear() throws PersistenceException {
        try {
            getHibernateTemplate().clear();
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query for persistent instances.
     * <p/>
     * Specified by:
     * find in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - a query expressed in Hibernate's query language
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.createQuery(java.lang.String)
     *
     * @param queryString
     */

    public List<T> find(String queryString) throws PersistenceException {
        List<T> result = null;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }

        try {
            result = getHibernateTemplate().find(queryString);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query for persistent instances, binding one value to a "?" parameter in the query string.
     * <p/>
     * Specified by:
     * find in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - a query expressed in Hibernate's query language
     * value - the value of the parameter
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.createQuery(java.lang.String)
     *
     * @param queryString
     * @param value
     */

    public List<T> find(String queryString, Object value) throws PersistenceException {
        List<T> result = null;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        if (value == null) {

            throw new PersistenceException("error.missing.argument.exception", "value");
        }

        try {
            result = getHibernateTemplate().find(queryString, value);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query for persistent instances, binding a number of values to "?" parameters in the query string.
     * <p/>
     * Specified by:
     * find in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - a query expressed in Hibernate's query language
     * values - the values of the parameters
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.createQuery(java.lang.String)
     *
     * @param queryString
     * @param values
     */

    public List<T> find(String queryString, Object[] values) throws PersistenceException {
        List<T> result = null;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        if (values == null) {

            throw new PersistenceException("error.missing.argument.exception", "values");
        }

        try {
            result = getHibernateTemplate().find(queryString, values);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query for persistent instances, binding one value to a ":" named parameter in the query string.
     * <p/>
     * Specified by:
     * findByNamedParam in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - the name of a Hibernate query in a mapping file
     * paramName - the name of parameter
     * value - the value of the parameter
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.getNamedQuery(String)
     *
     * @param queryString
     * @param paramName
     * @param value
     */

    public List<T> findByNamedParam(String queryString, String paramName, Object value) throws PersistenceException {
        List<T> result = null;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        if (paramName == null) {

            throw new PersistenceException("error.missing.argument.exception", "paramName");
        }
        if (value == null) {

            throw new PersistenceException("error.missing.argument.exception", "value");
        }

        try {
            result = getHibernateTemplate().findByNamedParam(queryString, paramName, value);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query for persistent instances, binding a number of values to ":" named parameters in the query string.
     * <p/>
     * Specified by:
     * findByNamedParam in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - a query expressed in Hibernate's query language
     * paramNames - the names of the parameters
     * values - the values of the parameters
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.getNamedQuery(String)
     *
     * @param queryString
     * @param paramNames
     * @param values
     */

    public List<T> findByNamedParam(String queryString, String[] paramNames, Object[] values) throws PersistenceException {
        List<T> result = null;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        if (paramNames == null) {

            throw new PersistenceException("error.missing.argument.exception", "paramNames");
        }
        if (values == null) {

            throw new PersistenceException("error.missing.argument.exception", "values");
        }

        try {
            result = getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query for persistent instances, binding the properties of the given bean to named parameters in the query string.
     * <p/>
     * Specified by:
     * findByValueBean in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - a query expressed in Hibernate's query language
     * valueBean - the values of the parameters
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Query.setProperties(java.lang.Object), Session.createQuery(java.lang.String)
     *
     * @param queryString
     * @param valueBean
     */

    public List<T> findByValueBean(String queryString, Object valueBean) throws PersistenceException {
        List<T> result = null;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        if (valueBean == null) {

            throw new PersistenceException("error.missing.argument.exception", "valueBean");
        }

        try {
            result = getHibernateTemplate().findByValueBean(queryString, valueBean);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a named query for persistent instances. A named query is defined in a Hibernate mapping file.
     * <p/>
     * Specified by:
     * findByNamedQuery in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryName - the name of a Hibernate query in a mapping file
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.getNamedQuery(String)
     *
     * @param queryName
     */

    public List<T> findByNamedQuery(String queryName) throws PersistenceException {
        List<T> result = null;

        if (queryName == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryName");
        }
        try {
            result = getHibernateTemplate().findByNamedQuery(getFullyQualifiedQueryName(queryName));
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a named query for persistent instances, binding one value to a "?" parameter in the query string. A named query is defined in a Hibernate mapping file.
     * <p/>
     * Specified by:
     * findByNamedQuery in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryName - the name of a Hibernate query in a mapping file
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.getNamedQuery(String)
     *
     * @param queryName
     * @param value
     */

    public List<T> findByNamedQuery(String queryName, Object value) throws PersistenceException {
        List<T> result = null;

        if (queryName == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryName");
        }
        if (value == null) {

            throw new PersistenceException("error.missing.argument.exception", "value");
        }

        try {
            result = getHibernateTemplate().findByNamedQuery(getFullyQualifiedQueryName(queryName), value);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a named query for persistent instances, binding a number of values to "?" parameters in the query string. A named query is defined in a Hibernate mapping file.
     * <p/>
     * Specified by:
     * findByNamedQuery in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryName - the name of a Hibernate query in a mapping file
     * values - the values of the parameters
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.getNamedQuery(String)
     *
     * @param queryName
     * @param values
     */

    public List<T> findByNamedQuery(String queryName, Object[] values) throws PersistenceException {
        List<T> result = null;

        if (queryName == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryName");
        }
        if (values == null) {

            throw new PersistenceException("error.missing.argument.exception", "values");
        }

        try {
            result = getHibernateTemplate().findByNamedQuery(getFullyQualifiedQueryName(queryName), values);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a named query for persistent instances, binding one value to a ":" named parameter in the query string. A named query is defined in a Hibernate mapping file.
     * <p/>
     * Specified by:
     * findByNamedQueryAndNamedParam in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryName - the name of a Hibernate query in a mapping file
     * paramName - the name of parameter
     * value - the value of the parameter
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.getNamedQuery(String)
     *
     * @param queryName
     * @param paramName
     * @param value
     */

    public List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) throws PersistenceException {
        List<T> result = null;

        if (queryName == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryName");
        }
        if (paramName == null) {

            throw new PersistenceException("error.missing.argument.exception", "paramName");
        }
        if (value == null) {

            throw new PersistenceException("error.missing.argument.exception", "value");
        }
        try {
            result = getHibernateTemplate().findByNamedQueryAndNamedParam(getFullyQualifiedQueryName(queryName), paramName, value);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a named query for persistent instances, binding a number of values to ":" named parameters in the query string. A named query is defined in a Hibernate mapping file.
     * <p/>
     * Specified by:
     * findByNamedQueryAndNamedParam in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryName - the name of a Hibernate query in a mapping file
     * paramNames - the names of the parameters
     * values - the values of the parameters
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.getNamedQuery(String)
     *
     * @param queryName
     * @param paramNames
     * @param values
     */

    public List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) throws PersistenceException {
        List<T> result = null;

        if (queryName == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryName");
        }
        if (paramNames == null) {

            throw new PersistenceException("error.missing.argument.exception", "paramNames");
        }
        if (values == null) {

            throw new PersistenceException("error.missing.argument.exception", "values");
        }

        try {
            result = getHibernateTemplate().findByNamedQueryAndNamedParam(getFullyQualifiedQueryName(queryName), paramNames, values);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a named query for persistent instances, binding the properties of the given bean to ":" named parameters in the query string. A named query is defined in a Hibernate mapping file.
     * <p/>
     * Specified by:
     * findByNamedQueryAndValueBean in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryName - the name of a Hibernate query in a mapping file
     * valueBean - the values of the parameters
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Query.setProperties(java.lang.Object), Session.getNamedQuery(String)
     *
     * @param queryName
     * @param valueBean
     */

    public List<T> findByNamedQueryAndValueBean(String queryName, Object valueBean) throws PersistenceException {
        List<T> result = null;

        if (queryName == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryName");
        }
        if (valueBean == null) {

            throw new PersistenceException("error.missing.argument.exception", "valueBean");
        }
        try {
            result = getHibernateTemplate().findByNamedQueryAndValueBean(getFullyQualifiedQueryName(queryName), valueBean);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query based on a given Hibernate criteria object.
     * <p/>
     * Specified by:
     * findByCriteria in interface HibernateOperations
     * <p/>
     * Parameters:
     * criteria - the detached Hibernate criteria object, which can for example be held in an instance variable of a DAO
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * DetachedCriteria.getExecutableCriteria(org.hibernate.Session)
     *
     * @param criteria
     */

    public List<T> findByCriteria(DetachedCriteria criteria) throws PersistenceException {
        List<T> result = null;

        if (criteria == null) {

            throw new PersistenceException("error.missing.argument.exception", "criteria");
        }

        try {
            result = getHibernateTemplate().findByCriteria(criteria);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query based on a given Hibernate criteria object.
     * <p/>
     * Specified by:
     * findByCriteria in interface HibernateOperations
     * <p/>
     * Parameters:
     * criteria - the detached Hibernate criteria object, which can for example be held in an instance variable of a DAO
     * firstResult - the index of the first result object to be retrieved (numbered from 0)
     * maxResults - the maximum number of result objects to retrieve (or <=0 for no limit)
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * DetachedCriteria.getExecutableCriteria(org.hibernate.Session), Criteria.setFirstResult(int), Criteria.setMaxResults(int)
     *
     * @param criteria
     * @param firstResult
     * @param maxResults
     */

    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) throws PersistenceException {
        List<T> result = null;

        if (criteria == null) {

            throw new PersistenceException("error.missing.argument.exception", "criteria");
        }
        try {
            result = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query based on a given example entity object.
     * <p/>
     * Specified by:
     * findByExample in interface HibernateOperations
     * <p/>
     * Parameters:
     * exampleEntity - an instance of the desired entity, serving as example for "query-by-example"
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Example.create(Object)
     *
     * @param exampleEntity
     */

    public List<T> findByExample(T exampleEntity) throws PersistenceException {
        List<T> result = null;

        if (exampleEntity == null) {

            throw new PersistenceException("error.missing.argument.exception", "exampleEntity");
        }
        try {
            result = getHibernateTemplate().findByExample(exampleEntity);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query based on a given example entity object.
     * <p/>
     * Specified by:
     * findByExample in interface HibernateOperations
     * <p/>
     * Parameters:
     * exampleEntity - an instance of the desired entity, serving as example for "query-by-example"
     * firstResult - the index of the first result object to be retrieved (numbered from 0)
     * maxResults - the maximum number of result objects to retrieve (or <=0 for no limit)
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Example.create(Object), Criteria.setFirstResult(int), Criteria.setMaxResults(int)
     *
     * @param exampleEntity
     * @param firstResult
     * @param maxResults
     */

    public List<T> findByExample(T exampleEntity, int firstResult, int maxResults) throws PersistenceException {
        List<T> result = null;

        if (exampleEntity == null) {

            throw new PersistenceException("error.missing.argument.exception", "exampleEntity");
        }
        try {
            result = getHibernateTemplate().findByExample(exampleEntity, firstResult, maxResults);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query for persistent instances.
     * <p/>
     * Returns the results as Iterator. Entities returned are initialized on demand. See Hibernate docs for details.
     * <p/>
     * Specified by:
     * iterate in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - a query expressed in Hibernate's query language
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.createQuery(java.lang.String), Query.iterate()
     *
     * @param queryString
     */

    public Iterator<T> iterate(String queryString) throws PersistenceException {
        Iterator<T> result = null;
        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        try {
            result = getHibernateTemplate().iterate(queryString);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query for persistent instances, binding one value to a "?" parameter in the query string.
     * <p/>
     * Returns the results as Iterator. Entities returned are initialized on demand. See Hibernate docs for details.
     * <p/>
     * Specified by:
     * iterate in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - a query expressed in Hibernate's query language
     * value - the value of the parameter
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.createQuery(java.lang.String), Query.iterate()
     *
     * @param queryString
     * @param value
     */

    public Iterator<T> iterate(String queryString, Object value) throws PersistenceException {
        Iterator<T> result = null;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        if (value == null) {

            throw new PersistenceException("error.missing.argument.exception", "value");
        }
        try {
            result = getHibernateTemplate().iterate(queryString, value);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Execute a query for persistent instances, binding a number of values to "?" parameters in the query string.
     * <p/>
     * Returns the results as Iterator. Entities returned are initialized on demand. See Hibernate docs for details.
     * <p/>
     * Specified by:
     * iterate in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - a query expressed in Hibernate's query language
     * values - the values of the parameters
     * Returns:
     * a List containing 0 or more persistent instances
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.createQuery(java.lang.String), Query.iterate()
     *
     * @param queryString
     * @param values
     */

    public Iterator<T> iterate(String queryString, Object[] values) throws PersistenceException {
        Iterator<T> result = null;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        if (values == null) {

            throw new PersistenceException("error.missing.argument.exception", "values");
        }

        try {
            result = getHibernateTemplate().iterate(queryString, values);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Close an Iterator created by iterate operations immediately, instead of waiting until the session is closed or disconnected.
     * <p/>
     * Specified by:
     * closeIterator in interface HibernateOperations
     * <p/>
     * Parameters:
     * it - the Iterator to close
     * Throws:
     * PersistenceException - if the Iterator could not be closed
     * See Also:
     * Hibernate.close(java.util.Iterator)
     *
     * @param it
     */
    public void closeIterator(Iterator<T> it) throws PersistenceException {

        if (it == null) {

            throw new PersistenceException("error.missing.argument.exception", "iterator");
        }
        try {
            getHibernateTemplate().closeIterator(it);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

    }

    /**
     * Description copied from interface: HibernateOperations
     * Update/delete all objects according to the given query. Return the number of entity instances updated/deleted.
     * <p/>
     * Specified by:
     * bulkUpdate in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - an update/delete query expressed in Hibernate's query language
     * Returns:
     * the number of instances updated/deleted
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.createQuery(java.lang.String), Query.executeUpdate()
     *
     * @param queryString
     */

    public int bulkUpdate(String queryString) throws PersistenceException {
        int result = -1;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        try {
            result = getHibernateTemplate().bulkUpdate(queryString);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Update/delete all objects according to the given query. Return the number of entity instances updated/deleted.
     * <p/>
     * Specified by:
     * bulkUpdate in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - an update/delete query expressed in Hibernate's query language
     * value - the value of the parameter
     * Returns:
     * the number of instances updated/deleted
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.createQuery(java.lang.String), Query.executeUpdate()
     *
     * @param queryString
     * @param value
     */
    public int bulkUpdate(String queryString, Object value) throws PersistenceException {
        int result = -1;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        if (value == null) {

            throw new PersistenceException("error.missing.argument.exception", "value");
        }
        try {
            result = getHibernateTemplate().bulkUpdate(queryString, value);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    /**
     * Description copied from interface: HibernateOperations
     * Update/delete all objects according to the given query. Return the number of entity instances updated/deleted.
     * <p/>
     * Specified by:
     * bulkUpdate in interface HibernateOperations
     * <p/>
     * Parameters:
     * queryString - an update/delete query expressed in Hibernate's query language
     * values - the values of the parameters
     * Returns:
     * the number of instances updated/deleted
     * Throws:
     * PersistenceException - in case of Hibernate errors
     * See Also:
     * Session.createQuery(java.lang.String), Query.executeUpdate()
     *
     * @param queryString
     * @param values
     */
    public int bulkUpdate(String queryString, Object[] values) throws PersistenceException {
        int result = -1;

        if (queryString == null) {

            throw new PersistenceException("error.missing.argument.exception", "queryString");
        }
        if (values == null) {

            throw new PersistenceException("error.missing.argument.exception", "values");
        }

        try {
            result = getHibernateTemplate().bulkUpdate(queryString, values);
        } catch (DataAccessException ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        } catch (Exception ex) {

            throw new PersistenceException("error.database.error", ex, ex.getMessage());
        }

        return result;
    }

    // Spring IoC
    private PlatformTransactionManager platformTransactionManager = null;

    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }
}
