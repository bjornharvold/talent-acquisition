package com.tps.tpi.converter;

import com.tps.tpi.cache.CacheManager;
import com.tps.tpi.dao.CompanyDao;
import com.tps.tpi.dao.UserDao;
import com.tps.tpi.exception.CacheException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.dto.*;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.lite.PersonLite;
import com.tps.tpi.service.ProfileService;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Search entity to a SearchDto dto. We are assuming the Search entity will ALWAYS
 * be created before the dto so when we convert back to the entity, we don't have to update every field because they will
 * always be there.
 */
public class SearchConverter extends AbstractConverter {
    private static final Logger log = LoggerFactory.getLogger(SearchConverter.class);
    private static final String CACHE_PREFIX = "lite_";

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Search && dto instanceof SearchDto) {
            Search entity = (Search) source;

            ((SearchDto) dto).setHitCount(entity.getHitCount());
            ((SearchDto) dto).setName(entity.getName());

            if (entity.getUser() != null) {
                ((SearchDto) dto).setUser(entity.getUser().getId());
            }

            if (entity.getGroups() != null) {
                for (SearchComponentGroup group : entity.getGroups()) {
                    ((SearchDto) dto).addGroup(mapper.map(group, SearchComponentGroupDto.class));
                }
            }

            if (entity.getPersonIdsWithRelevancy() != null) {
                ((SearchDto) dto).setPersons(handleResultIds(entity.getPersonIdsWithRelevancy()));
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof SearchDto && entity instanceof Search) {
                SearchDto dto = (SearchDto) source;

                if (StringUtils.isNotBlank(dto.getUser())) {
                    ((Search) entity).setUser(userDao.get(User.class, dto.getUser()));
                }

                if (dto.getGroups() != null) {
                    for (SearchComponentGroupDto group : dto.getGroups()) {
                        ((Search) entity).addGroup(mapper.map(group, SearchComponentGroup.class));
                    }
                }
            }
        } catch (PersistenceException e) {
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    /**
     * This method gets a list of person ids. It will loop through all ids to see if they exist in the cache already.
     * If they don't, it'll load the person entity from the database, map it to the required object and cache it.
     *
     * @param personIdsAndRelevancy
     * @return
     * @throws com.tps.tpi.exception.CacheException
     *
     * @throws com.tps.tpi.exception.DomainException
     *
     */
    private List<PersonLite> handleResultIds(List<Object[]> personIdsAndRelevancy) throws MappingException {
        List<PersonLite> result = null;

        try {
            if (personIdsAndRelevancy != null) {
                result = new ArrayList<PersonLite>(personIdsAndRelevancy.size());
                List<Object[]> notCachedIds = null;
                PersonLite lite = null;

                for (Object[] person : personIdsAndRelevancy) {
                    String id = (String) person[0];
                    Float normalizedRelevancy = (Float) person[1];

                    String cacheKey = CACHE_PREFIX + id;

                    if (log.isDebugEnabled()) {
                        log.debug("Checking to see if person with id: " + cacheKey + " is in cache...");
                    }

                    lite = cacheManager.getFromCache(cacheKey, PersonLite.class);

                    if (lite == null) {
                        if (log.isDebugEnabled()) {
                            log.debug("PersonLite with id: " + cacheKey + " is NOT in cache. Retrieving from database...");
                        }

                        // save up all non cached ids and then query the database for a collection
                        if (notCachedIds == null) {
                            notCachedIds = new ArrayList<Object[]>();
                        }

                        Object[] notCacheId = {id, normalizedRelevancy};
                        notCachedIds.add(notCacheId);
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("PersonLite with id: " + cacheKey + " is already in cache");
                        }

                        // existing cached person we add to results
                        result.add(lite);
                    }
                }

                if (notCachedIds != null) {
                    // now we grab the person collection
                    List<Person> persons = profileService.getPersons(notCachedIds);

                    for (Person person : persons) {
                        // put each one in the cache
                        result.add(cacheManager.mapEntityToDtoAndCache(CACHE_PREFIX, person, PersonLite.class));
                    }
                }
            }
            // sort the list by relevancy
            Collections.sort(result);
        } catch (CacheException e) {
            new MappingException(e.getMessage(), e);
        } catch (DomainException e) {
            new MappingException(e.getMessage(), e);
        }


        return result;
    }

    // Spring IoC
    @Autowired
    private ProfileService profileService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;
}