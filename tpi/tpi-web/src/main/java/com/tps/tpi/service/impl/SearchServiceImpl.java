package com.tps.tpi.service.impl;

import com.tps.tpi.dao.SearchDao;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Search;
import com.tps.tpi.model.objects.entity.Person;
import com.tps.tpi.model.objects.entity.SearchComponent;
import com.tps.tpi.model.objects.entity.SearchComponentGroup;
import com.tps.tpi.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 22, 2009
 * Time: 1:58:08 PM
 * Responsibility:
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {
    private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);
    private final static Integer INDEX = 0;
    private final static Integer MAXRESULTS = 20;
    private final SearchDao searchDao;

    @Autowired
    public SearchServiceImpl(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    /**
     * Expects a list of advanced search components. One component is a single component for searching, such as name
     * The method expects the component to have a type so as to know how to process the incoming params map with values.
     *
     * @param search
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    @Override
    public Search search(Search search, Integer index, Integer maxResults) throws DomainException {

        if (search == null) {
            throw new DomainException("error.missing.data", "search");
        }

        try {
            search = searchDao.searchEntities(search, index, maxResults);

            if (search.getRawSearchResults() != null) {
                search.setPersons(processPersonCentricSearchResults(search.getRawSearchResults()));

                // this is a good time to save the search
                search = searchDao.save(search);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return search;
    }

    /**
     * Expects a list of advanced search components. One component is a single component for searching, such as name
     * The method expects the component to have a type so as to know how to process the incoming params map with values.
     *
     * @param search
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    @Override
    public Search searchIds(Search search, Integer index, Integer maxResults) throws DomainException {

        if (search == null) {
            throw new DomainException("error.missing.data", "search");
        }

        try {
            search = searchDao.searchIds(search, index, maxResults);

            if (search.getRawSearchResults() != null) {
                search.setPersonIdsWithRelevancy(processPersonIdCentricSearchResults(search.getRawSearchResults()));

                // good time to save the search
                searchDao.saveOrUpdate(search);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return search;
    }

    /**
     * Expects a list of advanced search components. One component is a single component for searching, such as name
     * The method expects the component to have a type so as to know how to process the incoming params map with values.
     *
     * @param search
     * @return result count
     * @throws DomainException
     */
    @Override
    public Integer searchCount(Search search) throws DomainException {
        Integer result = null;

        if (search == null) {
            throw new DomainException("error.missing.data", "search");
        }

        try {
            result = searchDao.searchCount(search);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public void purgeSearchIndex(Class aClass) throws DomainException {
        if (aClass == null) {
            throw new DomainException("error.missing.data", "aClass");
        }

        try {
            searchDao.purgeSearchIndex(aClass);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void optimize() {
        if (log.isTraceEnabled()) {
            log.trace("Optimizing entire Search index");
        }

        searchDao.optimize();

        if (log.isTraceEnabled()) {
            log.trace("Optimization complete");
        }
    }

    /**
     * Depending on how we have Hibernate Search set up, it will return some objects that most likely are not the same
     * This method will find the person object in all of that and map it to a thin version of that person to return.
     *
     * @param list
     * @return
     */
    private List<Person> processPersonCentricSearchResults(List<Object[]> list) {
        List<Person> result = null;

        // now we flatten
        if (list != null) {
            result = new ArrayList<Person>(list.size());

            Float averageRelevancy = 0F;
            Float topScore = 0F;

            for (int i = 0; i < list.size(); i++) {
                Object[] person = list.get(i);
                Float relevancy = (Float) person[1];

                if (relevancy > topScore) {
                    topScore += relevancy;
                }
            }

            if (log.isTraceEnabled()) {
                log.trace("Top score for search is: " + topScore);
            }

            for (Object[] person : list) {
                Person p = (Person) person[0];
                Float luceneRelevancy = (Float) person[1];
                Float normalizedRelevancy = luceneRelevancy / topScore;

                if (log.isTraceEnabled()) {
                    log.trace("Normalized relevancy for person with id: " + p.getId() + " = " + normalizedRelevancy);
                }
                p.setRelevancy(normalizedRelevancy);
                result.add(p);
            }
        }

        return result;
    }

    /**
     * Depending on how we have Hibernate Search set up, it will return some objects that most likely are not the same
     * This method will find the person object in all of that and map it to a thin version of that person to return.
     *
     * @param list
     * @return
     */
    private List<Object[]> processPersonIdCentricSearchResults(List<Object[]> list) {

        // now we flatten
        if (list != null) {

            Float averageRelevancy;
            Float topScore = 0F;

            for (int i = 0; i < list.size(); i++) {
                Object[] person = list.get(i);
                Float relevancy = (Float) person[1];

                if (relevancy > topScore) {
                    topScore = relevancy;
                }
            }

            if (log.isTraceEnabled()) {
                log.trace("Top score for search is: " + topScore);
            }

            for (Object[] person : list) {
                String id = (String) person[0];
                Float luceneRelevancy = (Float) person[1];
                Float normalizedRelevancy = luceneRelevancy / topScore;

                if (log.isTraceEnabled()) {
                    log.trace("Normalized relevancy for person with id: " + id + " = " + normalizedRelevancy);
                }
                person[1] = normalizedRelevancy;
            }
        }

        return list;
    }
}
