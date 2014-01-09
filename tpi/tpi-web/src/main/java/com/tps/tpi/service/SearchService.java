package com.tps.tpi.service;

import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.Search;
import com.tps.tpi.model.objects.entity.Person;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 22, 2009
 * Time: 9:39:27 AM
 * Responsibility: This Service will be instrumental for everything SEARCH
 */
public interface SearchService {
    Search search(Search search, Integer index, Integer maxResults) throws DomainException;
    Search searchIds(Search search, Integer index, Integer maxResults) throws DomainException;
    Integer searchCount(Search search) throws DomainException;
    void purgeSearchIndex(Class aClass) throws DomainException;
    void optimize();
}
