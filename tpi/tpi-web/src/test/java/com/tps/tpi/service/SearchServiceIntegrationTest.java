package com.tps.tpi.service;

import com.tps.tpi.AbstractTest;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.dto.SearchComponentDto;
import com.tps.tpi.model.objects.dto.SearchComponentGroupDto;
import com.tps.tpi.model.objects.dto.SearchDto;
import com.tps.tpi.model.objects.entity.Company;
import com.tps.tpi.model.objects.entity.User;
import com.tps.tpi.model.objects.enums.*;
import com.tps.tpi.model.objects.lite.PersonLite;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:11:01 PM
 * Responsibility:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class SearchServiceIntegrationTest extends AbstractTest {
    private final static Logger log = LoggerFactory.getLogger(SearchServiceIntegrationTest.class);
    private final static String KEYWORD1a = "java";
    private final static String KEYWORD1b = "developer";
    private final static String KEYWORD1c = "london";
    private final static String KEYWORD2 = "java, developer";
    private final static String KEYWORD3 = "java, developer, london";
    private User u = null;
    private Company c = null;


    @Test
    public void testQuickSearch() {
        log.info("Quick Search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {

            // here's the base code required for a simple search
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS.name(), KEYWORD1a);

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.QUICK_SEARCH;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            log.info("Searching for persons matching keyword(s): " + KEYWORD1a);
            runSearchTest(dto);

            // CHANGE KEYWORD SEARCH
            map.put(SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS.name(), KEYWORD1b);
            dto = createSingleComponentSearchDto(groupType, map);

            log.info("Searching for persons matching keyword(s): " + KEYWORD1b);
            runSearchTest(dto);

            // CHANGE KEYWORD SEARCH
            map.put(SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS.name(), KEYWORD1c);
            dto = createSingleComponentSearchDto(groupType, map);

            log.info("Searching for persons matching keyword(s): " + KEYWORD1c);
            runSearchTest(dto);

            // CHANGE KEYWORD SEARCH
            map.put(SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS.name(), KEYWORD2);
            dto = createSingleComponentSearchDto(groupType, map);

            log.info("Searching for persons matching keyword(s): " + KEYWORD2);
            runSearchTest(dto);

            // CHANGE KEYWORD SEARCH
            map.put(SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS.name(), KEYWORD3);
            dto = createSingleComponentSearchDto(groupType, map);

            log.info("Searching for persons matching keyword(s): " + KEYWORD3);
            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
        log.info("Quick Search Integration Test - COMPLETE");
    }

    @Test
    public void testQuickSearchPerformance() {
        log.info("Quick Search Performance Integration Test");

        assertNotNull("searchService is null", searchService);

        try {
            log.info("Searching 2 times for persons matching keyword(s): " + KEYWORD3);

            // here's the base code required for a simple search
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS.name(), KEYWORD3);

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.QUICK_SEARCH;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            log.info("Searching first time for persons matching keyword(s): " + KEYWORD3);
            runSearchTest(dto);

            log.info("Searching second time for persons matching keyword(s): " + KEYWORD3);
            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Quick Search Performance Integration Test - COMPLETE");
    }

    @Test
    public void testNameSearch() {
        log.info("Name search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.NAME_FIRST_NAME.name(), "Pilar");
            map.put(SearchComponentFieldNameCd.NAME_LAST_NAME.name(), "Gill");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.NAME;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Name search Integration Test - COMPLETE");
    }

    @Test
    public void testEducationSearch() {
        log.info("Education search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.EDUCATION_SCHOOL.name(), "Harvard");
            map.put(SearchComponentFieldNameCd.EDUCATION_DEGREE.name(), DegreeTypeCd.MBA.name());
            map.put(SearchComponentFieldNameCd.EDUCATION_MAJOR.name(), MajorTypeCd.FINANCE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.EDUCATION;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Education search Integration Test - COMPLETE");
    }

    @Test
    public void testCompanyIndustrySearch() {
        log.info("Company/Industry search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.COMPANY_INDUSTRY;

            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.COMPANY_INDUSTRY.name(), "Banking");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);

            map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.COMPANY_COMPANY.name(), "MetroBank");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);


        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Company/Industry search Integration Test - COMPLETE");
    }

    @Test
    public void testPositionAndRolesSearch() {
        log.info("Position/Roles search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.POSITION_ROLE_TYPE.name(), PositionRoleTypeCd.ROLE.name());
            map.put(SearchComponentFieldNameCd.POSITION_ROLE.name(), "Java");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.POSITION_ROLES;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);

            map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.POSITION_ROLE_TYPE.name(), PositionRoleTypeCd.ROLE.name());
            map.put(SearchComponentFieldNameCd.POSITION_ROLE.name(), "Java Developer");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Position/Roles search Integration Test - COMPLETE");
    }

    @Test
    public void testLocationSearch() {
        log.info("Location search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.LOCATION_ADDRESS.name(), "Willow");
            map.put(SearchComponentFieldNameCd.LOCATION_STATE.name(), "OH");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.LOCATION;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Location search Integration Test - COMPLETE");
    }

    @Test
    public void testSkillSearch() {
        log.info("Skill search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.SKILL_EXPERTISE.name(), "Java");
            map.put(SearchComponentFieldNameCd.SKILL_PROFICIENCY.name(), ProficiencyCd.COMPETENT.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.SKILLS_EXPERTISE;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Skill search Integration Test - COMPLETE");
    }

    @Test
    public void testLanguageSearch() {
        log.info("Language search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.LANGUAGE_TYPE.name(), LanguageCd.ENGLISH.name());
            map.put(SearchComponentFieldNameCd.LANGUAGE_VERBAL.name(), LanguageLevelCd.NATIVE.name());
            map.put(SearchComponentFieldNameCd.LANGUAGE_READWRITE.name(), LanguageLevelCd.NATIVE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.LANGUAGE;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Language search Integration Test - COMPLETE");
    }

    @Test
    public void testEmailSearch() {
        log.info("Email search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.EMAIL_USERNAME.name(), "Luis");
            map.put(SearchComponentFieldNameCd.EMAIL_DOMAIN.name(), "work.co");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.MAYBE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.EMAIL;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Email search Integration Test - COMPLETE");
    }

    @Test
    public void testProjectSearch() {
        log.info("Project search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.PROJECT_NAME.name(), "Eclipse");
            map.put(SearchComponentFieldNameCd.PROJECT_COMPANY_NAME.name(), "Metrobank");
            map.put(SearchComponentFieldNameCd.PROJECT_SKILLED_ROLE.name(), "Developer");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.PROJECTS;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Project search Integration Test - COMPLETE");
    }

    @Test
    public void testEmploymentTypeSearch() {
        log.info("Employment Type search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.EMPLOYMENT_TYPE.name(), EmploymentTypeCd.CONTRACTOR.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.TRUE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.EMPLOYMENT_TYPE;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Employment Type search Integration Test - COMPLETE");
    }

    @Test
    public void testCoverageSearch() {
        log.info("Coverage search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.COVERAGE;

            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.COVERAGE_TYPE.name(), CoverageTypeCd.CLIENT.name());
            map.put(SearchComponentFieldNameCd.COVERAGE_VALUE.name(), "Bank");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());


            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
            
            map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.COVERAGE_TYPE.name(), CoverageTypeCd.PRODUCT.name());
            map.put(SearchComponentFieldNameCd.COVERAGE_VALUE.name(), "Regulatory");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);

            map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.COVERAGE_TYPE.name(), CoverageTypeCd.ROLE.name());
            map.put(SearchComponentFieldNameCd.COVERAGE_VALUE.name(), "Developer");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);

            map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.COVERAGE_TYPE.name(), CoverageTypeCd.TERRITORY.name());
            map.put(SearchComponentFieldNameCd.COVERAGE_VALUE.name(), "Europe");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Coverage search Integration Test - COMPLETE");
    }

    @Test
    public void testPhoneSearch() {
        log.info("Phone search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.PHONE_EXTENSION.name(), "223");
            map.put(SearchComponentFieldNameCd.PHONE_COUNTRYCODE.name(), "1");
            map.put(SearchComponentFieldNameCd.PHONE_NUMBER.name(), "4154078491");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.PHONE;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Phone search Integration Test - COMPLETE");
    }

    @Test
    public void testCertificationSearch() {
        log.info("Certification search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.CERTIFICATION_TITLE.name(), "Expert");
            map.put(SearchComponentFieldNameCd.CERTIFICATION_ISSUED_BY.name(), "Gloria");
            map.put(SearchComponentFieldNameCd.CERTIFICATION_ISSUE_DATE.name(), "2009");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.CERTIFICATION;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Certification search Integration Test - COMPLETE");
    }

    @Test
    public void testAwardSearch() {
        log.info("Award search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.AWARD_NAME.name(), "Region");
            map.put(SearchComponentFieldNameCd.AWARD_ISSUE_DATE.name(), "2001");
            map.put(SearchComponentFieldNameCd.AWARD_ISSUER.name(), "Nichole");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.AWARDS;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Award search Integration Test - COMPLETE");
    }

    @Test
    public void testPublicationSearch() {
        log.info("Publication search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.PUBLICATION_TYPE.name(), PublicationTypeCd.BOOK.name());
            map.put(SearchComponentFieldNameCd.PUBLICATION_TITLE.name(), "Exposure");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.PUBLICATIONS;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Publication search Integration Test - COMPLETE");
    }

    @Test
    public void testPatentSearch() {
        log.info("Patent search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.PATENT_CODE.name(), "31447831");
            map.put(SearchComponentFieldNameCd.PATENT_NAME.name(), "Computer fraud");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.PATENTS;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Patent search Integration Test - COMPLETE");
    }

    @Test
    public void testMembershipSearch() {
        log.info("Membership search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.ORGANIZATION_NAME.name(), "Canadi");
            map.put(SearchComponentFieldNameCd.ORGANIZATION_ROLE.name(), "Board");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.ORGANIZATION;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Membership search Integration Test - COMPLETE");
    }

    @Test
    public void testAvailabilitySearch() {
        log.info("Availability search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.AVAILABILITY_DATE.name(), "20100101");
            map.put(SearchComponentFieldNameCd.AVAILABILITY_TYPE.name(), AvailabilityTypeCd.COMPLETELY.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.AVAILABILITY;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Membership search Integration Test - COMPLETE");
    }

    @Test
    public void testRateSearch() {
        log.info("Rate search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.RATE.name(), SalaryRateCd.LEVEL_2.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.RATE;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Rate search Integration Test - COMPLETE");
    }

    @Test
    public void testInterestSearch() {
        log.info("Interest search Integration Test");
        assertNotNull("searchService is null", searchService);

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SearchComponentFieldNameCd.INTEREST.name(), "Hiking");
            map.put(SearchComponentFieldNameCd.GLOBAL_EXACT_MATCH.name(), BooleanTypeCd.FALSE.name());
            map.put(SearchComponentFieldNameCd.GLOBAL_EXCLUDE.name(), BooleanTypeCd.FALSE.name());

            // Group type
            SearchComponentGroupTypeCd groupType = SearchComponentGroupTypeCd.INTEREST;

            SearchDto dto = createSingleComponentSearchDto(groupType, map);

            runSearchTest(dto);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

        log.info("Interest search Integration Test - COMPLETE");
    }

    private void printRelevancy(List<PersonLite> list) {
        for (PersonLite personLite : list) {
            log.info("Relevancy for " + personLite.getFirstName() + " " + personLite.getLastName() + " is " + personLite.getRelevancy());
        }
    }

    private SearchDto createSingleComponentSearchDto(SearchComponentGroupTypeCd groupType, Map<String, String> map) throws DomainException {
        SearchComponentDto searchComponentDto = new SearchComponentDto();
        searchComponentDto.setSearchMap(map);
        searchComponentDto.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
        searchComponentDto.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());

        List<SearchComponentDto> searchList = new ArrayList<SearchComponentDto>();
        searchList.add(searchComponentDto);

        SearchComponentGroupDto groupDto = new SearchComponentGroupDto();
        groupDto.setType(groupType.name());
        groupDto.setComponents(searchList);
        groupDto.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
        groupDto.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());

        List<SearchComponentGroupDto> groupList = new ArrayList<SearchComponentGroupDto>();
        groupList.add(groupDto);

        SearchDto dto = new SearchDto();
        dto.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
        dto.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
        dto.setGroups(groupList);
        dto.setUser(getUserId());
        dto.setCompany(getCompanyId());

        return dto;
    }

    private void runSearchTest(SearchDto dto) throws DomainException {
        Integer count = searchService.searchCount(dto);
        assertNotNull("count is null", count);
        assertTrue("Should return at least one match", count > 0);
        dto = searchService.search(dto, 0, count);
        assertNotNull("list is null", dto.getPersons());
        assertTrue("Should return at least one match", dto.getPersons().size() > 0);

        printRelevancy(dto.getPersons());
    }

    private String getUserId() throws DomainException {
        String result = null;

        if (u != null) {
            result = u.getId();
        } else {
            User u = userService.getUserByUsername("bjorn");

            if (u != null) {
                result = u.getId();
            }
        }

        return result;
    }

    private String getCompanyId() throws DomainException {
        String result = null;

        if (c != null) {
            result = c.getId();
        } else {
            c = corporateService.getCompanyByCode("MB");

            if (c != null) {
                result = c.getId();
            }
        }

        return result;
    }

    @Autowired
    private RemotingSearchService searchService;

    @Autowired
    private UserService userService;

    @Autowired
    private CorporateService corporateService;
}