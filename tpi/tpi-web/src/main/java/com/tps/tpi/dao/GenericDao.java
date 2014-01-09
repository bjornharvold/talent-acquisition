/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 28, 2005
 * Time: 9:09:58 AM
 * <p/>
 * <p/>
 * Description: Core interface for all data access objects
 */
public interface GenericDao<T, ID extends Serializable> {

    T findById(ID id, boolean lock) throws PersistenceException;

    T findById(ID id) throws PersistenceException;

    List<T> findAll() throws PersistenceException;

    T findObjectByExample(T exampleInstance) throws PersistenceException;

    List<T> findByExample(T exampleInstance, String[] excludeProperty) throws PersistenceException;

    void executeUpdate(final String queryName) throws PersistenceException;

    T findObjectByNamedQuery(String queryName) throws PersistenceException;

    void delete(ID id) throws PersistenceException;

    /**
     * Set if a new Session should be created when no transactional Session can be found for the current thread.
     * HibernateTemplate is aware of a corresponding Session bound to the current thread, for example when using HibernateTransactionManager. If allowCreate is true, a new non-transactional Session will be created if none found, which needs to be closed at the end of the operation. If false, an IllegalStateException will get thrown in this case.
     * See Also:
     * SessionFactoryUtils.getSession(SessionFactory, boolean)
     *
     * @param allowCreate
     */
    public void setAllowCreate(boolean allowCreate) throws PersistenceException;

    /**
     * Return if a new Session should be created if no thread-bound found.
     *
     * @return boolean
     */
    public boolean isAllowCreate() throws PersistenceException;

    /**
     * Set whether to always use a new Hibernate Session for this template. Default is "false"; if activated, all operations on this template will work on a new Hibernate Session even in case of a pre-bound Session (for example, within a transaction or OpenSessionInViewFilter).
     * Within a transaction, a new Hibernate Session used by this template will participate in the transaction through using the same JDBC Connection. In such a scenario, multiple Sessions will participate in the same database transaction.
     * Turn this on for operations that are supposed to always execute independently, without side effects caused by a shared Hibernate Session.
     *
     * @param alwaysUseNewSession
     */
    public void setAlwaysUseNewSession(boolean alwaysUseNewSession) throws PersistenceException;

    /**
     * Return whether to always use a new Hibernate Session for this template.
     *
     * @return boolean
     */
    public boolean isAlwaysUseNewSession() throws PersistenceException;

    /**
     * Set whether to expose the native Hibernate Session to HibernateCallback code. Default is "false": a Session proxy will be returned, suppressing close calls and automatically applying query cache settings and transaction timeouts.
     * See Also:
     * HibernateCallback, Session, setCacheQueries(boolean), setQueryCacheRegion(java.lang.String), prepareQuery(org.hibernate.Query), prepareCriteria(org.hibernate.Criteria)
     *
     * @param exposeNativeSession
     */
    public void setExposeNativeSession(boolean exposeNativeSession) throws PersistenceException;

    /**
     * Return whether to expose the native Hibernate Session to HibernateCallback code, or rather a Session proxy.
     *
     * @return boolean
     */
    public boolean isExposeNativeSession() throws PersistenceException;

    /**
     * Set whether to check that the Hibernate Session is not in read-only mode in case of write operations (save/update/delete).
     * Default is "true", for fail-fast behavior when attempting write operations within a read-only transaction. Turn this off to allow save/update/delete on a Session with flush mode NEVER.
     * See Also:
     * HibernateAccessor.setFlushMode(int), checkWriteOperationAllowed(org.hibernate.Session), TransactionDefinition.isReadOnly()
     *
     * @param checkWriteOperations
     */
    public void setCheckWriteOperations(boolean checkWriteOperations) throws PersistenceException;

    /**
     * Return whether to check that the Hibernate Session is not in read-only mode in case of write operations (save/update/delete).
     *
     * @return boolean
     */
    public boolean isCheckWriteOperations() throws PersistenceException;

    /**
     * Set whether to cache all queries executed by this template. If this is true, all Query and Criteria objects created by this template will be marked as cacheable (including all queries through find methods).
     * To specify the query region to be used for queries cached by this template, set the "queryCacheRegion" property.
     * See Also:
     * setQueryCacheRegion(java.lang.String), Query.setCacheable(boolean), Criteria.setCacheable(boolean)
     *
     * @param cacheQueries
     */
    public void setCacheQueries(boolean cacheQueries) throws PersistenceException;

    /**
     * Return whether to cache all queries executed by this template.
     *
     * @return boolean
     */
    public boolean isCacheQueries() throws PersistenceException;

    /**
     * Set the name of the cache region for queries executed by this template. If this is specified, it will be applied to all Query and Criteria objects created by this template (including all queries through find methods).
     * The cache region will not take effect unless queries created by this template are configured to be cached via the "cacheQueries" property.
     * See Also:
     * setCacheQueries(boolean), Query.setCacheRegion(java.lang.String), Criteria.setCacheRegion(java.lang.String)
     *
     * @param queryCacheRegion
     */
    public void setQueryCacheRegion(String queryCacheRegion) throws PersistenceException;

    /**
     * Return the name of the cache region for queries executed by this template.
     *
     * @return String
     */
    public String getQueryCacheRegion() throws PersistenceException;

    /**
     * Set the fetch size for this HibernateTemplate. This is important for processing large result sets: Setting this higher than the default value will increase processing speed at the cost of memory consumption; setting this lower can avoid transferring row data that will never be read by the application.
     * Default is 0, indicating to use the JDBC driver's default.
     *
     * @param fetchSize
     */
    public void setFetchSize(int fetchSize) throws PersistenceException;

    /**
     * Return the fetch size specified for this HibernateTemplate.
     *
     * @return int
     */
    public int getFetchSize() throws PersistenceException;

    /**
     * Set the maximum number of rows for this HibernateTemplate. This is important for processing subsets of large result sets, avoiding to read and hold the entire result set in the database or in the JDBC driver if we're never interested in the entire result in the first place (for example, when performing searches that might return a large number of matches).
     * Default is 0, indicating to use the JDBC driver's default.
     *
     * @param maxResults
     */
    public void setMaxResults(int maxResults) throws PersistenceException;

    /**
     * Return the maximum number of rows specified for this HibernateTemplate.
     *
     * @return int
     */
    public int getMaxResults() throws PersistenceException;

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
    public T get(Class entityClass, ID id) throws PersistenceException;

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
    public T get(String entityName, ID id) throws PersistenceException;

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
    public T load(Class entityClass, ID id) throws PersistenceException;

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
    public T load(String entityName, ID id) throws PersistenceException;

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
    public void load(T entity, ID id) throws PersistenceException;

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
    public void refresh(T entity) throws PersistenceException;

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
    public boolean contains(T entity) throws PersistenceException;

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
    public void evict(T entity) throws PersistenceException;

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
    public void initialize(Object proxy) throws PersistenceException;

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
    public T save(T entity) throws PersistenceException;

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
    public T save(String entityName, T entity) throws PersistenceException;

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
    public T update(T entity) throws PersistenceException;

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
    public T update(String entityName, T entity) throws PersistenceException;

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
    public T saveOrUpdate(T entity) throws PersistenceException;

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
    public T saveOrUpdate(String entityName, T entity) throws PersistenceException;

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
    public void saveOrUpdateAll(Collection<T> entities) throws PersistenceException;

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
    public T persist(T entity) throws PersistenceException;

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
    public T persist(String entityName, T entity) throws PersistenceException;

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
    public T merge(T entity) throws PersistenceException;

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
    public T merge(String entityName, T entity) throws PersistenceException;

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
    public void delete(T entity) throws PersistenceException;

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
    public void deleteAll(Collection<T> entities) throws PersistenceException;

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
     *

     */
    public void flush() throws PersistenceException;

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
     *
     */
    public void clear() throws PersistenceException;

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
    public List<T> find(String queryString) throws PersistenceException;

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
    public List<T> find(String queryString, Object value) throws PersistenceException;

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
    public List<T> find(String queryString, Object[] values) throws PersistenceException;

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
    public List<T> findByNamedParam(String queryString, String paramName, Object value) throws PersistenceException;

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
    public List<T> findByNamedParam(String queryString, String[] paramNames, Object[] values) throws PersistenceException;

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
    public List<T> findByValueBean(String queryString, Object valueBean) throws PersistenceException;

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
    public List<T> findByNamedQuery(String queryName) throws PersistenceException;

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
    public List<T> findByNamedQuery(String queryName, Object value) throws PersistenceException;

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
    public List<T> findByNamedQuery(String queryName, Object[] values) throws PersistenceException;

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
    public List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) throws PersistenceException;

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
    public List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) throws PersistenceException;

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
    public List<T> findByNamedQueryAndValueBean(String queryName, Object valueBean) throws PersistenceException;

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
    public List<T> findByExample(T exampleEntity) throws PersistenceException;

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
    public List<T> findByExample(T exampleEntity, int firstResult, int maxResults) throws PersistenceException;

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
    public Iterator<T> iterate(String queryString) throws PersistenceException;

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
    public Iterator<T> iterate(String queryString, Object value) throws PersistenceException;

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
    public Iterator<T> iterate(String queryString, Object[] values) throws PersistenceException;

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
    public void closeIterator(Iterator<T> it) throws PersistenceException;

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
    public int bulkUpdate(String queryString) throws PersistenceException;

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
    public int bulkUpdate(String queryString, Object value) throws PersistenceException;

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
    public int bulkUpdate(String queryString, Object[] values) throws PersistenceException;
}
