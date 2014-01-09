package com.tps.tpi.service;

import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.exception.DomainException;
import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 9:23:17 AM
 * Responsibility:
 */
public interface ProfileService {

    @Secured("ROLE_USER")
    Person getPersonByUsername(String username) throws DomainException;

    @Secured("ROLE_USER")
    Person getPerson(String personId) throws DomainException;

    @Secured("ROLE_USER")
    Person getPersonByUserId(String userId) throws DomainException;

    @Secured("ROLE_USER")
    Biography getBiography(String personId) throws DomainException;

    @Secured("ROLE_USER")
    History getHistory(String personId) throws DomainException;

    @Secured("ROLE_USER")
    Education getEducation(String personId) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Person savePerson(Person p) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Person updatePerson(Person p) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deletePerson(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Biography saveBiography(Biography bio) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Biography updateBiography(Biography bio) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiography(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    History saveHistory(History bio) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    History updateHistory(History bio) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteHistory(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Education saveEducation(Education education) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Education updateEducation(Education education) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteEducation(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Award saveAward(Award award) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Award updateAward(Award award) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteAward(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Patent savePatent(Patent patent) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Patent updatePatent(Patent patent) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deletePatent(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Publication savePublication(Publication publication) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Publication updatePublication(Publication publication) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deletePublication(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkill saveBiographySkill(BiographySkill biographySkill) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkill updateBiographySkill(BiographySkill biographySkill) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiographySkill(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Coverage saveCoverage(Coverage coverage) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Coverage updateCoverage(Coverage coverage) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteCoverage(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    List<Coverage> getCoverages(String companyId, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Position savePosition(Position position) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Position updatePosition(Position position) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deletePosition(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Project saveProject(Project project) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Project updateProject(Project project) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteProject(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    EducationCertification saveEducationCertification(EducationCertification certification) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    EducationCertification updateEducationCertification(EducationCertification certification) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteEducationCertification(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Degree saveDegree(Degree degree) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Degree updateDegree(Degree degree) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteDegree(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Language saveLanguage(Language language) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Language updateLanguage(Language language) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteLanguage(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PersonAddress savePersonAddress(PersonAddress p) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PersonAddress updatePersonAddress(PersonAddress p) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deletePersonAddress(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyCity saveBiographyCity(BiographyCity biographyCity) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyCity updateBiographyCity(BiographyCity biographyCity) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiographyCity(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkilledRole saveBiographySkilledRole(BiographySkilledRole bsr) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkilledRole updateBiographySkilledRole(BiographySkilledRole bsr) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiographySkilledRole(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyDepartment saveBiographyDepartment(BiographyDepartment biographyDepartment) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyDepartment updateBiographyDepartment(BiographyDepartment biographyDepartment) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteBiographyDepartment(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Interest saveInterest(Interest interest) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Interest updateInterest(Interest interest) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteInterest(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Affiliation saveAffiliation(Affiliation affiliation) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    Affiliation updateAffiliation(Affiliation affiliation) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void deleteAffiliation(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    CoverageEndorsement endorseCoverage(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    LanguageEndorsement endorseLanguage(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PositionEndorsement endorsePosition(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    ProjectEndorsement endorseProject(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkillEndorsement endorseBiographySkill(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException;

    @Secured("ROLE_USER")
    Coverage getCoverageById(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    CoverageEndorsement saveCoverageEndorsement(CoverageEndorsement ce) throws DomainException;

    @Secured("ROLE_USER")
    Language getLanguageById(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    LanguageEndorsement saveLanguageEndorsement(LanguageEndorsement ce) throws DomainException;

    @Secured("ROLE_USER")
    Position getPositionById(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    PositionEndorsement savePositionEndorsement(PositionEndorsement ce) throws DomainException;

    @Secured("ROLE_USER")
    Project getProjectById(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    ProjectEndorsement saveProjectEndorsement(ProjectEndorsement ce) throws DomainException;

    @Secured("ROLE_USER")
    BiographySkill getBiographySkillById(String id) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographySkillEndorsement saveBiographySkillEndorsement(BiographySkillEndorsement ce) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    void updateLastLogin(String username) throws DomainException;

    @Transactional
    @Secured("ROLE_USER")
    BiographyCompanyTitle saveBiographyCompanyTitle(BiographyCompanyTitle bsr) throws DomainException;

    @Secured("ROLE_USER")
    List<Person> getPersons(List<Object[]> notCachedIds) throws DomainException;
}
