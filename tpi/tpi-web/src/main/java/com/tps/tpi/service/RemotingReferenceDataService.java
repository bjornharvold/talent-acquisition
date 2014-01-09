package com.tps.tpi.service;

import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.dto.*;
import com.tps.tpi.model.objects.entity.*;
import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 2:35:45 PM
 * Responsibility: Takes care of managing every piece of reference data in the system
 */
public interface RemotingReferenceDataService {

    @Transactional
    @Secured("ROLE_USER")
    List<CityDto> getCities(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    CityDto getCityByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<IndustryDto> getIndustries(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    IndustryDto getIndustryByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<IndustryGroupDto> getIndustryGroups(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    IndustryGroupDto getIndustryGroupByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    TitleDto getTitleByShortName(String shortName) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    TitleDto getTitleByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<TitleDto> getTitles(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<SkillDto> getSkills(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    CertificationDto getCertificationByShortName(String shortName) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    SchoolDto getSchoolByShortName(String shortName) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<RegionDto> getRegions(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<RegionDto> getRegionsWithChildren(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    RegionDto getRegionByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<CountryDto> getCountries(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    CountryDto getCountryByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    SkillGroupDto getSkillGroupByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<SkillGroupDto> getSkillGroups(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    SkillDto getSkillByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    SkilledRoleGroup getSkilledRoleGroupByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    SkilledRole getSkilledRoleByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")List<SkilledRoleGroup> getSkilledRoleGroups(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<SkilledRole> getSkilledRoles(String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_USER")
    List<String> getTimeZones() throws DomainException;

    @Secured("ROLE_USER")
    List<String> getLanguages() throws DomainException;

    @Secured("ROLE_USER")
    List<String> getLanguageLevels() throws DomainException;

    @Secured("ROLE_USER")
    List<String> getRaces() throws DomainException;
}