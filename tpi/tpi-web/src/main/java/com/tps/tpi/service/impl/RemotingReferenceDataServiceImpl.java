package com.tps.tpi.service.impl;

import com.tps.tpi.cache.CacheManager;
import com.tps.tpi.exception.CacheException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.dto.*;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Region;
import com.tps.tpi.model.objects.entity.SkilledRole;
import com.tps.tpi.model.objects.entity.SkilledRoleGroup;
import com.tps.tpi.service.ReferenceDataService;
import com.tps.tpi.service.RemotingReferenceDataService;
import com.tps.tpi.utils.IdComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 22, 2009
 * Time: 11:34:04 PM
 * Responsibility:
 */
@Service
@RemotingDestination(channels = {"my-amf", "my-secure-amf"})
public class RemotingReferenceDataServiceImpl implements RemotingReferenceDataService {
    private final static Logger log = LoggerFactory.getLogger(RemotingReferenceDataServiceImpl.class);
    private final ReferenceDataService referenceDataService;
    private final CacheManager cacheManager;
    private static final String REGION_PREFIX = "region_";

    @Autowired
    public RemotingReferenceDataServiceImpl(ReferenceDataService referenceDataService, CacheManager cacheManager) {
        this.referenceDataService = referenceDataService;
        this.cacheManager = cacheManager;
    }

    @Override
    @RemotingInclude
    public List<CityDto> getCities(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public CityDto getCityByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<IndustryDto> getIndustries(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public IndustryDto getIndustryByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<IndustryGroupDto> getIndustryGroups(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public IndustryGroupDto getIndustryGroupByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public TitleDto getTitleByShortName(String shortName) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public TitleDto getTitleByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<TitleDto> getTitles(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<SkillDto> getSkills(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public CertificationDto getCertificationByShortName(String shortName) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public SchoolDto getSchoolByShortName(String shortName) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<RegionDto> getRegions(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<RegionDto> getRegionsWithChildren(String name, Integer index, Integer maxResults) throws DomainException {
        List<RegionDto> result = null;

        try {
            List<String> ids = referenceDataService.getRegionIds(name, index, maxResults);
            Method method = referenceDataService.getClass().getMethod("getRegions", new Class[]{List.class});

            result = convertIdsToEntitiesAndMap(ids, RegionDto.class, Region.class, method, REGION_PREFIX);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    @RemotingInclude
    public RegionDto getRegionByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<CountryDto> getCountries(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public CountryDto getCountryByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public SkillGroupDto getSkillGroupByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<SkillGroupDto> getSkillGroups(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public SkillDto getSkillByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public SkilledRoleGroup getSkilledRoleGroupByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public SkilledRole getSkilledRoleByCode(String code) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<SkilledRoleGroup> getSkilledRoleGroups(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RemotingInclude
    public List<SkilledRole> getSkilledRoles(String name, Integer index, Integer maxResults) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getTimeZones() throws DomainException {
        return referenceDataService.getTimeZones();
    }

    @Override
    public List<String> getLanguages() throws DomainException {
        return referenceDataService.getLanguages();
    }

    @Override
    public List<String> getLanguageLevels() throws DomainException {
        return referenceDataService.getLanguageLevels();
    }

    @Override
    public List<String> getRaces() throws DomainException {
        return referenceDataService.getRaces();
    }

    private <T extends AbstractDto, E extends AbstractEntity> List<T> convertIdsToEntitiesAndMap(List<String> entityIds, Class<T> to, Class<E> from, Method method, String cachePrefix) throws DomainException {
        List<T> result = null;

        try {
            if (entityIds != null) {
                result = new ArrayList<T>(entityIds.size());
                List<String> notCachedIds = null;
                T dto = null;

                for (String id : entityIds) {
                    String cacheKey = cachePrefix + id;

                    if (log.isDebugEnabled()) {
                        log.debug("Checking to see if " + to + " with id: " + cacheKey + " is in cache...");
                    }

                    dto = cacheManager.getFromCache(cacheKey, to);

                    if (dto == null) {
                        if (log.isDebugEnabled()) {
                            log.debug(to + " with id: " + cacheKey + " is NOT in cache. Retrieving from database...");
                        }

                        // save up all non cached ids and then query the database for a collection
                        if (notCachedIds == null) {
                            notCachedIds = new ArrayList<String>();
                        }

                        notCachedIds.add(id);
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug(to + " with id: " + cacheKey + " is already in cache");
                        }

                        // existing cached region we add to results
                        result.add(dto);
                    }
                }

                if (notCachedIds != null) {

                    List<E> entityList = (List<E>) ReflectionUtils.invokeMethod(method, referenceDataService, notCachedIds);

                    for (E entity : entityList) {
                        // put each one in the cache
                        result.add(cacheManager.mapEntityToDtoAndCache(cachePrefix, entity, to));
                    }
                }
            }

            // sort the list by relevancy
            Collections.sort(result, new IdComparator(entityIds));

        } catch (CacheException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }
}
