package com.tps.tpi.service;

import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.*;

import java.util.List;
import java.util.TimeZone;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 2:35:45 PM
 * Responsibility: Takes care of managing every piece of reference data in the system
 */
public interface ReferenceDataService {
    
    @Transactional
    @Secured("ROLE_USER")
    City saveCity(City city) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    City updateCity(City city) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    void deleteCity(String id) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Address saveAddress(Address address) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Address updateAddress(Address address) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    void deleteAddress(String id) throws DomainException;

    @Secured("ROLE_USER")
    List<City> getCities(String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_USER")
    City getCityByCode(String code) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Industry saveIndustry(Industry industry) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Industry updateIndustry(Industry industry) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    void deleteIndustry(String id) throws DomainException;

    @Secured("ROLE_USER")
    List<Industry> getIndustries(String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_USER")
    Industry getIndustryByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    IndustryGroup saveIndustryGroup(IndustryGroup industryGroup) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    IndustryGroup updateIndustryGroup(IndustryGroup industryGroup) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    void deleteIndustryGroup(String id) throws DomainException;

    @Secured("ROLE_USER")
    List<IndustryGroup> getIndustryGroups(String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_USER")
    IndustryGroup getIndustryGroupByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Title saveTitle(Title title) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Title updateTitle(Title title) throws DomainException;

    @Secured("ROLE_USER")
    Title getTitleByShortName(String shortName) throws DomainException;

    @Secured("ROLE_USER")
    Title getTitleByCode(String code) throws DomainException;

    @Secured("ROLE_USER")
    List<Title> getTitles(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteTitle(String id) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Skill saveSkill(Skill skill) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Skill updateSkill(Skill skill) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    void deleteSkill(String id) throws DomainException;

    @Secured("ROLE_USER")
    List<Skill> getSkills(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Certification saveCertification(Certification cert) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Certification updateCertification(Certification cert) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    void deleteCertification(String id) throws DomainException;

    @Secured("ROLE_USER")
    Certification getCertificationByShortName(String shortName) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    School saveSchool(School school) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    School updateSchool(School school) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    void deleteSchool(String id) throws DomainException;

    @Secured("ROLE_USER")
    School getSchoolByShortName(String shortName) throws DomainException;

    @Secured("ROLE_USER")
    List<Region> getRegions(String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_USER")
    List<Region> getRegions(List<String> ids) throws DomainException;

    @Secured("ROLE_USER")
    List<String> getRegionIds(String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_USER")
    Region getRegionByCode(String code) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Region saveRegion(Region region) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Region updateRegion(Region region) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteRegion(String id) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Country saveCountry(Country country) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    Country updateCountry(Country country) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteCountry(String id) throws DomainException;

    @Secured("ROLE_USER")
    List<Country> getCountries(String name, Integer index, Integer maxResults) throws DomainException;

    @Secured("ROLE_USER")
    Country getCountryByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    State saveState(State state) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    State updateState(State state) throws DomainException;
    
    @Transactional
    @Secured("ROLE_USER")
    void deleteState(String id) throws DomainException;

    @Secured("ROLE_USER")
    SkillGroup getSkillGroupByCode(String code) throws DomainException;

    @Secured("ROLE_USER")
    List<SkillGroup> getSkillGroups(String name, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    SkillGroup saveSkillGroup(SkillGroup sg) throws DomainException;

    @Secured("ROLE_USER")
    Skill getSkillByCode(String code) throws DomainException;

    @Secured("ROLE_USER")
    SkilledRoleGroup getSkilledRoleGroupByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    SkilledRoleGroup saveSkilledRoleGroup(SkilledRoleGroup prg) throws DomainException;

    @Secured("ROLE_USER")
    SkilledRole getSkilledRoleByCode(String code) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    SkilledRole saveSkilledRole(SkilledRole pr) throws DomainException;

    @Secured("ROLE_USER")
    List<SkilledRoleGroup> getSkilledRoleGroups(String name, Integer index, Integer maxResults) throws DomainException;

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
