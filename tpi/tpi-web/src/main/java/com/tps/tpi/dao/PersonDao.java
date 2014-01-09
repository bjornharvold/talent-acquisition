package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Person;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 9:43:20 AM
 * Responsibility:
 */
public interface PersonDao extends GenericDao<Person, String>{
    Person getPersonByUserId(String userId) throws PersistenceException;
    Person getPersonByUsername(String username) throws PersistenceException;
    void updateLastLogin(String username) throws PersistenceException;
    List<Person> getPersons(List<Object[]> personIdsWithRelevancy) throws PersistenceException;
}
