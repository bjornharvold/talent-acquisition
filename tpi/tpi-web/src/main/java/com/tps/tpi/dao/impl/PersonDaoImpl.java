package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.PersonDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Person;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 9:44:56 AM
 * Responsibility:
 */
@Repository("personDao")
public class PersonDaoImpl extends AbstractHibernateDao<Person, String> implements PersonDao {
    @Override
    public Person getPersonByUserId(String userId) throws PersistenceException {
        Criteria c = getSession().createCriteria(Person.class);
        c.createAlias("user", "u").add(Restrictions.eq("u.id", userId));

        return (Person) c.uniqueResult();
    }

    @Override
    public Person getPersonByUsername(String username) throws PersistenceException {
        Criteria c = getSession().createCriteria(Person.class);
        c.createAlias("user", "u").add(Restrictions.eq("u.username", username));

        return (Person) c.uniqueResult();
    }

    @Override
    public void updateLastLogin(String username) throws PersistenceException {
        Person p = getPersonByUsername(username);

        if (p == null) {
            throw new PersistenceException("Cannot find person by username: " + username);
        }

        Query q = getSession().createQuery("update Person p set p.lastLogin = :lastLogin where p.id = :id");
        q.setParameter("lastLogin", new Date());
        q.setParameter("id", p.getId());

        q.executeUpdate();
    }

    /**
     * match on a list of person ids
     * Object[] is 0: personId, 1: relevancy from lucene
     * @param personIdsWithRelevancy
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Person> getPersons(List<Object[]> personIdsWithRelevancy) throws PersistenceException {
        Criteria c = getSession().createCriteria(Person.class);

        Disjunction d =  Restrictions.disjunction();
        for (Object[] objects : personIdsWithRelevancy) {
            d.add(Restrictions.eq("id", objects[0]));
        }

        c.add(d);

        return c.list();
    }
}
