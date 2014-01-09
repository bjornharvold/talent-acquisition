package com.tps.tpi.service.impl;

import com.tps.tpi.cache.CacheManager;
import com.tps.tpi.cache.impl.OSCacheManager;
import com.tps.tpi.exception.CacheException;
import com.tps.tpi.service.RemotingProfileService;
import com.tps.tpi.service.ProfileService;
import com.tps.tpi.model.objects.dto.*;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.exception.DomainException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dozer.Mapper;

/**
 * User: Bjorn Harvold
 * Date: Oct 27, 2009
 * Time: 4:34:51 PM
 * Responsibility:
 */
@Service("remotingProfileService")
@RemotingDestination(channels = {"my-amf", "my-secure-amf"})
public class RemotingProfileServiceImpl implements RemotingProfileService {
    private static final Logger log = LoggerFactory.getLogger(RemotingProfileServiceImpl.class);
    private final Mapper mapper;
    private final ProfileService profileService;
    private final CacheManager cacheManager;
    private static final String BIOGRAPHY_DTO_KEY = "biography_";
    private static final String PERSON_DTO_KEY = "person_";
    private static final String HISTORY_DTO_KEY = "history_";
    private static final String EDUCATION_DTO_KEY = "education_";

    @Autowired
    public RemotingProfileServiceImpl(Mapper mapper, ProfileService profileService,
                                      OSCacheManager cacheManager) {
        this.mapper = mapper;
        this.profileService = profileService;
        this.cacheManager = cacheManager;
    }

    @Override
    @RemotingInclude
    public PersonDto getPersonDto(String personId) throws DomainException {
        PersonDto result = null;

        if (StringUtils.isBlank(personId)) {
            throw new DomainException("error.missing.data", "personId");
        }

        try {
            String cacheKey = PERSON_DTO_KEY + personId;

            if (log.isDebugEnabled()) {
                log.debug("Checking if PersonDto with id: " + cacheKey + " is in the cache");
            }
            result = (PersonDto) cacheManager.getFromCache(cacheKey, PersonDto.class);

            if (result == null) {
                result = cacheManager.mapEntityToDtoAndCache(PERSON_DTO_KEY, profileService.getPerson(personId), PersonDto.class);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Found PersonDto in cache. ID: " + personId);
                }
                if (log.isTraceEnabled()) {
                    log.trace("Cached PersonDto: " + result);
                }
            }
        } catch (CacheException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    @RemotingInclude
    public PersonDto getPersonDtoByUsername(String username) throws DomainException {
        PersonDto result = null;

        if (StringUtils.isBlank(username)) {
            throw new DomainException("error.missing.data", "username");
        }

        try {
            Person p = profileService.getPersonByUsername(username);

            if (p != null) {
                String cacheKey = PERSON_DTO_KEY + p.getId();

                if (log.isDebugEnabled()) {
                    log.debug("Checking if PersonDto with id: " + cacheKey + " is in the cache");
                }
                result = cacheManager.getFromCache(cacheKey, PersonDto.class);

                if (result == null) {
                    result = cacheManager.mapEntityToDtoAndCache(PERSON_DTO_KEY, p, PersonDto.class);
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Found PersonDto in cache. ID: " + p.getId());
                    }
                    if (log.isTraceEnabled()) {
                        log.trace("Cached PersonDto: " + result);
                    }
                }
            }
        } catch (CacheException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    @RemotingInclude
    public BiographyDto getBiographyDto(String personId) throws DomainException {
        BiographyDto result = null;

        if (StringUtils.isBlank(personId)) {
            throw new DomainException("error.missing.data", "personId");
        }

        try {
            String cacheKey = BIOGRAPHY_DTO_KEY + personId;
            if (log.isDebugEnabled()) {
                log.debug("Checking if BiographyDto with id: " + cacheKey + " is in the cache");
            }
            result = cacheManager.getFromCache(cacheKey, BiographyDto.class);

            if (result == null) {
                result = cacheManager.mapEntityToDtoAndCache(BIOGRAPHY_DTO_KEY, profileService.getBiography(personId), BiographyDto.class);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Found BiographyDto in cache. ID: " + cacheKey);
                }
                if (log.isTraceEnabled()) {
                    log.trace("Cached BiographyDto: " + result);
                }
            }
        } catch (CacheException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    @RemotingInclude
    public HistoryDto getHistoryDto(String personId) throws DomainException {
        HistoryDto result = null;

        if (StringUtils.isBlank(personId)) {
            throw new DomainException("error.missing.data", "personId");
        }

        try {
            String cacheKey = HISTORY_DTO_KEY + personId;
            if (log.isDebugEnabled()) {
                log.debug("Checking if HistoryDto with id: " + cacheKey + " is in the cache");
            }
            result = cacheManager.getFromCache(cacheKey, HistoryDto.class);

            if (result == null) {
                result = cacheManager.mapEntityToDtoAndCache(cacheKey, profileService.getHistory(personId), HistoryDto.class);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Found HistoryDto in cache. ID: " + cacheKey);
                }
                if (log.isTraceEnabled()) {
                    log.trace("Cached HistoryDto: " + result);
                }
            }
        } catch (CacheException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    @RemotingInclude
    public EducationDto getEducationDto(String personId) throws DomainException {
        EducationDto result = null;

        if (StringUtils.isBlank(personId)) {
            throw new DomainException("error.missing.data", "personId");
        }

        try {
            String cacheKey = EDUCATION_DTO_KEY + personId;
            if (log.isDebugEnabled()) {
                log.debug("Checking if EducationDto with id: " + cacheKey + " is in the cache");
            }
            result = cacheManager.getFromCache(cacheKey, EducationDto.class);

            if (result == null) {
                result = cacheManager.mapEntityToDtoAndCache(cacheKey, profileService.getEducation(personId), EducationDto.class);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Found EducationDto in cache. ID: " + cacheKey);
                }
                if (log.isTraceEnabled()) {
                    log.trace("Cached EducationDto: " + result);
                }
            }
        } catch (CacheException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    @RemotingInclude
    public PersonDto savePersonDto(PersonDto p) throws DomainException {
        return mapper.map(profileService.savePerson(mapper.map(p, Person.class)), PersonDto.class);
    }

    @Override
    @RemotingInclude
    public PersonDto updatePersonDto(PersonDto p) throws DomainException {
        return mapper.map(profileService.updatePerson(mapper.map(p, Person.class)), PersonDto.class);
    }

    @Override
    @RemotingInclude
    public BiographyDto saveBiographyDto(BiographyDto bio) throws DomainException {
        return mapper.map(profileService.saveBiography(mapper.map(bio, Biography.class)), BiographyDto.class);
    }

    @Override
    @RemotingInclude
    public BiographyDto updateBiographyDto(BiographyDto bio) throws DomainException {
        return mapper.map(profileService.updateBiography(mapper.map(bio, Biography.class)), BiographyDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteBiography(String id) throws DomainException {
        profileService.deleteBiography(id);
    }

    @Override
    @RemotingInclude
    public HistoryDto saveHistoryDto(HistoryDto history) throws DomainException {
        return mapper.map(profileService.saveHistory(mapper.map(history, History.class)), HistoryDto.class);
    }

    @Override
    @RemotingInclude
    public HistoryDto updateHistoryDto(HistoryDto historyDto) throws DomainException {
        return mapper.map(profileService.updateHistory(mapper.map(historyDto, History.class)), HistoryDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteHistory(String id) throws DomainException {
        profileService.deleteHistory(id);
    }

    @Override
    @RemotingInclude
    public EducationDto saveEducationDto(EducationDto education) throws DomainException {
        return mapper.map(profileService.saveEducation(mapper.map(education, Education.class)), EducationDto.class);
    }

    @Override
    @RemotingInclude
    public EducationDto updateEducationDto(EducationDto education) throws DomainException {
        return mapper.map(profileService.updateEducation(mapper.map(education, Education.class)), EducationDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteEducation(String id) throws DomainException {
        profileService.deleteEducation(id);
    }

    @Override
    @RemotingInclude
    public AwardDto saveAwardDto(AwardDto award) throws DomainException {
        return mapper.map(profileService.saveAward(mapper.map(award, Award.class)), AwardDto.class);
    }

    @Override
    @RemotingInclude
    public AwardDto updateAwardDto(AwardDto award) throws DomainException {
        return mapper.map(profileService.updateAward(mapper.map(award, Award.class)), AwardDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteAward(String id) throws DomainException {
        profileService.deleteAward(id);
    }

    @Override
    @RemotingInclude
    public PatentDto savePatentDto(PatentDto patent) throws DomainException {
        return mapper.map(profileService.savePatent(mapper.map(patent, Patent.class)), PatentDto.class);
    }

    @Override
    @RemotingInclude
    public PatentDto updatePatentDto(PatentDto patent) throws DomainException {
        return mapper.map(profileService.updatePatent(mapper.map(patent, Patent.class)), PatentDto.class);
    }

    @Override
    @RemotingInclude
    public void deletePatent(String id) throws DomainException {
        profileService.deletePatent(id);
    }

    @Override
    @RemotingInclude
    public PublicationDto savePublicationDto(PublicationDto publication) throws DomainException {
        return mapper.map(profileService.savePublication(mapper.map(publication, Publication.class)), PublicationDto.class);
    }

    @Override
    @RemotingInclude
    public PublicationDto updatePublicationDto(PublicationDto publication) throws DomainException {
        return mapper.map(profileService.updatePublication(mapper.map(publication, Publication.class)), PublicationDto.class);
    }

    @Override
    @RemotingInclude
    public void deletePublication(String id) throws DomainException {
        profileService.deletePublication(id);
    }

    @Override
    @RemotingInclude
    public BiographySkillDto saveBiographySkillDto(BiographySkillDto biographySkill) throws DomainException {
        return mapper.map(profileService.saveBiographySkill(mapper.map(biographySkill, BiographySkill.class)), BiographySkillDto.class);
    }

    @Override
    @RemotingInclude
    public BiographySkillDto updateBiographySkillDto(BiographySkillDto biographySkill) throws DomainException {
        return mapper.map(profileService.updateBiographySkill(mapper.map(biographySkill, BiographySkill.class)), BiographySkillDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteBiographySkill(String id) throws DomainException {
        profileService.deleteBiographySkill(id);
    }

    @Override
    @RemotingInclude
    public CoverageDto saveCoverageDto(CoverageDto coverage) throws DomainException {
        return mapper.map(profileService.saveCoverage(mapper.map(coverage, Coverage.class)), CoverageDto.class);
    }

    @Override
    @RemotingInclude
    public CoverageDto updateCoverageDto(CoverageDto coverage) throws DomainException {
        return mapper.map(profileService.updateCoverage(mapper.map(coverage, Coverage.class)), CoverageDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteCoverage(String id) throws DomainException {
        profileService.deleteCoverage(id);
    }

    @Override
    @RemotingInclude
    public PositionDto savePositionDto(PositionDto position) throws DomainException {
        return mapper.map(profileService.savePosition(mapper.map(position, Position.class)), PositionDto.class);
    }

    @Override
    @RemotingInclude
    public PositionDto updatePositionDto(PositionDto position) throws DomainException {
        return mapper.map(profileService.updatePosition(mapper.map(position, Position.class)), PositionDto.class);
    }

    @Override
    @RemotingInclude
    public void deletePosition(String id) throws DomainException {
        profileService.deletePosition(id);
    }

    @Override
    @RemotingInclude
    public ProjectDto saveProjectDto(ProjectDto project) throws DomainException {
        return mapper.map(profileService.saveProject(mapper.map(project, Project.class)), ProjectDto.class);
    }

    @Override
    @RemotingInclude
    public ProjectDto updateProjectDto(ProjectDto project) throws DomainException {
        return mapper.map(profileService.updateProject(mapper.map(project, Project.class)), ProjectDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteProject(String id) throws DomainException {
        profileService.deleteProject(id);
    }

    @Override
    @RemotingInclude
    public EducationCertificationDto saveEducationCertificationDto(EducationCertificationDto certification) throws DomainException {
        return mapper.map(profileService.saveEducationCertification(mapper.map(certification, EducationCertification.class)), EducationCertificationDto.class);
    }

    @Override
    @RemotingInclude
    public EducationCertificationDto updateEducationCertificationDto(EducationCertificationDto certification) throws DomainException {
        return mapper.map(profileService.updateEducationCertification(mapper.map(certification, EducationCertification.class)), EducationCertificationDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteEducationCertification(String id) throws DomainException {
        profileService.deleteEducationCertification(id);
    }

    @Override
    @RemotingInclude
    public DegreeDto saveDegreeDto(DegreeDto degree) throws DomainException {
        return mapper.map(profileService.saveDegree(mapper.map(degree, Degree.class)), DegreeDto.class);
    }

    @Override
    @RemotingInclude
    public DegreeDto updateDegreeDto(DegreeDto degree) throws DomainException {
        return mapper.map(profileService.updateDegree(mapper.map(degree, Degree.class)), DegreeDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteDegree(String id) throws DomainException {
        profileService.deleteDegree(id);
    }

    @Override
    @RemotingInclude
    public LanguageDto saveLanguageDto(LanguageDto language) throws DomainException {
        return mapper.map(profileService.saveLanguage(mapper.map(language, Language.class)), LanguageDto.class);
    }

    @Override
    @RemotingInclude
    public LanguageDto updateLanguageDto(LanguageDto language) throws DomainException {
        return mapper.map(profileService.updateLanguage(mapper.map(language, Language.class)), LanguageDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteLanguage(String id) throws DomainException {
        profileService.deleteLanguage(id);
    }

    @Override
    @RemotingInclude
    public PersonAddressDto savePersonAddressDto(PersonAddressDto p) throws DomainException {
        return mapper.map(profileService.savePersonAddress(mapper.map(p, PersonAddress.class)), PersonAddressDto.class);
    }

    @Override
    @RemotingInclude
    public PersonAddressDto updatePersonAddressDto(PersonAddressDto p) throws DomainException {
        return mapper.map(profileService.updatePersonAddress(mapper.map(p, PersonAddress.class)), PersonAddressDto.class);
    }

    @Override
    @RemotingInclude
    public void deletePersonAddress(String id) throws DomainException {
        profileService.deletePerson(id);
    }

    @Override
    @RemotingInclude
    public BiographyCityDto saveBiographyCityDto(BiographyCityDto biographyCity) throws DomainException {
        return mapper.map(profileService.saveBiographyCity(mapper.map(biographyCity, BiographyCity.class)), BiographyCityDto.class);
    }

    @Override
    @RemotingInclude
    public BiographyCityDto updateBiographyCityDto(BiographyCityDto biographyCity) throws DomainException {
        return mapper.map(profileService.updateBiographyCity(mapper.map(biographyCity, BiographyCity.class)), BiographyCityDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteBiographyCity(String id) throws DomainException {
        profileService.deleteBiographyCity(id);
    }

    @Override
    @RemotingInclude
    public BiographySkilledRoleDto saveBiographySkilledRoleDto(BiographySkilledRoleDto bsr) throws DomainException {
        return mapper.map(profileService.saveBiographySkilledRole(mapper.map(bsr, BiographySkilledRole.class)), BiographySkilledRoleDto.class);
    }

    @Override
    @RemotingInclude
    public BiographySkilledRoleDto updateBiographySkilledRoleDto(BiographySkilledRoleDto bsr) throws DomainException {
        return mapper.map(profileService.updateBiographySkilledRole(mapper.map(bsr, BiographySkilledRole.class)), BiographySkilledRoleDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteBiographySkilledRole(String id) throws DomainException {
        profileService.deleteBiographySkilledRole(id);
    }

    @Override
    @RemotingInclude
    public BiographyDepartmentDto saveBiographyDepartmentDto(BiographyDepartmentDto biographyDepartment) throws DomainException {
        return mapper.map(profileService.saveBiographyDepartment(mapper.map(biographyDepartment, BiographyDepartment.class)), BiographyDepartmentDto.class);
    }

    @Override
    @RemotingInclude
    public BiographyDepartmentDto updateBiographyDepartmentDto(BiographyDepartmentDto biographyDepartment) throws DomainException {
        return mapper.map(profileService.updateBiographyDepartment(mapper.map(biographyDepartment, BiographyDepartment.class)), BiographyDepartmentDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteBiographyDepartment(String id) throws DomainException {
        profileService.deleteBiographyDepartment(id);
    }

    @Override
    @RemotingInclude
    public InterestDto saveInterestDto(InterestDto interest) throws DomainException {
        return mapper.map(profileService.saveInterest(mapper.map(interest, Interest.class)), InterestDto.class);
    }

    @Override
    @RemotingInclude
    public InterestDto updateInterestDto(InterestDto interest) throws DomainException {
        return mapper.map(profileService.updateInterest(mapper.map(interest, Interest.class)), InterestDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteInterest(String id) throws DomainException {
        profileService.deleteInterest(id);
    }

    @Override
    @RemotingInclude
    public AffiliationDto saveAffiliationDto(AffiliationDto affiliation) throws DomainException {
        return mapper.map(profileService.saveAffiliation(mapper.map(affiliation, Affiliation.class)), AffiliationDto.class);
    }

    @Override
    @RemotingInclude
    public AffiliationDto updateAffiliationDto(AffiliationDto affiliation) throws DomainException {
        return mapper.map(profileService.updateAffiliation(mapper.map(affiliation, Affiliation.class)), AffiliationDto.class);
    }

    @Override
    @RemotingInclude
    public void deleteAffiliation(String id) throws DomainException {
        profileService.deleteAffiliation(id);
    }

    @Override
    @RemotingInclude
    public CoverageEndorsementDto endorseCoverageDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        return mapper.map(profileService.endorseCoverage(endorsedEntityId, endorserPersonId, stars), CoverageEndorsementDto.class);
    }

    @Override
    @RemotingInclude
    public LanguageEndorsementDto endorseLanguageDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        return mapper.map(profileService.endorseLanguage(endorsedEntityId, endorserPersonId, stars), LanguageEndorsementDto.class);
    }

    @Override
    @RemotingInclude
    public PositionEndorsementDto endorsePositionDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        return mapper.map(profileService.endorsePosition(endorsedEntityId, endorserPersonId, stars), PositionEndorsementDto.class);
    }

    @Override
    @RemotingInclude
    public ProjectEndorsementDto endorseProjectDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        return mapper.map(profileService.endorseProject(endorsedEntityId, endorserPersonId, stars), ProjectEndorsementDto.class);
    }

    @Override
    @RemotingInclude
    public BiographySkillEndorsementDto endorseBiographySkillDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        return mapper.map(profileService.endorseBiographySkill(endorsedEntityId, endorserPersonId, stars), BiographySkillEndorsementDto.class);
    }

}
