package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.SearchDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Search;
import com.tps.tpi.model.objects.entity.SearchComponent;
import com.tps.tpi.model.objects.entity.SearchComponentGroup;
import com.tps.tpi.model.objects.entity.Person;
import com.tps.tpi.model.objects.enums.*;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 4:05:58 PM
 * Responsibility: Examples of how to use Hibernate Search
 */
@Repository("searchDao")
public class SearchDaoImpl extends AbstractHibernateDao<Search, String> implements SearchDao {
    private final static Logger log = LoggerFactory.getLogger(SearchDaoImpl.class);

    // leaving * out for now cause we're using it manually to create queries
    private static final String[] SPECIALS =
            new String[]{
                    "+", "-", "&&", "||", "!", "(", ")", "{", "}",
                    "[", "]", "^", "\"", "~", "?", ":", "\\"
            };
    private final static String[] QUICK_SEARCH_INDEXES = {
            "currentLocation.shortName",
            "biographies.biographySkills.skill.shortName",
            "biographies.biographySkilledRoles.skilledRole.shortName",
            "biographies.biographySkilledRoles.skilledRole.skilledRoleGroup.shortName",
            "biographies.biographyCities.city.shortName",
            "biographies.biographyDepartments.department.division.company.shortName",
            "biographies.biographyDepartments.department.division.company.companyNames.shortName"
    };

    // advanced search indexes
    private final static String FIRST_NAME_INDEX = "firstName";
    private final static String LAST_NAME_INDEX = "lastName";
    private final static String MIDDLE_NAME_INDEX = "middleName";
    private final static String ROLE_INDEX = "biographies.biographySkilledRoles.skilledRole.shortName";
    private final static String TITLE_INDEX = "biographies.biographyCompanyTitles.companyTitle.title.shortName";
    private static final String ADDRESS1_INDEX = "personAddresses.address.address1";
    private static final String ADDRESS2_INDEX = "personAddresses.address.address2";
    private static final String CITY_INDEX = "personAddresses.address.city.shortName";
    private static final String CITY2_INDEX = "preferredLocation.shortName";
    private static final String STATE_INDEX = "personAddresses.address.state";
    private static final String COUNTRY_INDEX = "personAddresses.address.city.country.shortName";
    private static final String COUNTRY2_INDEX = "preferredLocation.country.shortName";
    private static final String TIMEZONE_INDEX = "timezone";
    private static final String SKILL_INDEX = "biographies.biographySkills.skill.shortName";
    private static final String PROFICIENCY_INDEX = "biographies.biographySkills.proficiency";
    private static final String LANGUAGE_TYPE_INDEX = "educations.languages.type";
    private static final String LANGUAGE_VERBAL_INDEX = "educations.languages.verbal";
    private static final String LANGUAGE_READWRITE_INDEX = "educations.languages.readwrite";
    private static final String HOME_EMAIL_INDEX = "homeEmail";
    private static final String WORK_EMAIL_INDEX = "workEmail";
    private static final String PREFERRED_EMAIL_INDEX = "preferredEmail";
    private static final String HOMEPHONE_COUNTRYCODE_INDEX = "homePhoneCountryCode";
    private static final String WORKPHONE_COUNTRYCODE_INDEX = "workPhoneCountryCode";
    private static final String MOBILEPHONE_COUNTRYCODE_INDEX = "mobilePhoneCountryCode";
    private static final String PREFERREDPHONE_COUNTRYCODE_INDEX = "preferredPhoneCountryCode";
    private static final String FAX_COUNTRYCODE_INDEX = "faxCountryCode";
    private static final String HOMEPHONE_NUMBER_INDEX = "homePhone";
    private static final String WORKPHONE_NUMBER_INDEX = "workPhone";
    private static final String MOBILEPHONE_NUMBER_INDEX = "mobilePhone";
    private static final String PREFERREDPHONE_NUMBER_INDEX = "preferredPhone";
    private static final String FAX_NUMBER_INDEX = "fax";
    private static final String HOMEPHONE_EXTENSION_INDEX = "homePhoneExtension";
    private static final String WORKPHONE_EXTENSION_INDEX = "workPhoneExtension";
    private static final String MOBILEPHONE_EXTENSION_INDEX = "mobilePhoneExtension";
    private static final String PREFERREDPHONE_EXTENSION_INDEX = "preferredPhoneExtension";
    private static final String DEGREE_SCHOOL_SHORTNAME_INDEX = "educations.degrees.school.shortName";
    private static final String DEGREE_SCHOOL_LONGNAME_INDEX = "educations.degrees.school.longName";
    private static final String DEGREE_SCHOOL_CODE_INDEX = "educations.degrees.school.code";
    private static final String DEGREE_SCHOOL_MAJOR_INDEX = "educations.degrees.major";
    private static final String DEGREE_SCHOOL_DEGREE_INDEX = "educations.degrees.type";
    private static final String COMPANY_CODE_INDEX = "biographies.biographyDepartments.department.division.company.code";
    private static final String COMPANY_SHORTNAME_INDEX = "biographies.biographyDepartments.department.division.company.shortName";
    private static final String COMPANY_LONGNAME_INDEX = "biographies.biographyDepartments.department.division.company.longName";
    private static final String COMPANY_NAMES_INDEX = "biographies.biographyDepartments.department.division.company.companyNames.shortName";
    private static final String COMPANY_INDUSTRY_SHORTNAME_INDEX = "biographies.biographyDepartments.department.division.company.companyIndustries.industry.shortName";
    private static final String PROJECT_NAME_INDEX = "histories.projects.name";
    private static final String PROJECT_COMPANY_NAME_INDEX = "histories.projects.company.shortName";
    private static final String PROJECT_ROLE_INDEX = "histories.projects.skilledRole.shortName";
    private static final String EMPLOYMENT_TYPE_INDEX = "currentEmploymentType";
    private static final String COVERAGE_CLIENT_INDEX = "histories.coverages.company.shortName";
    private static final String COVERAGE_ROLE_INDEX = "histories.coverages.skilledRole.shortName";
    private static final String COVERAGE_PRODUCT_INDEX = "histories.coverages.product.shortName";
    private static final String COVERAGE_TERRITORY_INDEX = "histories.coverages.region.shortName";
    private static final String COVERAGE_CURRENT_INDEX = "histories.coverages.current";
    private static final String CERTIFICATION_TITLE_INDEX = "educations.educationCertifications.title";
    private static final String CERTIFICATION_ISSUED_BY_INDEX = "educations.educationCertifications.issuedBy";
    private static final String CERTIFICATION_YEAR_INDEX = "educations.educationCertifications.issueDate";
    private static final String ORGANIZATION_NAME_INDEX = "histories.affiliations.organization";
    private static final String ORGANIZATION_ROLE_INDEX = "histories.affiliations.role";
    private static final String INTEREST_INDEX = "interests.shortName";
    private static final String AWARD_NAME_INDEX = "biographies.awards.awardName";
    private static final String AWARD_ISSUER_INDEX = "biographies.awards.issuer";
    private static final String AWARD_YEAR_INDEX = "biographies.awards.issuedDate";
    private static final String PUBLICATION_TYPE_INDEX = "biographies.publications.type";
    private static final String PUBLICATION_TITLE_INDEX = "biographies.publications.description";
    private static final String PUBLICATION_TITLE2_INDEX = "biographies.publications.shortName";
    private static final String PATENT_CODE_INDEX = "biographies.patents.code";
    private static final String PATENT_NAME_INDEX = "biographies.patents.shortName";
    private static final String AVAILABILITY_DATE_INDEX = "availability";
    private static final String AVAILABILITY_TYPE_INDEX = "availabilityType";
    private static final String PREFERRED_SALARY_INDEX = "preferredSalary";
    private static final String CURRENT_SALARY_INDEX = "currentSalary";


    @Override
    public void purgeSearchIndex(Class aClass) throws PersistenceException {
        if (log.isDebugEnabled()) {
            log.debug("Purging all Lucene caches for class: " + aClass.getName());
        }

        FullTextSession fts = org.hibernate.search.Search.getFullTextSession(getSession());
        fts.purgeAll(aClass);
        fts.flushToIndexes();
        fts.getSearchFactory().optimize(aClass);
    }

    @Override
    public void optimize() {
        if (log.isDebugEnabled()) {
            log.debug("Optimizing all Lucene caches...");
        }

        FullTextSession fts = org.hibernate.search.Search.getFullTextSession(getSession());
        fts.getSearchFactory().optimize();

        if (log.isDebugEnabled()) {
            log.debug("Lucene caches optimized");
        }
    }

    /**
     * The advanced search methods are by far the most complex in the whole system. They are responsible for
     * interpreting advanced search queries coming from the user.
     * <p/>
     * It will delegate looping through every list item and constructing a lucene query. Every query needs to contain
     * a result before being added to the final query
     *
     * @param search
     * @return
     * @throws PersistenceException
     */
    @Override
    public Integer searchCount(Search search) throws PersistenceException {
        FullTextQuery ftq = createFullTextQuery(createSearchQuery(search));

        if (log.isTraceEnabled()) {
            log.trace("Executing Lucene query: \n" + ftq.getQueryString());
        }

        return ftq.getResultSize();
    }

    /**
     * The advanced search methods are by far the most complex in the whole system. They are responsible for
     * interpreting advanced search queries coming from the user.
     * <p/>
     * It will delegate looping through every list item and constructing a lucene query. Every query needs to contain
     * a result before being added to the final query
     *
     * @param search
     * @param index
     * @param maxResults
     * @return
     * @throws PersistenceException
     */
    @Override
    public Search searchEntities(Search search, Integer index, Integer maxResults) throws PersistenceException {
        return search(search, index, maxResults, new String[]{FullTextQuery.THIS, FullTextQuery.SCORE, FullTextQuery.DOCUMENT_ID});
    }

    /**
     * The advanced search methods are by far the most complex in the whole system. They are responsible for
     * interpreting advanced search queries coming from the user.
     * <p/>
     * It will delegate looping through every list item and constructing a lucene query. Every query needs to contain
     * a result before being added to the final query
     *
     * @param search
     * @param index
     * @param maxResults
     * @return
     * @throws PersistenceException
     */
    @Override
    public Search searchIds(Search search, Integer index, Integer maxResults) throws PersistenceException {
        return search(search, index, maxResults, new String[]{FullTextQuery.ID, FullTextQuery.SCORE, FullTextQuery.DOCUMENT_ID});
    }

    private Search search(Search search, Integer index, Integer maxResults, String[] projections) throws PersistenceException {
        FullTextQuery ftq = createFullTextQuery(createSearchQuery(search));

        if (index != null && maxResults != null) {
            ftq.setFirstResult(index * maxResults);
            ftq.setMaxResults(maxResults);
        }

        // sort by relevancy
        Sort sort = new Sort(SortField.FIELD_SCORE);
        ftq.setSort(sort);
        ftq.setProjection(projections);

        if (log.isTraceEnabled()) {
            log.trace("Executing Lucene query: \n" + ftq.getQueryString());
        }

        List<Object[]> result = ftq.list();

        search.setRawSearchResults(result);

        logSearchResults(ftq, result);

        return search;
    }

    /**
     * Loops through every searchcomponent created by the caller. Caller can pass along an arbitrary list of these components.
     * If a type is encountered more than once, they will be grouped together and connected with an OR
     *
     * @param search
     * @return
     */
    private BooleanQuery createSearchQuery(Search search) throws PersistenceException {

        if (log.isDebugEnabled()) {
            log.debug("Processing advanced search components");
        }

        BooleanQuery finalQuery = new BooleanQuery();
        Query query = null;

        if (search.getGroups() != null && !search.getGroups().isEmpty()) {
            for (SearchComponentGroup group : search.getGroups()) {
                query = null;
                if (log.isDebugEnabled()) {
                    log.debug("Processing " + group.getType() + " components from Advanced Search...");
                }
                try {

                    switch (group.getType()) {
                        case NAME:
                            List<Query> nameQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createNameComponentQuery(component);

                                if (query != null) {
                                    if (nameQueries == null) {
                                        nameQueries = new ArrayList<Query>();
                                    }

                                    nameQueries.add(query);
                                }
                            }

                            if (nameQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, nameQueries);
                            }
                            break;
                        case EMAIL:
                            List<Query> emailQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createEmailComponentQuery(component);

                                if (query != null) {
                                    if (emailQueries == null) {
                                        emailQueries = new ArrayList<Query>();
                                    }

                                    emailQueries.add(query);
                                }
                            }

                            if (emailQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, emailQueries);
                            }
                            break;
                        case PHONE:
                            List<Query> phoneQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createPhoneComponentQuery(component);

                                if (query != null) {
                                    if (phoneQueries == null) {
                                        phoneQueries = new ArrayList<Query>();
                                    }

                                    phoneQueries.add(query);
                                }
                            }

                            if (phoneQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, phoneQueries);
                            }
                            break;
                        case LOCATION:
                            List<Query> locationQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createLocationComponentQuery(component);

                                if (query != null) {
                                    if (locationQueries == null) {
                                        locationQueries = new ArrayList<Query>();
                                    }

                                    locationQueries.add(query);
                                }
                            }

                            if (locationQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, locationQueries);
                            }
                            break;
                        case SKILLS_EXPERTISE:
                            List<Query> skillsQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createSkillsExpertiseComponentQuery(component);

                                if (query != null) {
                                    if (skillsQueries == null) {
                                        skillsQueries = new ArrayList<Query>();
                                    }

                                    skillsQueries.add(query);
                                }
                            }

                            if (skillsQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, skillsQueries);
                            }
                            break;
                        case COMPANY_INDUSTRY:
                            List<Query> companiesQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createCompanyIndustryComponentQuery(component);

                                if (query != null) {
                                    if (companiesQueries == null) {
                                        companiesQueries = new ArrayList<Query>();
                                    }

                                    companiesQueries.add(query);
                                }
                            }

                            if (companiesQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, companiesQueries);
                            }
                            break;
                        case POSITION_ROLES:
                            List<Query> roleQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createPositionAndRolesComponentQuery(component);

                                if (query != null) {
                                    if (roleQueries == null) {
                                        roleQueries = new ArrayList<Query>();
                                    }

                                    roleQueries.add(query);
                                }
                            }

                            if (roleQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, roleQueries);
                            }
                            break;
                        case PROJECTS:
                            List<Query> projectQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createProjectComponentQuery(component);

                                if (query != null) {
                                    if (projectQueries == null) {
                                        projectQueries = new ArrayList<Query>();
                                    }

                                    projectQueries.add(query);
                                }
                            }

                            if (projectQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, projectQueries);
                            }
                            break;
                        case COVERAGE:
                            List<Query> coverageQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createCoverageComponentQuery(component);

                                if (query != null) {
                                    if (coverageQueries == null) {
                                        coverageQueries = new ArrayList<Query>();
                                    }

                                    coverageQueries.add(query);
                                }
                            }

                            if (coverageQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, coverageQueries);
                            }
                            break;
                        case AVAILABILITY:
                            List<Query> availabilityQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createAvailabilityComponentQuery(component);

                                if (query != null) {
                                    if (availabilityQueries == null) {
                                        availabilityQueries = new ArrayList<Query>();
                                    }

                                    availabilityQueries.add(query);
                                }
                            }

                            if (availabilityQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, availabilityQueries);
                            }
                            break;
                        case EMPLOYMENT_TYPE:
                            ArrayList<Query> employmentTypeQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createEmploymentTypeComponentQuery(component);

                                if (query != null) {
                                    if (employmentTypeQueries == null) {
                                        employmentTypeQueries = new ArrayList<Query>();
                                    }

                                    employmentTypeQueries.add(query);
                                }
                            }

                            if (employmentTypeQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, employmentTypeQueries);
                            }
                            break;
                        case EDUCATION:
                            List<Query> educationQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createEducationComponentQuery(component);

                                if (query != null) {
                                    if (educationQueries == null) {
                                        educationQueries = new ArrayList<Query>();
                                    }

                                    educationQueries.add(query);
                                }
                            }

                            if (educationQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, educationQueries);
                            }
                            break;
                        case CERTIFICATION:
                            List<Query> certificationQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createCertificationComponentQuery(component);

                                if (query != null) {
                                    if (certificationQueries == null) {
                                        certificationQueries = new ArrayList<Query>();
                                    }

                                    certificationQueries.add(query);
                                }
                            }

                            if (certificationQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, certificationQueries);
                            }
                            break;
                        case LANGUAGE:
                            List<Query> languagesQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createLanguageComponentQuery(component);

                                if (query != null) {
                                    if (languagesQueries == null) {
                                        languagesQueries = new ArrayList<Query>();
                                    }

                                    languagesQueries.add(query);
                                }
                            }

                            if (languagesQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, languagesQueries);
                            }
                            break;
                        case RATE:
                            List<Query> rateQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createRateComponentQuery(component);

                                if (query != null) {
                                    if (rateQueries == null) {
                                        rateQueries = new ArrayList<Query>();
                                    }

                                    rateQueries.add(query);
                                }
                            }

                            if (rateQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, rateQueries);
                            }
                            break;
                        case ORGANIZATION:
                            List<Query> organizationQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createOrganizationComponentQuery(component);

                                if (query != null) {
                                    if (organizationQueries == null) {
                                        organizationQueries = new ArrayList<Query>();
                                    }

                                    organizationQueries.add(query);
                                }
                            }

                            if (organizationQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, organizationQueries);
                            }
                            break;
                        case INTEREST:
                            List<Query> interestQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createInterestComponentQuery(component);

                                if (query != null) {
                                    if (interestQueries == null) {
                                        interestQueries = new ArrayList<Query>();
                                    }

                                    interestQueries.add(query);
                                }
                            }

                            if (interestQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, interestQueries);
                            }
                            break;
                        case AWARDS:
                            List<Query> awardQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createAwardComponentQuery(component);

                                if (query != null) {
                                    if (awardQueries == null) {
                                        awardQueries = new ArrayList<Query>();
                                    }

                                    awardQueries.add(query);
                                }
                            }

                            if (awardQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, awardQueries);
                            }
                            break;
                        case PUBLICATIONS:
                            List<Query> publicationQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createPublicationComponentQuery(component);

                                if (query != null) {
                                    if (publicationQueries == null) {
                                        publicationQueries = new ArrayList<Query>();
                                    }

                                    publicationQueries.add(query);
                                }
                            }

                            if (publicationQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, publicationQueries);
                            }
                            break;
                        case PATENTS:
                            List<Query> patentQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createPatentComponentQuery(component);

                                if (query != null) {
                                    if (patentQueries == null) {
                                        patentQueries = new ArrayList<Query>();
                                    }

                                    patentQueries.add(query);
                                }
                            }

                            if (patentQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, patentQueries);
                            }
                            break;
                        case DEMOGRAPHIC:
                            log.error(group.getType() + " search component has not yet been implemented");
                            break;
                        case QUICK_SEARCH:
                            List<Query> quickSearchQueries = null;
                            for (SearchComponent component : group.getComponents()) {
                                query = createQuickSearchComponentQuery(component);

                                if (query != null) {
                                    if (quickSearchQueries == null) {
                                        quickSearchQueries = new ArrayList<Query>();
                                    }

                                    quickSearchQueries.add(query);
                                }
                            }

                            if (quickSearchQueries != null) {
                                addGroupedQueriesToBooleanQuery(finalQuery, quickSearchQueries);
                            }
                            break;
                        default:
                            log.error("How did we get here????: " + group.getType());
                    }
                } catch (IllegalArgumentException e) {
                    String error = "Illegal group type: " + group.getType();
                    log.error(error);
                    throw new PersistenceException(error);
                } catch (NullPointerException e) {
                    String error = "NPE on group type: " + group.getType() + ": " + e.getMessage();
                    log.error(error, e);
                    throw new PersistenceException(error, e);
                }

                if (log.isDebugEnabled()) {
                    log.debug("Processed " + group.getType() + " component from Advanced Search complete");
                }
            }
        } else {
            log.warn("No SearchComponentGroups were passed with the request.");
        }

        return finalQuery;
    }

    private Query createQuickSearchComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String searchString = map.get(SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS.name() + " = " + searchString);
        }

        // here we are looping through every search index and nested within this loop is
        // another loop of keywords. We want this to mean:
        // At least one of the keywords need to match one of the search indexes to be valid
        // e.g. index1 needs to equal (keyword 1 OR keyword 2) AND index2 needs to equal (keyword 1 OR keyword 2)
        List<String> keywords = processSearchKeywordString(searchString);

        // we will only include indexes that have results in them
        List<String> validSearchIndexes = determineSearchIndexes(QUICK_SEARCH_INDEXES, keywords);

        if (keywords != null && !keywords.isEmpty()) {
            for (String searchIndex : validSearchIndexes) {
                // every keyword should match at least one of the different indexes
                BooleanQuery bq2 = new BooleanQuery();

                // loop through every keyword and construct a term query for every index we'd like to search on
                for (String s : keywords) {
                    bq2.add(determineQueryType(searchIndex, s, BooleanTypeCd.FALSE.name(), Boolean.TRUE), BooleanClause.Occur.SHOULD);
                }

                bq.add(bq2, BooleanClause.Occur.MUST);
            }
        }


        return bq;
    }

    /**
     * Returns a Query that will accurately search within all fields indicated in that component
     *
     * @param component
     * @return
     */
    private Query createNameComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String firstName = map.get(SearchComponentFieldNameCd.NAME_FIRST_NAME.name());
        String lastName = map.get(SearchComponentFieldNameCd.NAME_LAST_NAME.name());
        String middleName = map.get(SearchComponentFieldNameCd.NAME_MIDDLE_NAME.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" + SearchComponentFieldNameCd.NAME_FIRST_NAME.name() + " = " + firstName + "\n" +
                    SearchComponentFieldNameCd.NAME_LAST_NAME.name() + " = " + lastName + "\n" +
                    SearchComponentFieldNameCd.NAME_MIDDLE_NAME.name() + " = " + middleName + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(firstName)) {
                bq.add(determineQueryType(FIRST_NAME_INDEX, firstName, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(middleName)) {
                bq.add(determineQueryType(MIDDLE_NAME_INDEX, middleName, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(lastName)) {
                bq.add(determineQueryType(LAST_NAME_INDEX, lastName, exactMatchS, Boolean.TRUE), excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }


        return bq;
    }

    private Query createAvailabilityComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        BooleanQuery bq = new BooleanQuery();
        String availableDate = map.get(SearchComponentFieldNameCd.AVAILABILITY_DATE.name());
        String type = map.get(SearchComponentFieldNameCd.AVAILABILITY_TYPE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.AVAILABILITY_DATE.name() + " = " + availableDate + "\n" +
                    SearchComponentFieldNameCd.AVAILABILITY_TYPE.name() + " = " + type + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(availableDate)) {
                Date now = df.parse(availableDate);

                // we want to match on availability ranges from 6 months before the specified date to 30 days into the future
                Calendar futureDate = GregorianCalendar.getInstance();
                futureDate.setTime(now);
                futureDate.add(Calendar.DAY_OF_YEAR, 30);

                Calendar pastDate = GregorianCalendar.getInstance();
                pastDate.setTime(now);
                pastDate.add(Calendar.MONTH, -6);

                Term lowerTerm = new Term(AVAILABILITY_DATE_INDEX, df.format(pastDate.getTime()));
                Term upperTerm = new Term(AVAILABILITY_DATE_INDEX, df.format(futureDate.getTime()));

                RangeQuery rq = new RangeQuery(lowerTerm, upperTerm, true);
                bq.add(rq, excludeOccur);
            }

            if (StringUtils.isNotBlank(type)) {
                bq.add(determineQueryType(AVAILABILITY_TYPE_INDEX, type, exactMatchS, Boolean.FALSE), excludeOccur);
            }

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }


        return bq;
    }

    private Query createRateComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String rateS = map.get(SearchComponentFieldNameCd.RATE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.RATE.name() + " = " + rateS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(rateS)) {
                SalaryRateCd rate = SalaryRateCd.valueOf(rateS);

                BooleanQuery bq2 = new BooleanQuery();
                RangeQuery rq = null;
                RangeQuery rq2 = null;
                Term lowerTerm = null;
                Term upperTerm = null;

                switch (rate) {
                    case LEVEL_1:
                        lowerTerm = new Term(PREFERRED_SALARY_INDEX, "0");
                        upperTerm = new Term(PREFERRED_SALARY_INDEX, "050000.0");

                        rq = new RangeQuery(lowerTerm, upperTerm, true);

                        lowerTerm = new Term(CURRENT_SALARY_INDEX, "0");
                        upperTerm = new Term(CURRENT_SALARY_INDEX, "050000.0");

                        rq2 = new RangeQuery(lowerTerm, upperTerm, true);

                        bq2.add(rq, BooleanClause.Occur.SHOULD);
                        bq2.add(rq2, BooleanClause.Occur.SHOULD);

                        bq.add(bq2, BooleanClause.Occur.MUST);

                        break;
                    case LEVEL_2:
                        lowerTerm = new Term(PREFERRED_SALARY_INDEX, "050001.0");
                        upperTerm = new Term(PREFERRED_SALARY_INDEX, "100000.0");

                        rq = new RangeQuery(lowerTerm, upperTerm, true);

                        lowerTerm = new Term(CURRENT_SALARY_INDEX, "050001.0");
                        upperTerm = new Term(CURRENT_SALARY_INDEX, "100000.0");

                        rq2 = new RangeQuery(lowerTerm, upperTerm, true);

                        bq2.add(rq, BooleanClause.Occur.SHOULD);
                        bq2.add(rq2, BooleanClause.Occur.SHOULD);

                        bq.add(bq2, BooleanClause.Occur.MUST);
                        break;
                    case LEVEL_3:
                        lowerTerm = new Term(PREFERRED_SALARY_INDEX, "100001.0");
                        upperTerm = new Term(PREFERRED_SALARY_INDEX, "250000.0");

                        rq = new RangeQuery(lowerTerm, upperTerm, true);

                        lowerTerm = new Term(CURRENT_SALARY_INDEX, "100001.0");
                        upperTerm = new Term(CURRENT_SALARY_INDEX, "250000.0");

                        rq2 = new RangeQuery(lowerTerm, upperTerm, true);

                        bq2.add(rq, BooleanClause.Occur.SHOULD);
                        bq2.add(rq2, BooleanClause.Occur.SHOULD);

                        bq.add(bq2, BooleanClause.Occur.MUST);
                        break;
                    case LEVEL_4:
                        lowerTerm = new Term(PREFERRED_SALARY_INDEX, "250001.0");
                        upperTerm = new Term(PREFERRED_SALARY_INDEX, "999999.0");

                        rq = new RangeQuery(lowerTerm, upperTerm, true);

                        lowerTerm = new Term(CURRENT_SALARY_INDEX, "250001.0");
                        upperTerm = new Term(CURRENT_SALARY_INDEX, "999999.0");

                        rq2 = new RangeQuery(lowerTerm, upperTerm, true);

                        bq2.add(rq, BooleanClause.Occur.SHOULD);
                        bq2.add(rq2, BooleanClause.Occur.SHOULD);

                        bq.add(bq2, BooleanClause.Occur.MUST);
                        break;
                    default:
                        log.error("Unsupported rate level: " + rate);
                }

            }

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createCertificationComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String title = map.get(SearchComponentFieldNameCd.CERTIFICATION_TITLE.name());
        String issuedBy = map.get(SearchComponentFieldNameCd.CERTIFICATION_ISSUED_BY.name());
        String issueYear = map.get(SearchComponentFieldNameCd.CERTIFICATION_ISSUE_DATE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.CERTIFICATION_TITLE.name() + " = " + title + "\n" +
                    SearchComponentFieldNameCd.CERTIFICATION_ISSUED_BY.name() + " = " + issuedBy + "\n" +
                    SearchComponentFieldNameCd.CERTIFICATION_ISSUE_DATE.name() + " = " + issueYear + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(title)) {
                bq.add(determineQueryType(CERTIFICATION_TITLE_INDEX, title, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(issueYear)) {
                bq.add(determineQueryType(CERTIFICATION_YEAR_INDEX, issueYear, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(issuedBy)) {
                bq.add(determineQueryType(CERTIFICATION_ISSUED_BY_INDEX, issuedBy, exactMatchS, Boolean.TRUE), excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createAwardComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String name = map.get(SearchComponentFieldNameCd.AWARD_NAME.name());
        String issuer = map.get(SearchComponentFieldNameCd.AWARD_ISSUER.name());
        String issueYear = map.get(SearchComponentFieldNameCd.AWARD_ISSUE_DATE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.AWARD_NAME.name() + " = " + name + "\n" +
                    SearchComponentFieldNameCd.AWARD_ISSUER.name() + " = " + issuer + "\n" +
                    SearchComponentFieldNameCd.AWARD_ISSUE_DATE.name() + " = " + issueYear + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(name)) {
                bq.add(determineQueryType(AWARD_NAME_INDEX, name, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(issueYear)) {
                bq.add(determineQueryType(AWARD_YEAR_INDEX, issueYear, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(issuer)) {
                bq.add(determineQueryType(AWARD_ISSUER_INDEX, issuer, exactMatchS, Boolean.TRUE), excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createOrganizationComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String name = map.get(SearchComponentFieldNameCd.ORGANIZATION_NAME.name());
        String role = map.get(SearchComponentFieldNameCd.ORGANIZATION_ROLE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.ORGANIZATION_NAME.name() + " = " + name + "\n" +
                    SearchComponentFieldNameCd.ORGANIZATION_ROLE.name() + " = " + role + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(name)) {
                bq.add(determineQueryType(ORGANIZATION_NAME_INDEX, name, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(role)) {
                bq.add(determineQueryType(ORGANIZATION_ROLE_INDEX, role, exactMatchS, Boolean.TRUE), excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createPatentComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String code = map.get(SearchComponentFieldNameCd.PATENT_CODE.name());
        String name = map.get(SearchComponentFieldNameCd.PATENT_NAME.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.PATENT_CODE.name() + " = " + code + "\n" +
                    SearchComponentFieldNameCd.PATENT_NAME.name() + " = " + name + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(code)) {
                bq.add(determineQueryType(PATENT_CODE_INDEX, code, exactMatchS, Boolean.FALSE), excludeOccur);
            }

            if (StringUtils.isNotBlank(name)) {
                bq.add(determineQueryType(PATENT_NAME_INDEX, name, exactMatchS, Boolean.TRUE), excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createPublicationComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String type = map.get(SearchComponentFieldNameCd.PUBLICATION_TYPE.name());
        String title = map.get(SearchComponentFieldNameCd.PUBLICATION_TITLE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.PUBLICATION_TYPE.name() + " = " + type + "\n" +
                    SearchComponentFieldNameCd.PUBLICATION_TITLE.name() + " = " + title + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(type)) {
                bq.add(determineQueryType(PUBLICATION_TYPE_INDEX, type, exactMatchS, Boolean.FALSE), excludeOccur);
            }

            if (StringUtils.isNotBlank(title)) {
                BooleanQuery bq2 = new BooleanQuery();
                bq2.add(determineQueryType(PUBLICATION_TITLE_INDEX, title, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
                bq2.add(determineQueryType(PUBLICATION_TITLE2_INDEX, title, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
                bq.add(bq2, excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    /**
     * Returns a Query that will accurately search for emails
     *
     * @param component
     * @return
     */
    private Query createEmailComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String username = map.get(SearchComponentFieldNameCd.EMAIL_USERNAME.name());
        String domain = map.get(SearchComponentFieldNameCd.EMAIL_DOMAIN.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.EMAIL_USERNAME.name() + " = " + username + "\n" +
                    SearchComponentFieldNameCd.EMAIL_DOMAIN.name() + " = " + domain + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            String email = username + "*@*" + domain + "*";

            if (StringUtils.isNotBlank(email)) {
                bq.add(determineQueryType(HOME_EMAIL_INDEX, email, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
            }

            if (StringUtils.isNotBlank(email)) {
                bq.add(determineQueryType(WORK_EMAIL_INDEX, email, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
            }

            if (StringUtils.isNotBlank(email)) {
                bq.add(determineQueryType(PREFERRED_EMAIL_INDEX, email, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    /**
     * Returns a Query that will accurately search for degrees
     *
     * @param component
     * @return
     */
    private Query createEducationComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String school = map.get(SearchComponentFieldNameCd.EDUCATION_SCHOOL.name());
        String degree = map.get(SearchComponentFieldNameCd.EDUCATION_DEGREE.name());
        String major = map.get(SearchComponentFieldNameCd.EDUCATION_MAJOR.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.EDUCATION_SCHOOL.name() + " = " + school + "\n" +
                    SearchComponentFieldNameCd.EDUCATION_DEGREE.name() + " = " + degree + "\n" +
                    SearchComponentFieldNameCd.EDUCATION_MAJOR.name() + " = " + major + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {

            BooleanQuery bq1 = new BooleanQuery();

            if (StringUtils.isNotBlank(school)) {
                bq1.add(determineQueryType(DEGREE_SCHOOL_SHORTNAME_INDEX, school, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
                bq1.add(determineQueryType(DEGREE_SCHOOL_LONGNAME_INDEX, school, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
                bq1.add(determineQueryType(DEGREE_SCHOOL_CODE_INDEX, school, exactMatchS, Boolean.FALSE), BooleanClause.Occur.SHOULD);
                bq.add(bq1, BooleanClause.Occur.MUST);
            }

            if (StringUtils.isNotBlank(degree)) {
                bq.add(determineQueryType(DEGREE_SCHOOL_DEGREE_INDEX, degree, exactMatchS, Boolean.FALSE), BooleanClause.Occur.MUST);
            }

            if (StringUtils.isNotBlank(major)) {
                bq.add(determineQueryType(DEGREE_SCHOOL_MAJOR_INDEX, major, exactMatchS, Boolean.FALSE), BooleanClause.Occur.MUST);
            }

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    /**
     * Returns a Query that will accurately search for phone numbers
     *
     * @param component
     * @return
     */
    private Query createPhoneComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String countrycode = map.get(SearchComponentFieldNameCd.PHONE_COUNTRYCODE.name());
        String number = map.get(SearchComponentFieldNameCd.PHONE_NUMBER.name());
        String extension = map.get(SearchComponentFieldNameCd.PHONE_EXTENSION.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.PHONE_COUNTRYCODE.name() + " = " + countrycode + "\n" +
                    SearchComponentFieldNameCd.PHONE_NUMBER.name() + " = " + number + "\n" +
                    SearchComponentFieldNameCd.PHONE_EXTENSION.name() + " = " + extension + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {

            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            // here we are saying to match on any one of the following home number
            BooleanQuery bqHome = new BooleanQuery();
            if (StringUtils.isNotBlank(countrycode)) {
                bqHome.add(determineQueryType(HOMEPHONE_COUNTRYCODE_INDEX, countrycode, exactMatchS, Boolean.FALSE), excludeOccur);
            }
            if (StringUtils.isNotBlank(number)) {
                bqHome.add(determineQueryType(HOMEPHONE_NUMBER_INDEX, number, exactMatchS, Boolean.FALSE), excludeOccur);
            }
            if (StringUtils.isNotBlank(extension)) {
                bqHome.add(determineQueryType(HOMEPHONE_EXTENSION_INDEX, extension, exactMatchS, Boolean.FALSE), excludeOccur);
            }

            bq.add(bqHome, BooleanClause.Occur.SHOULD);

            // here we are saying to match on any one of the following work number
            BooleanQuery bqWork = new BooleanQuery();
            if (StringUtils.isNotBlank(countrycode)) {
                bqWork.add(determineQueryType(WORKPHONE_COUNTRYCODE_INDEX, countrycode, exactMatchS, Boolean.FALSE), excludeOccur);
            }
            if (StringUtils.isNotBlank(number)) {
                bqWork.add(determineQueryType(WORKPHONE_NUMBER_INDEX, number, exactMatchS, Boolean.FALSE), excludeOccur);
            }
            if (StringUtils.isNotBlank(extension)) {
                bqWork.add(determineQueryType(WORKPHONE_EXTENSION_INDEX, extension, exactMatchS, Boolean.FALSE), excludeOccur);
            }

            bq.add(bqWork, BooleanClause.Occur.SHOULD);

            // here we are saying to match on any one of the following mobile number
            BooleanQuery bqMobile = new BooleanQuery();
            if (StringUtils.isNotBlank(countrycode)) {
                bqMobile.add(determineQueryType(MOBILEPHONE_COUNTRYCODE_INDEX, countrycode, exactMatchS, Boolean.FALSE), excludeOccur);
            }
            if (StringUtils.isNotBlank(number)) {
                bqMobile.add(determineQueryType(MOBILEPHONE_NUMBER_INDEX, number, exactMatchS, Boolean.FALSE), excludeOccur);
            }
            if (StringUtils.isNotBlank(extension)) {
                bqMobile.add(determineQueryType(MOBILEPHONE_EXTENSION_INDEX, extension, exactMatchS, Boolean.FALSE), excludeOccur);
            }

            bq.add(bqMobile, BooleanClause.Occur.SHOULD);

            // here we are saying to match on any one of the following preferred number
            BooleanQuery bqPreferred = new BooleanQuery();
            if (StringUtils.isNotBlank(countrycode)) {
                bqPreferred.add(determineQueryType(PREFERREDPHONE_COUNTRYCODE_INDEX, countrycode, exactMatchS, Boolean.FALSE), excludeOccur);
            }
            if (StringUtils.isNotBlank(number)) {
                bqPreferred.add(determineQueryType(PREFERREDPHONE_NUMBER_INDEX, number, exactMatchS, Boolean.FALSE), excludeOccur);
            }
            if (StringUtils.isNotBlank(extension)) {
                bqPreferred.add(determineQueryType(PREFERREDPHONE_EXTENSION_INDEX, extension, exactMatchS, Boolean.FALSE), excludeOccur);
            }

            bq.add(bqPreferred, BooleanClause.Occur.SHOULD);

            // here we are saying to match on any one of the following fax number
            BooleanQuery bqFax = new BooleanQuery();
            if (StringUtils.isNotBlank(countrycode)) {
                bqFax.add(determineQueryType(FAX_COUNTRYCODE_INDEX, countrycode, exactMatchS, Boolean.FALSE), excludeOccur);
            }
            if (StringUtils.isNotBlank(number)) {
                bqFax.add(determineQueryType(FAX_NUMBER_INDEX, number, exactMatchS, Boolean.FALSE), excludeOccur);
            }

            bq.add(bqFax, BooleanClause.Occur.SHOULD);

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    /**
     * Returns a Query that will accurately search within all fields indicated in that component
     *
     * @param component
     * @return
     */
    private Query createLocationComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String address = map.get(SearchComponentFieldNameCd.LOCATION_ADDRESS.name());
        String city = map.get(SearchComponentFieldNameCd.LOCATION_CITY.name());
        String state = map.get(SearchComponentFieldNameCd.LOCATION_STATE.name());
        String country = map.get(SearchComponentFieldNameCd.LOCATION_COUNTRY.name());
        String timezone = map.get(SearchComponentFieldNameCd.LOCATION_TIMEZONE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" + SearchComponentFieldNameCd.LOCATION_ADDRESS.name() + " = " + address + "\n" +
                    SearchComponentFieldNameCd.LOCATION_CITY.name() + " = " + city + "\n" +
                    SearchComponentFieldNameCd.LOCATION_STATE.name() + " = " + state + "\n" +
                    SearchComponentFieldNameCd.LOCATION_COUNTRY.name() + " = " + country + "\n" +
                    SearchComponentFieldNameCd.LOCATION_TIMEZONE.name() + " = " + timezone + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(address)) {
                bq.add(determineQueryType(ADDRESS1_INDEX, address, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            /* if we are going to search in different indexes on the same field, they need to automatically be OR and
            if (StringUtils.isNotBlank(address)) {
                bq.add(determineQueryType(ADDRESS2_INDEX, address, exactMatchS), excludeOccur);
            }
            */

            if (StringUtils.isNotBlank(city)) {
                bq.add(determineQueryType(CITY_INDEX, city, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            /*
            if (StringUtils.isNotBlank(city)) {
                bq.add(determineQueryType(CITY2_INDEX, city, exactMatchS), excludeOccur);
            }
            */

            if (StringUtils.isNotBlank(state)) {
                bq.add(determineQueryType(STATE_INDEX, state, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(country)) {
                bq.add(determineQueryType(COUNTRY_INDEX, country, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            /*
            if (StringUtils.isNotBlank(country)) {
                bq.add(determineQueryType(COUNTRY2_INDEX, country, exactMatchS), excludeOccur);
            }
            */

            if (StringUtils.isNotBlank(timezone)) {
                bq.add(determineQueryType(TIMEZONE_INDEX, timezone, exactMatchS, Boolean.TRUE), excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createSkillsExpertiseComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String skill = map.get(SearchComponentFieldNameCd.SKILL_EXPERTISE.name());
        String proficiency = map.get(SearchComponentFieldNameCd.SKILL_PROFICIENCY.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" + SearchComponentFieldNameCd.SKILL_EXPERTISE.name() + " = " + skill + "\n" +
                    SearchComponentFieldNameCd.SKILL_PROFICIENCY.name() + " = " + proficiency + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(skill)) {
                bq.add(determineQueryType(SKILL_INDEX, skill, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(proficiency)) {
                bq.add(determineQueryType(PROFICIENCY_INDEX, proficiency, exactMatchS, Boolean.FALSE), excludeOccur);
            }

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createCompanyIndustryComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String company = map.get(SearchComponentFieldNameCd.COMPANY_COMPANY.name());
        String industry = map.get(SearchComponentFieldNameCd.COMPANY_INDUSTRY.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" + SearchComponentFieldNameCd.COMPANY_COMPANY.name() + " = " + company + "\n" +
                    SearchComponentFieldNameCd.COMPANY_INDUSTRY.name() + " = " + industry + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            BooleanQuery bq1 = new BooleanQuery();

            if (StringUtils.isNotBlank(company)) {
                bq1.add(determineQueryType(COMPANY_CODE_INDEX, company, exactMatchS, Boolean.FALSE), BooleanClause.Occur.SHOULD);
                bq1.add(determineQueryType(COMPANY_LONGNAME_INDEX, company, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
                bq1.add(determineQueryType(COMPANY_SHORTNAME_INDEX, company, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
                bq1.add(determineQueryType(COMPANY_NAMES_INDEX, company, exactMatchS, Boolean.TRUE), BooleanClause.Occur.SHOULD);
                bq.add(bq1, excludeOccur);
            }

            if (StringUtils.isNotBlank(industry)) {
                bq.add(determineQueryType(COMPANY_INDUSTRY_SHORTNAME_INDEX, industry, exactMatchS, Boolean.TRUE), excludeOccur);
            }

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createPositionAndRolesComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String positionRoleType = map.get(SearchComponentFieldNameCd.POSITION_ROLE_TYPE.name());
        String positionRole = map.get(SearchComponentFieldNameCd.POSITION_ROLE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" + SearchComponentFieldNameCd.POSITION_ROLE_TYPE.name() + " = " + positionRoleType + "\n" +
                    SearchComponentFieldNameCd.POSITION_ROLE.name() + " = " + positionRole + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(positionRoleType)) {
                PositionRoleTypeCd type = PositionRoleTypeCd.valueOf(positionRoleType);
                String index = null;

                switch (type) {
                    case TITLE:
                        index = TITLE_INDEX;
                        break;
                    case ROLE:
                        index = ROLE_INDEX;
                        break;
                    default:
                        log.warn("What are we doing here?");
                }

                bq.add(determineQueryType(index, positionRole, exactMatchS, Boolean.TRUE), excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createProjectComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String role = map.get(SearchComponentFieldNameCd.PROJECT_SKILLED_ROLE.name());
        String name = map.get(SearchComponentFieldNameCd.PROJECT_NAME.name());
        String companyName = map.get(SearchComponentFieldNameCd.PROJECT_COMPANY_NAME.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" + SearchComponentFieldNameCd.PROJECT_SKILLED_ROLE.name() + " = " + role + "\n" +
                    SearchComponentFieldNameCd.PROJECT_NAME.name() + " = " + name + "\n" +
                    SearchComponentFieldNameCd.PROJECT_COMPANY_NAME.name() + " = " + companyName + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(role)) {
                bq.add(determineQueryType(PROJECT_ROLE_INDEX, role, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(name)) {
                bq.add(determineQueryType(PROJECT_NAME_INDEX, name, exactMatchS, Boolean.TRUE), excludeOccur);
            }

            if (StringUtils.isNotBlank(companyName)) {
                bq.add(determineQueryType(PROJECT_COMPANY_NAME_INDEX, companyName, exactMatchS, Boolean.TRUE), excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createEmploymentTypeComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        Query result = null;
        String type = map.get(SearchComponentFieldNameCd.EMPLOYMENT_TYPE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" + SearchComponentFieldNameCd.EMPLOYMENT_TYPE.name() + " = " + type + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(type)) {
                result = determineQueryType(EMPLOYMENT_TYPE_INDEX, type, exactMatchS, Boolean.FALSE);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return result;
    }

    private Query createInterestComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        Query result = null;
        String interest = map.get(SearchComponentFieldNameCd.INTEREST.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" +
                    SearchComponentFieldNameCd.INTEREST.name() + " = " + interest + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(interest)) {
                result = determineQueryType(INTEREST_INDEX, interest, exactMatchS, Boolean.TRUE);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return result;
    }

    private Query createCoverageComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String typeS = map.get(SearchComponentFieldNameCd.COVERAGE_TYPE.name());
        String value = map.get(SearchComponentFieldNameCd.COVERAGE_VALUE.name());
        String currentPrevious = map.get(SearchComponentFieldNameCd.COVERAGE_CURRENT_PREVIOUS.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" + SearchComponentFieldNameCd.COVERAGE_TYPE.name() + " = " + typeS + "\n" +
                    SearchComponentFieldNameCd.COVERAGE_VALUE.name() + " = " + value + "\n" +
                    SearchComponentFieldNameCd.COVERAGE_CURRENT_PREVIOUS.name() + " = " + currentPrevious + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);
            CoverageTypeCd type = CoverageTypeCd.valueOf(typeS);

            switch (type) {
                case CLIENT:
                    bq.add(determineQueryType(COVERAGE_CLIENT_INDEX, value, exactMatchS, Boolean.TRUE), excludeOccur);
                    break;
                case ROLE:
                    bq.add(determineQueryType(COVERAGE_ROLE_INDEX, value, exactMatchS, Boolean.TRUE), excludeOccur);
                    break;
                case PRODUCT:
                    bq.add(determineQueryType(COVERAGE_PRODUCT_INDEX, value, exactMatchS, Boolean.TRUE), excludeOccur);
                    break;
                case TERRITORY:
                    bq.add(determineQueryType(COVERAGE_TERRITORY_INDEX, value, exactMatchS, Boolean.TRUE), excludeOccur);
                    break;
                default:
                    log.error("What are we doing here? Unsupported Type: " + type);
            }

            if (StringUtils.isNotBlank(currentPrevious)) {
                bq.add(determineQueryType(COVERAGE_CURRENT_INDEX, currentPrevious, exactMatchS, Boolean.FALSE), excludeOccur);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    private Query createLanguageComponentQuery(SearchComponent component) throws PersistenceException {
        Map<String, String> map = component.getSearchMap();

        BooleanQuery bq = new BooleanQuery();
        String type = map.get(SearchComponentFieldNameCd.LANGUAGE_TYPE.name());
        String verbal = map.get(SearchComponentFieldNameCd.LANGUAGE_VERBAL.name());
        String readwrite = map.get(SearchComponentFieldNameCd.LANGUAGE_READWRITE.name());
        String excludeS = map.get(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name());
        String exactMatchS = map.get(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name());

        if (log.isDebugEnabled()) {
            log.debug("Extracted the following values: \n" + SearchComponentFieldNameCd.LANGUAGE_TYPE.name() + " = " + type + "\n" +
                    SearchComponentFieldNameCd.LANGUAGE_VERBAL.name() + " = " + verbal + "\n" +
                    SearchComponentFieldNameCd.LANGUAGE_READWRITE.name() + " = " + readwrite + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name() + " = " + excludeS + "\n" +
                    SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name() + " = " + exactMatchS
            );
        }

        try {
            BooleanClause.Occur excludeOccur = determineOccurence(excludeS);

            if (StringUtils.isNotBlank(type)) {
                bq.add(determineQueryType(LANGUAGE_TYPE_INDEX, type, exactMatchS, Boolean.FALSE), excludeOccur);
            }

            if (StringUtils.isNotBlank(verbal)) {
                bq.add(determineQueryType(LANGUAGE_VERBAL_INDEX, verbal, exactMatchS, Boolean.FALSE), excludeOccur);
            }

            if (StringUtils.isNotBlank(readwrite)) {
                bq.add(determineQueryType(LANGUAGE_READWRITE_INDEX, readwrite, exactMatchS, Boolean.FALSE), excludeOccur);
            }

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e.getMessage(), e);
        }

        return bq;
    }

    /**
     * Determines whether the field should be included or excluded in the search. Defaults to include.
     *
     * @param excludeS
     * @return
     * @throws PersistenceException
     */
    private BooleanClause.Occur determineOccurence(String excludeS) throws PersistenceException {
        BooleanClause.Occur occur = null;

        if (StringUtils.isNotBlank(excludeS)) {

            try {
                BooleanTypeCd exclude = BooleanTypeCd.valueOf(excludeS);

                switch (exclude) {
                    case TRUE:
                        if (log.isDebugEnabled()) {
                            log.debug("Excluding search values for component");
                        }
                        occur = BooleanClause.Occur.MUST_NOT;
                        break;
                    case MAYBE:
                        if (log.isDebugEnabled()) {
                            log.debug("Search values for component are optional");
                        }
                        occur = BooleanClause.Occur.SHOULD;
                        break;
                    case FALSE:
                    default:
                        if (log.isDebugEnabled()) {
                            log.debug("Including values for component");
                        }
                        occur = BooleanClause.Occur.MUST;
                }
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage(), e);
                throw new PersistenceException(e.getMessage(), e);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Exclude is not included. Defaulting to field must be included in search");
            }

            occur = BooleanClause.Occur.MUST;
        }

        return occur;
    }

    /**
     * Creates either a PrefixQuery for non exact matches or a TermQuery for exact matches. If the value contains
     * 2 or more words it creates a nested BooleanQuery with a MUST occurrence
     *
     * @param index
     * @param value
     * @param exactMatchS
     * @return
     * @throws PersistenceException
     */
    private Query determineQueryType(String index, String value, String exactMatchS, Boolean doLuceneTokenize) throws PersistenceException {
        Query result = null;
        BooleanTypeCd exactMatch = null;

        if (StringUtils.isNotBlank(exactMatchS)) {
            try {
                exactMatch = BooleanTypeCd.valueOf(exactMatchS);

                switch (exactMatch) {
                    case TRUE:
                        if (log.isDebugEnabled()) {
                            log.debug("Exact match is required for field: " + value + " with index: " + index);
                        }

                        // let's see if value contains more than one word. Expecting only spaces at this point.
                        result = processValue(index, value, TermQuery.class, doLuceneTokenize);
                        break;
                    case FALSE:
                    default:
                        if (log.isDebugEnabled()) {
                            log.debug("Exact match is NOT required for field: " + value + " with index: " + index);
                        }

                        // let's see if value contains more than one word. Expecting only spaces at this point.
                        result = processValue(index, value, WildcardQuery.class, doLuceneTokenize);
                }
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage(), e);
                throw new PersistenceException(e.getMessage(), e);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Exact match not included. Defaulting to NOT required for field: " + value + " with index: " + index);
            }
            // let's see if value contains more than one word. Expecting only spaces at this point.
            result = processValue(index, value, PrefixQuery.class, doLuceneTokenize);
        }

        return result;
    }

    /**
     * Handles the value that was entered by the user. Will check to see if value contains more than 2 words, in
     * which case it create a new nested BooleanQuery and add the defined Class query to it.
     *
     * @param index
     * @param value
     * @param clazz
     * @param doLuceneTokenize
     * @return
     * @throws PersistenceException
     */
    private Query processValue(String index, String value, Class clazz, Boolean doLuceneTokenize) throws PersistenceException {
        Query result;

        StringTokenizer st = new StringTokenizer(value, " ");

        try {
            Constructor constructor = clazz.getConstructor(Term.class);

            if (st.countTokens() > 1) {

                if (log.isDebugEnabled()) {
                    log.debug("Value contains: " + st.countTokens() + " separate words. Tokenizing and nesting with BooleanQuery");
                }
                BooleanQuery bq = new BooleanQuery();

                while (st.hasMoreTokens()) {
                    String val = st.nextToken();

                    Query query = processValueToken(index, clazz, doLuceneTokenize, constructor, val);
                    bq.add(query, BooleanClause.Occur.MUST);
                }

                result = bq;
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Value only contains 1 word. Processing without tokenizing.");
                }

                result = processValueToken(index, clazz, doLuceneTokenize, constructor, value);
            }
        } catch (NoSuchMethodException e) {
            throw new PersistenceException(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new PersistenceException(e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new PersistenceException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new PersistenceException(e.getMessage(), e);
        }

        return result;
    }

    private Query processValueToken(String index, Class clazz, Boolean doLuceneTokenize, Constructor constructor, String val) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        if (log.isDebugEnabled()) {
            log.debug("Creating query for token: " + val);
        }

        // first we do some default processing
        val = escapeSpecials(val);

        // if the incoming value is an enum, we don't want to process the string value at all, in all other cases we do
        if (doLuceneTokenize) {
            if (log.isDebugEnabled()) {
                log.debug("Token: " + val + " should be processed to make Lucene happy");
            }
            val = processAccordingToLucene(val);
        } else if (log.isDebugEnabled()) {
            log.debug("Token: " + val + " should not be processed to make Lucene happy. Token is an enum");
        }

        if (clazz == WildcardQuery.class) {
            if (log.isDebugEnabled()) {
                log.debug("Using wildcard query. Need to check for ending *");
            }
            // finally we want to add * to queries that are wild cards in case they haven't already been added
            if (!StringUtils.endsWith(val, "*")) {
                val = val + "*";
            }
        }

        Term t = new Term(index, val);

        Query query = (Query) constructor.newInstance(t);
        return query;
    }

    private String processAccordingToLucene(String val) {
        return val.trim().toLowerCase();
    }

    /**
     * Quick check to see if index returns anything for the given keywords
     *
     * @param keywords
     * @return
     * @throws PersistenceException
     */
    private Boolean hasQuickSearchCount(String searchIndex, List<String> keywords) throws PersistenceException {
        Boolean result = null;

        if (keywords != null) {
            BooleanQuery bq1 = new BooleanQuery();

            // loop through every keyword and construct a term query for every index we'd like to search on
            for (String s : keywords) {
                bq1.add(determineQueryType(searchIndex, s, BooleanTypeCd.FALSE.name(), Boolean.TRUE), BooleanClause.Occur.SHOULD);
            }

            if (log.isDebugEnabled()) {
                log.debug("Executing Lucene query: \n" + bq1.toString());
            }

            FullTextSession fts = org.hibernate.search.Search.getFullTextSession(getSession());
            FullTextQuery query = fts.createFullTextQuery(bq1, Person.class);

            result = query.getResultSize() > 0 ? Boolean.TRUE : Boolean.FALSE;
        } else {
            log.error("Cannot search with empty keyword string");
        }

        return result;
    }

    private void logSearchResults(FullTextQuery q1, List<Object[]> result) {

        if (log.isTraceEnabled() && result != null) {
            for (Object[] objects : result) {
                Explanation e = q1.explain((Integer) objects[2]);
                String id = null;

                if (objects[0] instanceof Person) {
                    id = ((Person) objects[0]).getId();
                } else if (objects[0] instanceof String) {
                    id = (String) objects[0];
                }

                log.trace("\n\n\nSearch results explained: \n" + id + " \nmatches\n " + e.toString());
            }

        }
    }

    /**
     * @param indexes
     * @param keywords
     * @return
     * @throws PersistenceException
     */
    private List<String> determineSearchIndexes(String[] indexes, List<String> keywords) throws PersistenceException {
        Map<String, String> searchIndexMap = new HashMap<String, String>();

        for (String keyword : keywords) {
            for (String searchIndex : indexes) {
                List<String> kw = new ArrayList<String>();
                kw.add(keyword);
                List<String> si = new ArrayList<String>();
                si.add(searchIndex);

                if (hasQuickSearchCount(searchIndex, kw)) {
                    if (log.isTraceEnabled()) {
                        log.trace("Search index: " + searchIndex + " is a valid index for keyword: " + keyword);
                    }
                    searchIndexMap.put(searchIndex, searchIndex);
                } else if (log.isTraceEnabled()) {
                    log.trace("Search index: " + searchIndex + " is NOT a valid index for keyword: " + keyword);
                }
            }
        }

        // ok, now we have determined the indexes to use we can create the actual query

        return new ArrayList<String>(searchIndexMap.values());
    }

    /**
     * Convenience method to see if a query returns a result
     *
     * @param query
     * @return
     */
    private FullTextQuery createFullTextQuery(Query query) {
        FullTextSession fts = org.hibernate.search.Search.getFullTextSession(getSession());
        FullTextQuery ftq = fts.createFullTextQuery(query, Person.class);

        return ftq;
    }

    /**
     * Will only add a query to a BooleanQuery if the query has results
     *
     * @param result
     * @param query
     */
    private void addQueryToBooleanQuery(BooleanQuery result, Query query, BooleanClause.Occur occur) {

        // we can do some additional processing here before adding it to the final result
        if (query != null) {
            result.add(query, occur);
        }
    }

    /**
     * Will only add a query to a BooleanQuery if the query has results
     *
     * @param result
     * @param queries
     */
    private void addGroupedQueriesToBooleanQuery(BooleanQuery result, List<Query> queries) {

        // we can do some additional processing here before adding it to the final result
        if (queries != null) {
            if (queries.size() > 1) {
                BooleanQuery bq = new BooleanQuery();

                for (Query query : queries) {
                    addQueryToBooleanQuery(bq, query, BooleanClause.Occur.SHOULD);
                }

                addQueryToBooleanQuery(result, bq, BooleanClause.Occur.MUST);
            } else if (queries.size() == 1) {
                addQueryToBooleanQuery(result, queries.get(0), BooleanClause.Occur.MUST);
            }
        }
    }

    private String escapeSpecials(String clientQuery) {
        String regexOr = "";
        for (String special : SPECIALS) {
            regexOr += (special.equals(SPECIALS[0]) ? "" : "|") + "\\" + special.substring(0, 1);
        }
        clientQuery = clientQuery.replaceAll("(?<!\\\\)(" + regexOr + ")", "\\\\$1");

        return clientQuery.trim();
    }

    private List<String> processSearchKeywordString(String keywords) {
        List<String> result = new ArrayList<String>();

        if (keywords.indexOf(",") > -1) {
            StringTokenizer st = new StringTokenizer(keywords.trim(), ",");

            if (st.hasMoreTokens()) {

                while (st.hasMoreTokens()) {
                    String token = st.nextToken();

                    if (StringUtils.isNotBlank(token)) {
                        result.add(token.trim());
                    }
                }
            }
        } else {
            result.add(keywords.trim());
        }

        if (result != null && log.isTraceEnabled()) {
            log.trace("Search keywords:");
            for (String s : result) {
                log.trace("Keyword: " + s);
            }
        }

        return result;
    }
}
