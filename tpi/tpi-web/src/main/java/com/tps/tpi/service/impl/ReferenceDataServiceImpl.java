package com.tps.tpi.service.impl;

import com.tps.tpi.dao.*;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.enums.LanguageCd;
import com.tps.tpi.model.objects.enums.LanguageLevelCd;
import com.tps.tpi.model.objects.enums.RaceCd;
import com.tps.tpi.service.ReferenceDataService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * User: Bjorn Harvold
 * Date: Oct 9, 2009
 * Time: 11:40:10 AM
 * Responsibility:
 */
@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {
    private final static Logger log = LoggerFactory.getLogger(ReferenceDataServiceImpl.class);
    private final SkillGroupDao skillGroupDao;
    private final SkillDao skillDao;
    private final SkilledRoleGroupDao skilledRoleGroupDao;
    private final SkilledRoleDao skilledRoleDao;
    private final CountryDao countryDao;
    private final RegionDao regionDao;
    private final CertificationDao certificationDao;
    private final TitleDao titleDao;
    private final CityDao cityDao;
    private final IndustryGroupDao industryGroupDao;
    private final IndustryDao industryDao;
    private final SchoolDao schoolDao;
    private final AddressDao addressDao;

    @Autowired
    public ReferenceDataServiceImpl(SkillGroupDao skillGroupDao, SkillDao skillDao,
                                    SkilledRoleGroupDao skilledRoleGroupDao, SkilledRoleDao skilledRoleDao,
                                    CountryDao countryDao, RegionDao regionDao,
                                    CertificationDao certificationDao, TitleDao titleDao,
                                    CityDao cityDao, IndustryDao industryDao,
                                    IndustryGroupDao industryGroupDao, SchoolDao schoolDao,
                                    AddressDao addressDao) {
        this.skillGroupDao = skillGroupDao;
        this.skillDao = skillDao;
        this.skilledRoleGroupDao = skilledRoleGroupDao;
        this.skilledRoleDao = skilledRoleDao;
        this.countryDao = countryDao;
        this.regionDao = regionDao;
        this.certificationDao = certificationDao;
        this.titleDao = titleDao;
        this.cityDao = cityDao;
        this.industryDao = industryDao;
        this.industryGroupDao = industryGroupDao;
        this.schoolDao = schoolDao;
        this.addressDao = addressDao;
    }

    @Override
    public City saveCity(City city) throws DomainException {
        if (city == null) {
            throw new DomainException("error.missing.data", "City");
        }

        try {
            return cityDao.save(city);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public City updateCity(City city) throws DomainException {
        if (city == null) {
            throw new DomainException("error.missing.data", "City");
        }

        try {
            return cityDao.update(city);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteCity(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            cityDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Address saveAddress(Address address) throws DomainException {
        if (address == null) {
            throw new DomainException("error.missing.data", "address");
        }

        try {
            return addressDao.save(address);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Address updateAddress(Address address) throws DomainException {
        if (address == null) {
            throw new DomainException("error.missing.data", "address");
        }

        try {
            return addressDao.update(address);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteAddress(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            addressDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<City> getCities(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return cityDao.getCities(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public City getCityByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return cityDao.getCityByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Industry saveIndustry(Industry industry) throws DomainException {
        if (industry == null) {
            throw new DomainException("error.missing.data", "Industry");
        }

        try {
            return industryDao.save(industry);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Industry updateIndustry(Industry industry) throws DomainException {
        if (industry == null) {
            throw new DomainException("error.missing.data", "Industry");
        }

        try {
            return industryDao.update(industry);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteIndustry(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            industryDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Industry> getIndustries(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return industryDao.getIndustries(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Industry getIndustryByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return industryDao.getIndustryByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public IndustryGroup saveIndustryGroup(IndustryGroup industryGroup) throws DomainException {
        if (industryGroup == null) {
            throw new DomainException("error.missing.data", "IndustryGroup");
        }

        try {
            return industryGroupDao.save(industryGroup);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public IndustryGroup updateIndustryGroup(IndustryGroup industryGroup) throws DomainException {
        if (industryGroup == null) {
            throw new DomainException("error.missing.data", "IndustryGroup");
        }

        try {
            return industryGroupDao.save(industryGroup);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteIndustryGroup(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            industryGroupDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<IndustryGroup> getIndustryGroups(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return industryGroupDao.getIndustryGroups(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public IndustryGroup getIndustryGroupByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return industryGroupDao.getIndustryByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Title saveTitle(Title title) throws DomainException {
        if (title == null) {
            throw new DomainException("error.missing.data", "Title");
        }

        try {
            return titleDao.save(title);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Title updateTitle(Title title) throws DomainException {
        if (title == null) {
            throw new DomainException("error.missing.data", "Title");
        }

        try {
            return titleDao.update(title);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Title getTitleByShortName(String shortName) throws DomainException {
        if (StringUtils.isBlank(shortName)) {
            throw new DomainException("error.missing.data", "shortName");
        }

        try {
            return titleDao.getTitleByShortName(shortName);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Title getTitleByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return titleDao.getTitleByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Title> getTitles(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return titleDao.getTitles(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteTitle(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            titleDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Skill saveSkill(Skill skill) throws DomainException {
        if (skill == null) {
            throw new DomainException("error.missing.data", "Skill");
        }

        try {
            return skillDao.save(skill);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Skill updateSkill(Skill skill) throws DomainException {
        if (skill == null) {
            throw new DomainException("error.missing.data", "Skill");
        }

        try {
            return skillDao.update(skill);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteSkill(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            skillDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Skill> getSkills(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return skillDao.getSkills(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Certification saveCertification(Certification certification) throws DomainException {
        if (certification == null) {
            throw new DomainException("error.missing.data", "Certification");
        }

        try {
            return certificationDao.save(certification);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Certification updateCertification(Certification certification) throws DomainException {
        if (certification == null) {
            throw new DomainException("error.missing.data", "Certification");
        }

        try {
            return certificationDao.save(certification);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteCertification(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            certificationDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Certification getCertificationByShortName(String shortName) throws DomainException {
        if (StringUtils.isBlank(shortName)) {
            throw new DomainException("error.missing.data", "shortName");
        }

        try {
            return certificationDao.getCertificationByShortName(shortName);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public School saveSchool(School school) throws DomainException {
        if (school == null) {
            throw new DomainException("error.missing.data", "School");
        }

        try {
            return schoolDao.save(school);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public School updateSchool(School school) throws DomainException {
        if (school == null) {
            throw new DomainException("error.missing.data", "School");
        }

        try {
            return schoolDao.update(school);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteSchool(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            schoolDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public School getSchoolByShortName(String shortName) throws DomainException {
        if (StringUtils.isBlank(shortName)) {
            throw new DomainException("error.missing.data", "shortName");
        }

        try {
            return schoolDao.getSchoolByShortName(shortName);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Region> getRegions(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return regionDao.getRegions(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Region> getRegions(List<String> ids) throws DomainException {
        try {
            return regionDao.getRegions(ids);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getRegionIds(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return regionDao.getRegionIds(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Region saveRegion(Region region) throws DomainException {
        if (region == null) {
            throw new DomainException("error.missing.data", "Region");
        }

        try {
            return regionDao.save(region);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Region updateRegion(Region region) throws DomainException {
        if (region == null) {
            throw new DomainException("error.missing.data", "Region");
        }

        try {
            return regionDao.update(region);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteRegion(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }
        
        try {
            regionDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Country saveCountry(Country country) throws DomainException {
        if (country == null) {
            throw new DomainException("error.missing.data", "Country");
        }

        try {
            return countryDao.save(country);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Country updateCountry(Country country) throws DomainException {
        if (country == null) {
            throw new DomainException("error.missing.data", "Country");
        }

        try {
            return countryDao.update(country);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteCountry(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            countryDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Country> getCountries(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return countryDao.getCountries(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public State saveState(State state) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public State updateState(State state) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteState(String id) throws DomainException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SkillGroup getSkillGroupByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return skillGroupDao.getSkillGroupByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<SkillGroup> getSkillGroups(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return skillGroupDao.getSkillGroups(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public SkillGroup saveSkillGroup(SkillGroup sg) throws DomainException {
        if (sg == null) {
            throw new DomainException("error.missing.data", "SkillGroup");
        }

        try {
            return skillGroupDao.save(sg);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Skill getSkillByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return skillDao.getSkillByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public SkilledRoleGroup getSkilledRoleGroupByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return skilledRoleGroupDao.getProfessionalRoleGroupByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public SkilledRoleGroup saveSkilledRoleGroup(SkilledRoleGroup prg) throws DomainException {
        if (prg == null) {
            throw new DomainException("error.missing.data", "ProfessionalRoleGroup");
        }

        try {
            return skilledRoleGroupDao.save(prg);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public SkilledRole getSkilledRoleByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return skilledRoleDao.getProfessionalRoleByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public SkilledRole saveSkilledRole(SkilledRole pr) throws DomainException {
        if (pr == null) {
            throw new DomainException("error.missing.data", "ProfessionalRole");
        }

        try {
            return skilledRoleDao.save(pr);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<SkilledRoleGroup> getSkilledRoleGroups(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return skilledRoleGroupDao.getSkilledRoleGroups(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<SkilledRole> getSkilledRoles(String name, Integer index, Integer maxResults) throws DomainException {
        try {
            return skilledRoleDao.getSkilledRoles(name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getTimeZones() throws DomainException {
        return Arrays.asList(TimeZone.getAvailableIDs());
    }

    @Override
    public List<String> getLanguages() throws DomainException {
        LanguageCd[] list = LanguageCd.values();
        List<String> result = new ArrayList<String>(list.length);

        for (LanguageCd languageCd : list) {
            result.add("language." + languageCd.name());
        }

        return result;
    }

    @Override
    public List<String> getLanguageLevels() throws DomainException {
        LanguageLevelCd[] list = LanguageLevelCd.values();
        List<String> result = new ArrayList<String>(list.length);

        for (LanguageLevelCd LanguageLevelCd : list) {
            result.add("language.proficiency." + LanguageLevelCd.name());
        }

        return result;
    }

    @Override
    public List<String> getRaces() throws DomainException {
        RaceCd[] list = RaceCd.values();
        List<String> result = new ArrayList<String>(list.length);

        for (RaceCd RaceCd : list) {
            result.add("race." + RaceCd.name());
        }

        return result;
    }

    @Override
    public Country getCountryByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return countryDao.getCountryByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Region getRegionByCode(String code) throws DomainException {
        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            return regionDao.getRegionByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }
}
