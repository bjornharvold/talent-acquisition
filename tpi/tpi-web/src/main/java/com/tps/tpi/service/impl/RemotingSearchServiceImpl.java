package com.tps.tpi.service.impl;

import com.tps.tpi.cache.CacheManager;
import com.tps.tpi.model.objects.dto.SearchComponentDto;
import com.tps.tpi.model.objects.dto.SearchComponentGroupDto;
import com.tps.tpi.model.objects.dto.SearchDto;
import com.tps.tpi.model.objects.entity.Search;
import com.tps.tpi.service.ProfileService;
import com.tps.tpi.service.RemotingSearchService;
import com.tps.tpi.service.SearchService;
import com.tps.tpi.model.objects.lite.PersonLite;
import com.tps.tpi.model.objects.entity.Person;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.exception.CacheException;
import com.tps.tpi.cache.impl.OSCacheManager;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Dec 2, 2009
 * Time: 3:18:57 AM
 * Responsibility: Queries SearchService for Person entities and converts them to lighter objects for remoting
 */
@Service("remotingSearchService")
@RemotingDestination(channels = {"my-amf", "my-secure-amf"})
public class RemotingSearchServiceImpl implements RemotingSearchService {
    private static final Logger log = LoggerFactory.getLogger(RemotingSearchServiceImpl.class);
    private final SearchService searchService;
    private final Mapper mapper;
    private final static Integer INDEX = 0;
    private final static Integer MAXRESULTS = 20;

    @Autowired
    public RemotingSearchServiceImpl(SearchService searchService, OSCacheManager cacheManager,
                                     Mapper mapper, ProfileService profileService) {
        this.searchService = searchService;
        this.mapper = mapper;
    }

    /**
     * This is a VERY expensive call. THE most expensive call in the system.
     * Here's a rundown of what happens:
     * 1. Search request comes in
     * 2. We convert the dto over to an entity and search for persons who match the criteria
     * 3. We get the ids of the person who match back
     * 4. Then we loop through this list to see which one of these people are in the cache and which need to be loaded from the db
     * 5. Finally we convert the dto back
     *
     * @param dto
     * @param index
     * @param maxResults
     * @return
     * @throws DomainException
     */
    @Override
    @RemotingInclude
    public SearchDto search(SearchDto dto, Integer index, Integer maxResults) throws DomainException {

        if (dto == null) {
            throw new DomainException("error.missing.data", "dto");
        }

        if (index == null) {
            index = INDEX;
        }
        if (maxResults == null) {
            maxResults = MAXRESULTS;
        }

        printTraceInformation(dto);

        Search search = searchService.searchIds(mapper.map(dto, Search.class), index, maxResults);

        return mapper.map(search, SearchDto.class);
    }

    @Override
    @RemotingInclude
    public Integer searchCount(SearchDto dto) throws DomainException {
        if (dto == null) {
            throw new DomainException("error.missing.data", "dto");
        }

        printTraceInformation(dto);
        
        return searchService.searchCount(mapper.map(dto, Search.class));
    }

    private void printTraceInformation(SearchDto dto) {
        if (log.isTraceEnabled()) {
            if (dto.getGroups() != null) {
                log.trace("Incoming search component group dtos...");
                for (SearchComponentGroupDto groupDto : dto.getGroups()) {
                    log.trace(groupDto.getType());

                    if (groupDto.getComponents() != null) {
                        for (SearchComponentDto componentDto : groupDto.getComponents()) {
                            log.trace("Here's a component...");
                            if (componentDto.getSearchMap() != null) {
                                log.trace("Contents of component map: ");
                                for (String s : componentDto.getSearchMap().keySet()) {
                                    log.trace(s + " : " + componentDto.getSearchMap().get(s));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
