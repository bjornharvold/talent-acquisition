package com.tps.tpi.service;

import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import com.tps.tpi.model.objects.dto.*;
import com.tps.tpi.exception.DomainException;

/**
 * User: Bjorn Harvold
 * Date: Oct 27, 2009
 * Time: 4:25:50 PM
 * Responsibility: Light-weight service for web services and remoting. Talks to profileService to get the job done.
 */
public interface RemotingProfileService {
    @Transactional
    @Secured("ROLE_USER")
    PersonDto getPersonDto(String userId) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PersonDto getPersonDtoByUsername(String username) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyDto getBiographyDto(String personId) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    HistoryDto getHistoryDto(String personId) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    EducationDto getEducationDto(String personId) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PersonDto savePersonDto(PersonDto p) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PersonDto updatePersonDto(PersonDto p) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyDto saveBiographyDto(BiographyDto bio) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyDto updateBiographyDto(BiographyDto bio) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiography(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    HistoryDto saveHistoryDto(HistoryDto bio) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    HistoryDto updateHistoryDto(HistoryDto bio) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteHistory(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    EducationDto saveEducationDto(EducationDto education) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    EducationDto updateEducationDto(EducationDto education) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteEducation(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    AwardDto saveAwardDto(AwardDto award) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    AwardDto updateAwardDto(AwardDto award) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteAward(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PatentDto savePatentDto(PatentDto patent) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PatentDto updatePatentDto(PatentDto patent) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deletePatent(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PublicationDto savePublicationDto(PublicationDto publication) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PublicationDto updatePublicationDto(PublicationDto publication) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deletePublication(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkillDto saveBiographySkillDto(BiographySkillDto biographySkill) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkillDto updateBiographySkillDto(BiographySkillDto biographySkill) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiographySkill(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    CoverageDto saveCoverageDto(CoverageDto coverage) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    CoverageDto updateCoverageDto(CoverageDto coverage) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteCoverage(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PositionDto savePositionDto(PositionDto position) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PositionDto updatePositionDto(PositionDto position) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deletePosition(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    ProjectDto saveProjectDto(ProjectDto project) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    ProjectDto updateProjectDto(ProjectDto project) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteProject(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    EducationCertificationDto saveEducationCertificationDto(EducationCertificationDto certification) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    EducationCertificationDto updateEducationCertificationDto(EducationCertificationDto certification) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteEducationCertification(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    DegreeDto saveDegreeDto(DegreeDto degree) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    DegreeDto updateDegreeDto(DegreeDto degree) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteDegree(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    LanguageDto saveLanguageDto(LanguageDto language) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    LanguageDto updateLanguageDto(LanguageDto language) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteLanguage(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PersonAddressDto savePersonAddressDto(PersonAddressDto p) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PersonAddressDto updatePersonAddressDto(PersonAddressDto p) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deletePersonAddress(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyCityDto saveBiographyCityDto(BiographyCityDto biographyCity) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyCityDto updateBiographyCityDto(BiographyCityDto biographyCity) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiographyCity(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkilledRoleDto saveBiographySkilledRoleDto(BiographySkilledRoleDto bsr) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkilledRoleDto updateBiographySkilledRoleDto(BiographySkilledRoleDto bsr) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiographySkilledRole(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyDepartmentDto saveBiographyDepartmentDto(BiographyDepartmentDto biographyDepartment) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyDepartmentDto updateBiographyDepartmentDto(BiographyDepartmentDto biographyDepartment) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiographyDepartment(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    InterestDto saveInterestDto(InterestDto interest) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    InterestDto updateInterestDto(InterestDto interest) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteInterest(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    AffiliationDto saveAffiliationDto(AffiliationDto affiliation) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    AffiliationDto updateAffiliationDto(AffiliationDto affiliation) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteAffiliation(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    CoverageEndorsementDto endorseCoverageDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    LanguageEndorsementDto endorseLanguageDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PositionEndorsementDto endorsePositionDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    ProjectEndorsementDto endorseProjectDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkillEndorsementDto endorseBiographySkillDto(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;
}
