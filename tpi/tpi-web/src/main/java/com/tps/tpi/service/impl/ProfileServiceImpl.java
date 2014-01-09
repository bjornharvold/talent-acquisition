package com.tps.tpi.service.impl;

import com.tps.tpi.dao.*;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.enums.RecordCreatorTypeCd;
import com.tps.tpi.service.ProfileService;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 9:31:07 AM
 * Responsibility: Handles everything regarding a person's profile
 */
@Service("profileService")
public class ProfileServiceImpl implements ProfileService {
    private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);
    private final PersonDao personDao;
    private final BiographyDao biographyDao;
    private final HistoryDao historyDao;
    private final EducationDao educationDao;
    private final UserDao userDao;
    private final SearchDao searchDao;
    private final PersonAddressDao personAddressDao;
    private final AwardDao awardDao;
    private final PatentDao patentDao;
    private final PublicationDao publicationDao;
    private final BiographySkillDao biographySkillDao;
    private final BiographyCityDao biographyCityDao;
    private final BiographySkilledRoleDao biographySkilledRoleDao;
    private final BiographyDepartmentDao biographyDepartmentDao;
    private final CoverageDao coverageDao;
    private final PositionDao positionDao;
    private final ProjectDao projectDao;
    private final EducationCertificationDao educationCertificationDao;
    private final DegreeDao degreeDao;
    private final LanguageDao languageDao;
    private final InterestDao interestDao;
    private final AffiliationDao affiliationDao;
    private final CoverageEndorsementDao coverageEndorsementDao;
    private final PositionEndorsementDao positionEndorsementDao;
    private final ProjectEndorsementDao projectEndorsementDao;
    private final LanguageEndorsementDao languageEndorsementDao;
    private final BiographySkillEndorsementDao biographySkillEndorsementDao;
    private final Mapper mapper;
    private final BiographyCompanyTitleDao biographyCompanyTitleDao;

    @Autowired
    public ProfileServiceImpl(PersonDao personDao, BiographyDao biographyDao,
                              HistoryDao historyDao, EducationDao educationDao,
                              UserDao userDao, SearchDao searchDao,
                              PersonAddressDao personAddressDao, AwardDao awardDao,
                              PatentDao patentDao, PublicationDao publicationDao,
                              BiographySkillDao biographySkillDao, CoverageDao coverageDao,
                              ProjectDao projectDao, EducationCertificationDao educationCertificationDao,
                              DegreeDao degreeDao, LanguageDao languageDao,
                              PositionDao positionDao, InterestDao interestDao,
                              AffiliationDao affiliationDao, BiographyCityDao biographyCityDao,
                              BiographyDepartmentDao biographyDepartmentDao, BiographySkilledRoleDao biographySkilledRoleDao,
                              Mapper mapper, CoverageEndorsementDao coverageEndorsementDao,
                              LanguageEndorsementDao languageEndorsementDao, PositionEndorsementDao positionEndorsementDao,
                              ProjectEndorsementDao projectEndorsementDao, BiographySkillEndorsementDao biographySkillEndorsementDao,
                              BiographyCompanyTitleDao biographyCompanyTitleDao) {
        this.personDao = personDao;
        this.biographyDao = biographyDao;
        this.historyDao = historyDao;
        this.educationDao = educationDao;
        this.userDao = userDao;
        this.searchDao = searchDao;
        this.personAddressDao = personAddressDao;
        this.awardDao = awardDao;
        this.patentDao = patentDao;
        this.publicationDao = publicationDao;
        this.biographySkillDao = biographySkillDao;
        this.coverageDao = coverageDao;
        this.projectDao = projectDao;
        this.educationCertificationDao = educationCertificationDao;
        this.degreeDao = degreeDao;
        this.languageDao = languageDao;
        this.positionDao = positionDao;
        this.interestDao = interestDao;
        this.affiliationDao = affiliationDao;
        this.biographyCityDao = biographyCityDao;
        this.biographyDepartmentDao = biographyDepartmentDao;
        this.biographySkilledRoleDao = biographySkilledRoleDao;
        this.mapper = mapper;
        this.coverageEndorsementDao = coverageEndorsementDao;
        this.languageEndorsementDao = languageEndorsementDao;
        this.positionEndorsementDao = positionEndorsementDao;
        this.projectEndorsementDao = projectEndorsementDao;
        this.biographySkillEndorsementDao = biographySkillEndorsementDao;
        this.biographyCompanyTitleDao = biographyCompanyTitleDao;
    }

    @Override
    public Person getPersonByUsername(String username) throws DomainException {
        Person result = null;

        if (StringUtils.isBlank(username)) {
            throw new DomainException("error.missing.data", "username");
        }

        try {
            result = personDao.getPersonByUsername(username);

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Person getPerson(String personId) throws DomainException {
        Person result = null;

        if (StringUtils.isBlank(personId)) {
            throw new DomainException("error.missing.data", "personId");
        }

        try {
            result = personDao.get(Person.class, personId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Person getPersonByUserId(String userId) throws DomainException {
        Person result = null;

        if (StringUtils.isBlank(userId)) {
            throw new DomainException("error.missing.data", "userId");
        }

        try {
            result = personDao.getPersonByUserId(userId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Biography getBiography(String personId) throws DomainException {
        Biography result = null;

        if (StringUtils.isBlank(personId)) {
            throw new DomainException("error.missing.data", "personId");
        }

        try {
            result = biographyDao.getBiography(personId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public History getHistory(String personId) throws DomainException {
        History result = null;

        if (StringUtils.isBlank(personId)) {
            throw new DomainException("error.missing.data", "personId");
        }

        try {
            result = historyDao.getHistory(personId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Education getEducation(String personId) throws DomainException {
        Education result = null;

        if (StringUtils.isBlank(personId)) {
            throw new DomainException("error.missing.data", "personId");
        }

        try {
            result = educationDao.getEducation(personId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Person savePerson(Person p) throws DomainException {

        if (p == null) {
            throw new DomainException("error.missing.data", "person");
        }

        try {
            p = personDao.save(p);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return p;
    }

    @Override
    public Person updatePerson(Person p) throws DomainException {
        if (p == null) {
            throw new DomainException("error.missing.data", "person");
        }

        try {
            p = personDao.update(p);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return p;
    }

    @Override
    public void updateLastLogin(String username) throws DomainException {
        if (StringUtils.isBlank(username)) {
            throw new DomainException("error.missing.data", "username");
        }

        try {
            personDao.updateLastLogin(username);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public BiographyCompanyTitle saveBiographyCompanyTitle(BiographyCompanyTitle bsr) throws DomainException {
        if (bsr == null) {
            throw new DomainException("error.missing.data", "BiographyCompanyTitle");
        }

        try {
            bsr = biographyCompanyTitleDao.save(bsr);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return bsr;
    }

    @Override
    public List<Person> getPersons(List<Object[]> personIdsWithRelevancy) throws DomainException {
        List<Person> result = null;

        if (personIdsWithRelevancy == null) {
            throw new DomainException("error.missing.data", "personIdsWithRelevancy");
        }

        if (log.isTraceEnabled()) {
            for (Object[] person : personIdsWithRelevancy) {
                log.trace("Retrieving Person with id: " + person[0]);
            }

        }

        try {
            result = personDao.getPersons(personIdsWithRelevancy);

            // loop through and set relevancy
            // we are assuming that the result list keeps the same order as the passed Object[]
            // if not we have to do a loop within a loop and match on id
            if (result != null) {
                for (int i = 0; i < result.size(); i++) {
                    Person p = result.get(i);
                    p.setRelevancy((Float) personIdsWithRelevancy.get(i)[1]);
                }
            }


        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public void deletePerson(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            personDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Biography saveBiography(Biography bio) throws DomainException {
        if (bio == null) {
            throw new DomainException("error.missing.data", "biography");
        }

        try {
            bio = biographyDao.save(bio);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return bio;
    }

    @Override
    public Biography updateBiography(Biography bio) throws DomainException {
        if (bio == null) {
            throw new DomainException("error.missing.data", "biography");
        }

        try {
            bio = biographyDao.update(bio);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return bio;
    }

    @Override
    public void deleteBiography(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            biographyDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public History saveHistory(History history) throws DomainException {
        if (history == null) {
            throw new DomainException("error.missing.data", "history");
        }

        try {
            history = historyDao.save(history);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return history;
    }

    @Override
    public History updateHistory(History history) throws DomainException {
        if (history == null) {
            throw new DomainException("error.missing.data", "history");
        }

        try {
            history = historyDao.update(history);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return history;
    }

    @Override
    public void deleteHistory(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            historyDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Education saveEducation(Education education) throws DomainException {
        if (education == null) {
            throw new DomainException("error.missing.data", "education");
        }

        try {
            education = educationDao.save(education);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return education;
    }

    @Override
    public Education updateEducation(Education education) throws DomainException {
        if (education == null) {
            throw new DomainException("error.missing.data", "education");
        }

        try {
            education = educationDao.update(education);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return education;
    }

    @Override
    public void deleteEducation(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            educationDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Award saveAward(Award award) throws DomainException {
        if (award == null) {
            throw new DomainException("error.missing.data", "award");
        }

        try {
            award = awardDao.save(award);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return award;
    }

    @Override
    public Award updateAward(Award award) throws DomainException {
        if (award == null) {
            throw new DomainException("error.missing.data", "award");
        }

        try {
            award = awardDao.update(award);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return award;
    }

    @Override
    public void deleteAward(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            awardDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Patent savePatent(Patent patent) throws DomainException {
        if (patent == null) {
            throw new DomainException("error.missing.data", "patent");
        }

        try {
            patent = patentDao.save(patent);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return patent;
    }

    @Override
    public Patent updatePatent(Patent patent) throws DomainException {
        if (patent == null) {
            throw new DomainException("error.missing.data", "patent");
        }

        try {
            patent = patentDao.update(patent);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return patent;
    }

    @Override
    public void deletePatent(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            patentDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Publication savePublication(Publication publication) throws DomainException {
        if (publication == null) {
            throw new DomainException("error.missing.data", "publication");
        }

        try {
            publication = publicationDao.save(publication);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return publication;
    }

    @Override
    public Publication updatePublication(Publication publication) throws DomainException {
        if (publication == null) {
            throw new DomainException("error.missing.data", "publication");
        }

        try {
            publication = publicationDao.update(publication);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return publication;
    }

    @Override
    public void deletePublication(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            publicationDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public BiographySkill saveBiographySkill(BiographySkill biographySkill) throws DomainException {
        if (biographySkill == null) {
            throw new DomainException("error.missing.data", "biographySkill");
        }

        try {
            biographySkill = biographySkillDao.save(biographySkill);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return biographySkill;
    }

    @Override
    public BiographySkill updateBiographySkill(BiographySkill biographySkill) throws DomainException {
        if (biographySkill == null) {
            throw new DomainException("error.missing.data", "biographySkill");
        }

        try {
            biographySkill = biographySkillDao.update(biographySkill);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return biographySkill;
    }

    @Override
    public void deleteBiographySkill(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            biographySkillDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Coverage saveCoverage(Coverage coverage) throws DomainException {
        if (coverage == null) {
            throw new DomainException("error.missing.data", "coverage");
        }

        try {
            coverage = coverageDao.save(coverage);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return coverage;
    }

    @Override
    public Coverage updateCoverage(Coverage coverage) throws DomainException {
        if (coverage == null) {
            throw new DomainException("error.missing.data", "coverage");
        }

        try {
            coverage = coverageDao.update(coverage);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return coverage;
    }

    @Override
    public void deleteCoverage(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            coverageDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Coverage> getCoverages(String historyId, Integer index, Integer maxResults) throws DomainException {
        List<Coverage> result = null;

        if (StringUtils.isBlank(historyId)) {
            throw new DomainException("error.missing.data", "historyId");
        }

        try {
            result = coverageDao.getCoverages(historyId, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Position savePosition(Position position) throws DomainException {
        if (position == null) {
            throw new DomainException("error.missing.data", "position");
        }

        try {
            position = positionDao.save(position);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return position;
    }

    @Override
    public Position updatePosition(Position position) throws DomainException {
        if (position == null) {
            throw new DomainException("error.missing.data", "position");
        }

        try {
            position = positionDao.update(position);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return position;
    }

    @Override
    public void deletePosition(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            positionDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Project saveProject(Project project) throws DomainException {
        if (project == null) {
            throw new DomainException("error.missing.data", "project");
        }

        try {
            project = projectDao.save(project);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return project;
    }

    @Override
    public Project updateProject(Project project) throws DomainException {
        if (project == null) {
            throw new DomainException("error.missing.data", "project");
        }

        try {
            project = projectDao.update(project);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return project;
    }

    @Override
    public void deleteProject(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            projectDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public EducationCertification saveEducationCertification(EducationCertification educationCertification) throws DomainException {
        if (educationCertification == null) {
            throw new DomainException("error.missing.data", "educationCertification");
        }

        try {
            educationCertification = educationCertificationDao.save(educationCertification);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return educationCertification;
    }

    @Override
    public EducationCertification updateEducationCertification(EducationCertification educationCertification) throws DomainException {
        if (educationCertification == null) {
            throw new DomainException("error.missing.data", "educationCertification");
        }

        try {
            educationCertification = educationCertificationDao.update(educationCertification);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return educationCertification;
    }

    @Override
    public void deleteEducationCertification(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            educationCertificationDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Degree saveDegree(Degree degree) throws DomainException {
        if (degree == null) {
            throw new DomainException("error.missing.data", "degree");
        }

        try {
            degree = degreeDao.save(degree);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return degree;
    }

    @Override
    public Degree updateDegree(Degree degree) throws DomainException {
        if (degree == null) {
            throw new DomainException("error.missing.data", "degree");
        }

        try {
            degree = degreeDao.update(degree);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return degree;
    }

    @Override
    public void deleteDegree(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            degreeDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Language saveLanguage(Language language) throws DomainException {
        if (language == null) {
            throw new DomainException("error.missing.data", "language");
        }

        try {
            language = languageDao.save(language);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return language;
    }

    @Override
    public Language updateLanguage(Language language) throws DomainException {
        if (language == null) {
            throw new DomainException("error.missing.data", "language");
        }

        try {
            language = languageDao.update(language);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return language;
    }

    @Override
    public void deleteLanguage(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            languageDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public PersonAddress savePersonAddress(PersonAddress p) throws DomainException {
        if (p == null) {
            throw new DomainException("error.missing.data", "PersonAddress");
        }

        try {
            p = personAddressDao.save(p);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return p;
    }

    @Override
    public PersonAddress updatePersonAddress(PersonAddress p) throws DomainException {
        if (p == null) {
            throw new DomainException("error.missing.data", "PersonAddress");
        }

        try {
            p = personAddressDao.save(p);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return p;
    }

    @Override
    public void deletePersonAddress(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            personAddressDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public BiographyCity saveBiographyCity(BiographyCity biographyCity) throws DomainException {
        if (biographyCity == null) {
            throw new DomainException("error.missing.data", "BiographyCity");
        }

        try {
            biographyCity = biographyCityDao.save(biographyCity);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return biographyCity;
    }

    @Override
    public BiographyCity updateBiographyCity(BiographyCity biographyCity) throws DomainException {
        if (biographyCity == null) {
            throw new DomainException("error.missing.data", "BiographyCity");
        }

        try {
            biographyCity = biographyCityDao.update(biographyCity);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return biographyCity;
    }

    @Override
    public void deleteBiographyCity(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            biographyCityDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public BiographySkilledRole saveBiographySkilledRole(BiographySkilledRole bsr) throws DomainException {
        if (bsr == null) {
            throw new DomainException("error.missing.data", "BiographySkilledRole");
        }

        try {
            bsr = biographySkilledRoleDao.save(bsr);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return bsr;
    }

    @Override
    public BiographySkilledRole updateBiographySkilledRole(BiographySkilledRole bsr) throws DomainException {
        if (bsr == null) {
            throw new DomainException("error.missing.data", "BiographySkilledRole");
        }

        try {
            bsr = biographySkilledRoleDao.update(bsr);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return bsr;
    }

    @Override
    public void deleteBiographySkilledRole(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            biographySkilledRoleDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public BiographyDepartment saveBiographyDepartment(BiographyDepartment bd) throws DomainException {
        if (bd == null) {
            throw new DomainException("error.missing.data", "BiographyDepartment");
        }

        try {
            bd = biographyDepartmentDao.save(bd);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return bd;
    }

    @Override
    public BiographyDepartment updateBiographyDepartment(BiographyDepartment bd) throws DomainException {
        if (bd == null) {
            throw new DomainException("error.missing.data", "BiographyDepartment");
        }

        try {
            bd = biographyDepartmentDao.save(bd);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return bd;
    }

    @Override
    public void deleteBiographyDepartment(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            biographyDepartmentDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Interest saveInterest(Interest interest) throws DomainException {
        if (interest == null) {
            throw new DomainException("error.missing.data", "interest");
        }

        try {
            interest = interestDao.save(interest);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return interest;
    }

    @Override
    public Interest updateInterest(Interest interest) throws DomainException {
        if (interest == null) {
            throw new DomainException("error.missing.data", "interest");
        }

        try {
            interest = interestDao.update(interest);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return interest;
    }

    @Override
    public void deleteInterest(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            interestDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Affiliation saveAffiliation(Affiliation affiliation) throws DomainException {
        if (affiliation == null) {
            throw new DomainException("error.missing.data", "affiliation");
        }

        try {
            affiliation = affiliationDao.save(affiliation);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return affiliation;
    }

    @Override
    public Affiliation updateAffiliation(Affiliation affiliation) throws DomainException {
        if (affiliation == null) {
            throw new DomainException("error.missing.data", "affiliation");
        }

        try {
            affiliation = affiliationDao.update(affiliation);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return affiliation;
    }

    @Override
    public void deleteAffiliation(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            affiliationDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    /**
     * One person endorses another person
     *
     * @param endorsedEntityId
     * @param endorserPersonId
     * @param stars            must match EndorsementLevelCd
     * @throws DomainException
     */
    @Override
    public CoverageEndorsement endorseCoverage(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        CoverageEndorsement result = null;

        if (StringUtils.isBlank(endorsedEntityId)) {
            throw new DomainException("error.missing.data", "endorsedEntityId");
        }
        if (StringUtils.isBlank(endorserPersonId)) {
            throw new DomainException("error.missing.data", "endorserPersonId");
        }
        if (stars == null) {
            throw new DomainException("error.missing.data", "stars");
        }

        try {
            Person endorser = getPerson(endorserPersonId);
            result = coverageEndorsementDao.getCoverageEndorsement(endorsedEntityId, endorserPersonId);

            if (result != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Updating CoverageEndorsement");
                }
                // existing endorsement already exists, update record if level was changed
                if (result.getEndorsement().getStars() != stars) {
                    result.getEndorsement().setStars(stars);

                    result = coverageEndorsementDao.update(result);
                }
            } else if (endorser.getAvailableEndorsements() > 0) {
                if (log.isTraceEnabled()) {
                    log.trace("Inserting CoverageEndorsement");
                }

                Endorsement end = new Endorsement();
                end.setEndorsedOn(new Date());
                end.setEndorser(endorser);
                end.setStars(stars);
                end.setRecordCreatorType(RecordCreatorTypeCd.PERSON);

                Coverage coverage = getCoverageById(endorsedEntityId);

                if (coverage != null) {
                    if (!StringUtils.equals(coverage.getHistory().getPerson().getId(), endorserPersonId)) {
                        result = new CoverageEndorsement();
                        result.setCoverage(coverage);
                        result.setEndorsement(end);
                        result = saveCoverageEndorsement(result);

                        if (StringUtils.isNotBlank(result.getId())) {
                            endorser.setAvailableEndorsements(endorser.getAvailableEndorsements() - 1);
                            updatePerson(endorser);
                        }
                    } else {
                        // person is trying to endorse self
                        log.error("Endorser cannot endorse self");
                        throw new DomainException("error.endorsement.self");
                    }
                } else {
                    // endorsed entity record doesn't exist
                    log.error("Endorsement entity for class Coverage with id: " + endorsedEntityId + " does not exist");
                    throw new DomainException("error.endorsement.entity");
                }


            } else {
                log.error("Not enough endorsement points for Person endorser with id: " + endorser.getId());
                throw new DomainException("error.endorsement.points");
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public LanguageEndorsement endorseLanguage(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        LanguageEndorsement result = null;

        if (StringUtils.isBlank(endorsedEntityId)) {
            throw new DomainException("error.missing.data", "endorsedEntityId");
        }
        if (StringUtils.isBlank(endorserPersonId)) {
            throw new DomainException("error.missing.data", "endorserPersonId");
        }
        if (stars == null) {
            throw new DomainException("error.missing.data", "stars");
        }

        try {
            Person endorser = getPerson(endorserPersonId);
            result = languageEndorsementDao.getLanguageEndorsementDao(endorsedEntityId, endorserPersonId);

            if (result != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Updating LanguageEndorsement");
                }
                // existing endorsement already exists, update record if level was changed
                if (result.getEndorsement().getStars() != stars) {
                    result.getEndorsement().setStars(stars);

                    result = languageEndorsementDao.update(result);
                }
            } else if (endorser.getAvailableEndorsements() > 0) {
                if (log.isTraceEnabled()) {
                    log.trace("Inserting LanguageEndorsement");
                }
                Endorsement end = new Endorsement();
                end.setEndorsedOn(new Date());
                end.setEndorser(endorser);
                end.setStars(stars);
                end.setRecordCreatorType(RecordCreatorTypeCd.PERSON);

                Language language = getLanguageById(endorsedEntityId);

                if (language != null) {
                    if (!StringUtils.equals(language.getEducation().getPerson().getId(), endorserPersonId)) {
                        result = new LanguageEndorsement();
                        result.setLanguage(language);
                        result.setEndorsement(end);
                        result = saveLanguageEndorsement(result);

                        if (StringUtils.isNotBlank(result.getId())) {
                            endorser.setAvailableEndorsements(endorser.getAvailableEndorsements() - 1);
                            updatePerson(endorser);
                        }
                    } else {
                        // person is trying to endorse self
                        log.error("Endorser cannot endorse self");
                        throw new DomainException("error.endorsement.self");
                    }
                } else {
                    // endorsed entity record doesn't exist
                    log.error("Endorsement entity for class Language with id: " + endorsedEntityId + " does not exist");
                    throw new DomainException("error.endorsement.entity");
                }

            } else {
                log.error("Not enough endorsement points for Person endorser with id: " + endorser.getId());
                throw new DomainException("error.endorsement.points");
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public PositionEndorsement endorsePosition(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        PositionEndorsement result = null;

        if (StringUtils.isBlank(endorsedEntityId)) {
            throw new DomainException("error.missing.data", "endorsedEntityId");
        }
        if (StringUtils.isBlank(endorserPersonId)) {
            throw new DomainException("error.missing.data", "endorserPersonId");
        }
        if (stars == null) {
            throw new DomainException("error.missing.data", "stars");
        }

        try {
            Person endorser = getPerson(endorserPersonId);
            result = positionEndorsementDao.getPositionEndorsement(endorsedEntityId, endorserPersonId);

            if (result != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Updating PositionEndorsement");
                }
                // existing endorsement already exists, update record if level was changed
                if (result.getEndorsement().getStars() != stars) {
                    result.getEndorsement().setStars(stars);

                    result = positionEndorsementDao.update(result);
                }
            } else if (endorser.getAvailableEndorsements() > 0) {
                if (log.isTraceEnabled()) {
                    log.trace("Inserting PositionEndorsement");
                }

                Endorsement end = new Endorsement();
                end.setEndorsedOn(new Date());
                end.setEndorser(endorser);
                end.setStars(stars);
                end.setRecordCreatorType(RecordCreatorTypeCd.PERSON);

                Position position = getPositionById(endorsedEntityId);

                if (position != null) {
                    if (!StringUtils.equals(position.getHistory().getPerson().getId(), endorserPersonId)) {
                        result = new PositionEndorsement();
                        result.setPosition(position);
                        result.setEndorsement(end);
                        result = savePositionEndorsement(result);

                        if (StringUtils.isNotBlank(result.getId())) {
                            endorser.setAvailableEndorsements(endorser.getAvailableEndorsements() - 1);
                            updatePerson(endorser);
                        }
                    } else {
                        // person is trying to endorse self
                        log.error("Endorser cannot endorse self");
                        throw new DomainException("error.endorsement.self");
                    }
                } else {
                    // endorsed entity record doesn't exist
                    log.error("Endorsement entity for class Position with id: " + endorsedEntityId + " does not exist");
                    throw new DomainException("error.endorsement.entity");
                }

            } else {
                log.error("Not enough endorsement points for Person endorser with id: " + endorser.getId());
                throw new DomainException("error.endorsement.points");
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public ProjectEndorsement endorseProject(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        ProjectEndorsement result = null;

        if (StringUtils.isBlank(endorsedEntityId)) {
            throw new DomainException("error.missing.data", "endorsedEntityId");
        }
        if (StringUtils.isBlank(endorserPersonId)) {
            throw new DomainException("error.missing.data", "endorserPersonId");
        }
        if (stars == null) {
            throw new DomainException("error.missing.data", "stars");
        }

        try {
            Person endorser = getPerson(endorserPersonId);
            result = projectEndorsementDao.getProjectEndorsement(endorsedEntityId, endorserPersonId);

            if (result != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Updating ProjectEndorsement");
                }
                // existing endorsement already exists, update record if level was changed
                if (result.getEndorsement().getStars() != stars) {
                    result.getEndorsement().setStars(stars);

                    result = projectEndorsementDao.update(result);
                }
            } else if (endorser.getAvailableEndorsements() > 0) {
                if (log.isTraceEnabled()) {
                    log.trace("Inserting ProjectEndorsement");
                }


                Endorsement end = new Endorsement();
                end.setEndorsedOn(new Date());
                end.setEndorser(endorser);
                end.setStars(stars);
                end.setRecordCreatorType(RecordCreatorTypeCd.PERSON);

                Project project = getProjectById(endorsedEntityId);

                if (project != null) {
                    if (!StringUtils.equals(project.getHistory().getPerson().getId(), endorserPersonId)) {
                        result = new ProjectEndorsement();
                        result.setProject(project);
                        result.setEndorsement(end);
                        saveProjectEndorsement(result);

                        if (StringUtils.isNotBlank(result.getId())) {
                            endorser.setAvailableEndorsements(endorser.getAvailableEndorsements() - 1);
                            updatePerson(endorser);
                        }
                    } else {
                        // person is trying to endorse self
                        log.error("Endorser cannot endorse self");
                        throw new DomainException("error.endorsement.self");
                    }
                } else {
                    // endorsed entity record doesn't exist
                    log.error("Endorsement entity for class Project with id: " + endorsedEntityId + " does not exist");
                    throw new DomainException("error.endorsement.entity");
                }

            } else {
                log.error("Not enough endorsement points for Person endorser with id: " + endorser.getId());
                throw new DomainException("error.endorsement.points");
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public BiographySkillEndorsement endorseBiographySkill(String endorsedEntityId, String endorserPersonId, Integer stars) throws DomainException {
        BiographySkillEndorsement result = null;

        if (StringUtils.isBlank(endorsedEntityId)) {
            throw new DomainException("error.missing.data", "endorsedEntityId");
        }
        if (StringUtils.isBlank(endorserPersonId)) {
            throw new DomainException("error.missing.data", "endorserPersonId");
        }
        if (stars == null) {
            throw new DomainException("error.missing.data", "stars");
        }

        try {
            Person endorser = getPerson(endorserPersonId);
            result = biographySkillEndorsementDao.getBiographySkillEndorsement(endorsedEntityId, endorserPersonId);

            if (result != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Updating BiographySkillEndorsement");
                }
                // existing endorsement already exists, update record if level was changed
                if (result.getEndorsement().getStars() != stars) {
                    result.getEndorsement().setStars(stars);

                    result = biographySkillEndorsementDao.update(result);
                }
            } else if (endorser.getAvailableEndorsements() > 0) {
                if (log.isTraceEnabled()) {
                    log.trace("Inserting BiographySkillEndorsement");
                }

                Endorsement end = new Endorsement();
                end.setEndorsedOn(new Date());
                end.setEndorser(endorser);
                end.setStars(stars);
                end.setRecordCreatorType(RecordCreatorTypeCd.PERSON);
                BiographySkill skill = getBiographySkillById(endorsedEntityId);

                if (skill != null) {
                    if (!StringUtils.equals(skill.getBiography().getPerson().getId(), endorserPersonId)) {
                        result = new BiographySkillEndorsement();
                        result.setBiographySkill(skill);
                        result.setEndorsement(end);
                        result = saveBiographySkillEndorsement(result);

                        if (StringUtils.isNotBlank(result.getId())) {
                            endorser.setAvailableEndorsements(endorser.getAvailableEndorsements() - 1);
                            updatePerson(endorser);
                        }
                    } else {
                        // person is trying to endorse self
                        log.error("Endorser cannot endorse self");
                        throw new DomainException("error.endorsement.self");
                    }
                } else {
                    // endorsed entity record doesn't exist
                    log.error("Endorsement entity for class BiographySkill with id: " + endorsedEntityId + " does not exist");
                    throw new DomainException("error.endorsement.entity");
                }

            } else {
                log.error("Not enough endorsement points for Person endorser with id: " + endorser.getId());
                throw new DomainException("error.endorsement.points");
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Coverage getCoverageById(String id) throws DomainException {
        Coverage result = null;

        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            result = coverageDao.get(Coverage.class, id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public CoverageEndorsement saveCoverageEndorsement(CoverageEndorsement ce) throws DomainException {
        if (ce == null) {
            throw new DomainException("error.missing.data", "ce");
        }

        try {
            ce = coverageEndorsementDao.save(ce);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return ce;
    }

    @Override
    public Language getLanguageById(String id) throws DomainException {
        Language result = null;

        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            result = languageDao.get(Language.class, id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public LanguageEndorsement saveLanguageEndorsement(LanguageEndorsement ce) throws DomainException {
        if (ce == null) {
            throw new DomainException("error.missing.data", "ce");
        }

        try {
            ce = languageEndorsementDao.save(ce);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return ce;
    }

    @Override
    public Position getPositionById(String id) throws DomainException {
        Position result = null;

        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            result = positionDao.get(Position.class, id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public PositionEndorsement savePositionEndorsement(PositionEndorsement ce) throws DomainException {
        if (ce == null) {
            throw new DomainException("error.missing.data", "ce");
        }

        try {
            ce = positionEndorsementDao.save(ce);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return ce;
    }

    @Override
    public Project getProjectById(String id) throws DomainException {
        Project result = null;

        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            result = projectDao.get(Project.class, id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public ProjectEndorsement saveProjectEndorsement(ProjectEndorsement ce) throws DomainException {
        if (ce == null) {
            throw new DomainException("error.missing.data", "ce");
        }

        try {
            ce = projectEndorsementDao.save(ce);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return ce;
    }

    @Override
    public BiographySkill getBiographySkillById(String id) throws DomainException {
        BiographySkill result = null;

        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            result = biographySkillDao.get(BiographySkill.class, id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public BiographySkillEndorsement saveBiographySkillEndorsement(BiographySkillEndorsement ce) throws DomainException {
        if (ce == null) {
            throw new DomainException("error.missing.data", "ce");
        }

        try {
            ce = biographySkillEndorsementDao.save(ce);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return ce;
    }

}
