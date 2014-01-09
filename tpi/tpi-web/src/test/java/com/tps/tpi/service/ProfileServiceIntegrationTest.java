package com.tps.tpi.service;

import com.tps.tpi.AbstractTest;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.dto.*;
import com.tps.tpi.model.objects.enums.*;
import org.dozer.Mapper;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:11:01 PM
 * Responsibility:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class ProfileServiceIntegrationTest extends AbstractTest {
    private final static Logger log = LoggerFactory.getLogger(ProfileServiceIntegrationTest.class);
    private final static String USERNAME = "catherinefernandez";
    private static final String USERNAME2 = "kimnavarro";
    private static final String COMPANY = "Bank Holding Company Ltd";
    private static final String PRODUCT = "Integrated Inventory";

    @Test
    public void testGetPersonDtoByUsername() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now we retrieve the person again. It should be a lot faster as it is coming from our cache");
            dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");
        } catch (DomainException e) {
            log.error("Could not retrieve PersonDto for " + USERNAME + " : " + e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetBiography() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's biographydto record");
            BiographyDto bDto = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Result biography Dto is null", bDto);
            log.info("BiographyDto retrieved successfully");

        } catch (DomainException e) {
            log.error("Could not retrieve PersonDto for " + USERNAME + " : " + e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetHistory() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's history dto record");
            HistoryDto bDto = remotingProfileService.getHistoryDto(dto.getId());
            assertNotNull("Result history Dto is null", bDto);
            log.info("HistoryDto retrieved successfully");

        } catch (DomainException e) {
            log.error("Could not retrieve PersonDto for " + USERNAME + " : " + e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetEducation() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's education dto record");
            EducationDto bDto = remotingProfileService.getEducationDto(dto.getId());
            assertNotNull("Result education Dto is null", bDto);
            log.info("EducationDto retrieved successfully");

        } catch (DomainException e) {
            log.error("Could not retrieve PersonDto for " + USERNAME + " : " + e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testPersonDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now we want to change dto record and then update it");
            assertEquals("Twitter doesn't match expected value", dto.getTwitter(), "CFernandez");
            log.info("And update the dto with the new value");
            dto = remotingProfileService.updatePersonDto(dto);
            assertNotNull("Updated dto is null", dto);
            log.info("Updated dto successfully. Verifying that aim value has changed");
            assertEquals("Twitter doesn't match expected value", dto.getTwitter(), "CFernandez");
            log.info("dto updated value successfully");

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testBiographyDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's biographydto record");
            BiographyDto bDto = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Result biography Dto is null", bDto);
            log.info("BiographyDto retrieved successfully");

            log.info("Now we want to change the summary on biography dto and update");
            bDto.setSummary("New Summary");
            bDto = remotingProfileService.updateBiographyDto(bDto);
            assertNotNull("Updated dto is null", bDto);
            log.info("Updated dto successfully. Verifying that value has changed");
            assertEquals("Summary doesn't match expected value", "New Summary", bDto.getSummary());
            log.info("dto updated value successfully");

        } catch (DomainException e) {
            log.error("Could not retrieve PersonDto for " + USERNAME + " : " + e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testAwardDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's biographydto record");
            BiographyDto bDto = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Result biography Dto is null", bDto);
            log.info("BiographyDto retrieved successfully");

            log.info("Now we want to create a new dto and associate it with the user's biography");
            AwardDto award = new AwardDto();
            award.setBiography(bDto.getId());
            award.setIssuedDate(new Date());
            award.setIssuer("Me");
            award.setAwardName("Greatest Person Ever Lived");
            award.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            award.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());

            award = remotingProfileService.saveAwardDto(award);
            assertNotNull("Saved dto is null", award);
            assertNotNull("Dto was not given a UUID", award.getId());
            assertEquals("Issuer doesn't match", "Me", award.getIssuer());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            award.setIssuer("You");
            award = remotingProfileService.updateAwardDto(award);
            assertNotNull("Saved dto is null", award);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), award.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), award.getRecordStatusType());
            assertEquals("Issuer doesn't match", "You", award.getIssuer());
            log.info("Updated dto successfully.");

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testPatentDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's biographydto record");
            BiographyDto bDto = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Result biography Dto is null", bDto);
            log.info("BiographyDto retrieved successfully");

            log.info("Now we want to create a new dto and associate it with the user's biography");
            PatentDto patent = new PatentDto();
            patent.setBiography(bDto.getId());
            patent.setCode("therefcode");
            patent.setShortName("economics of the heart");
            patent.setRef("http://www.patents.com/therefcode");
            patent.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            patent.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());

            patent = remotingProfileService.savePatentDto(patent);
            assertNotNull("Saved dto is null", patent);
            assertNotNull("Dto was not given a UUID", patent.getId());
            assertEquals("Value doesn't match", "therefcode", patent.getCode());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            patent.setCode("newrefcode");
            patent = remotingProfileService.updatePatentDto(patent);
            assertNotNull("Saved dto is null", patent);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), patent.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), patent.getRecordStatusType());
            assertEquals("Value doesn't match", "newrefcode", patent.getCode());
            log.info("Updated dto successfully.");

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testPublicationDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's biographydto record");
            BiographyDto bDto = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Result biography Dto is null", bDto);
            log.info("BiographyDto retrieved successfully");

            log.info("Now we want to create a new dto and associate it with the user's biography");
            PublicationDto publication = new PublicationDto();
            publication.setBiography(bDto.getId());
            publication.setType(PublicationTypeCd.ARTICLE.name());
            publication.setShortName("bird");
            publication.setDescription("Description here");
            publication.setIssueDate(new Date());
            publication.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            publication.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());

            publication = remotingProfileService.savePublicationDto(publication);
            assertNotNull("Saved dto is null", publication);
            assertNotNull("Dto was not given a UUID", publication.getId());
            assertEquals("Value doesn't match", "bird", publication.getShortName());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            publication.setShortName("plane");
            publication = remotingProfileService.updatePublicationDto(publication);
            assertNotNull("Saved dto is null", publication);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), publication.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), publication.getRecordStatusType());
            assertEquals("Value doesn't match", "plane", publication.getShortName());
            log.info("Updated dto successfully.");

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testBiographySkillDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's biographydto record");
            BiographyDto bDto = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Result biography Dto is null", bDto);
            log.info("BiographyDto retrieved successfully");

            Skill skill = referenceDataService.getSkillByCode("skill.java");
            SkillDto skillDto = mapper.map(skill, SkillDto.class);

            assertNotNull("Skill doesn't exist: skill.java", skill);

            log.info("Now we want to create a new dto and associate it with the user's biography");
            BiographySkillDto biographySkill = new BiographySkillDto();
            biographySkill.setBiography(bDto.getId());
            biographySkill.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            biographySkill.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            biographySkill.setProficiency(ProficiencyCd.EXPERT.name());
            biographySkill.setSkill(skillDto);

            biographySkill = remotingProfileService.saveBiographySkillDto(biographySkill);
            assertNotNull("Saved dto is null", biographySkill);
            assertNotNull("Dto was not given a UUID", biographySkill.getId());
            assertEquals("Value doesn't match", ProficiencyCd.EXPERT.name(), biographySkill.getProficiency());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            biographySkill.setProficiency(ProficiencyCd.COMPETENT.name());
            biographySkill = remotingProfileService.updateBiographySkillDto(biographySkill);
            assertNotNull("Saved dto is null", biographySkill);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), biographySkill.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), biographySkill.getRecordStatusType());
            assertEquals("Value doesn't match", ProficiencyCd.COMPETENT.name(), biographySkill.getProficiency());
            log.info("Updated dto successfully.");

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testBiographySkilledRoleDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's biographydto record");
            BiographyDto bDto = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Result biography Dto is null", bDto);
            log.info("BiographyDto retrieved successfully");

            SkilledRole skill = referenceDataService.getSkilledRoleByCode("role.project.leader");
            SkilledRoleDto skillDto = mapper.map(skill, SkilledRoleDto.class);

            assertNotNull("SkilledRole doesn't exist: role.project.leader", skill);

            log.info("Now we want to create a new dto and associate it with the user's biography");
            BiographySkilledRoleDto biographySkilledRole = new BiographySkilledRoleDto();
            biographySkilledRole.setBiography(bDto.getId());
            biographySkilledRole.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            biographySkilledRole.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            biographySkilledRole.setSkilledRole(skillDto);
            biographySkilledRole.setYears(3);

            biographySkilledRole = remotingProfileService.saveBiographySkilledRoleDto(biographySkilledRole);
            assertNotNull("Saved dto is null", biographySkilledRole);
            assertNotNull("Dto was not given a UUID", biographySkilledRole.getId());
            assertEquals("Value doesn't match", 3, biographySkilledRole.getYears(), 0);
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            biographySkilledRole.setYears(4);
            biographySkilledRole = remotingProfileService.updateBiographySkilledRoleDto(biographySkilledRole);
            assertNotNull("Saved dto is null", biographySkilledRole);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), biographySkilledRole.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), biographySkilledRole.getRecordStatusType());
            assertEquals("Value doesn't match", 4, biographySkilledRole.getYears(), 0);
            log.info("Updated dto successfully.");

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testBiographyDepartmentDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's biographydto record");
            BiographyDto bDto = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Result biography Dto is null", bDto);
            log.info("BiographyDto retrieved successfully");

            Company company = corporateService.getCompanyByShortName(COMPANY);
            List<Department> depts = corporateService.getDepartments(company.getId(), null, 0, 1);
            DepartmentDto dept = mapper.map(depts.get(0), DepartmentDto.class);
            assertNotNull("Department doesn't exist", dept);

            log.info("Now we want to create a new dto and associate it with the user's biography");
            BiographyDepartmentDto biographyDepartment = new BiographyDepartmentDto();
            biographyDepartment.setBiography(bDto.getId());
            biographyDepartment.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            biographyDepartment.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            biographyDepartment.setDepartment(dept);
            biographyDepartment.setYears(3);

            biographyDepartment = remotingProfileService.saveBiographyDepartmentDto(biographyDepartment);
            assertNotNull("Saved dto is null", biographyDepartment);
            assertNotNull("Dto was not given a UUID", biographyDepartment.getId());
            assertEquals("Value doesn't match", 3, biographyDepartment.getYears(), 0);
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            biographyDepartment.setYears(4);
            biographyDepartment = remotingProfileService.updateBiographyDepartmentDto(biographyDepartment);
            assertNotNull("Saved dto is null", biographyDepartment);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), biographyDepartment.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), biographyDepartment.getRecordStatusType());
            assertEquals("Value doesn't match", 4, biographyDepartment.getYears(), 0);
            log.info("Updated dto successfully.");

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testBiographyCityDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's biographydto record");
            BiographyDto bDto = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Result biography Dto is null", bDto);
            log.info("BiographyDto retrieved successfully");

            City city = referenceDataService.getCityByCode("city.new.york");
            CityDto cityDto = mapper.map(city, CityDto.class);
            assertNotNull("City doesn't exist", cityDto);

            log.info("Now we want to create a new dto and associate it with the user's biography");
            BiographyCityDto biographyCityDto = new BiographyCityDto();
            biographyCityDto.setBiography(bDto.getId());
            biographyCityDto.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            biographyCityDto.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            biographyCityDto.setCity(cityDto);

            biographyCityDto = remotingProfileService.saveBiographyCityDto(biographyCityDto);
            assertNotNull("Saved dto is null", biographyCityDto);
            assertNotNull("Dto was not given a UUID", biographyCityDto.getId());
            assertEquals("Value doesn't match", cityDto.getId(), biographyCityDto.getCity().getId());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            city = referenceDataService.getCityByCode("city.london");
            cityDto = mapper.map(city, CityDto.class);
            biographyCityDto.setCity(cityDto);
            biographyCityDto = remotingProfileService.updateBiographyCityDto(biographyCityDto);
            assertNotNull("Saved dto is null", biographyCityDto);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), biographyCityDto.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), biographyCityDto.getRecordStatusType());
            assertEquals("Value doesn't match", cityDto.getId(), biographyCityDto.getCity().getId());
            log.info("Updated dto successfully.");

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testPositionDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's history dto record");
            HistoryDto bDto = remotingProfileService.getHistoryDto(dto.getId());
            assertNotNull("Result history Dto is null", bDto);
            log.info("HistoryDto retrieved successfully");

            City city = referenceDataService.getCityByCode("city.new.york");
            assertNotNull("City doesn't exist", city);

            Company company = corporateService.getCompanyByShortName(COMPANY);
            List<Department> depts = corporateService.getDepartments(company.getId(), null, 0, 1);
            assertNotNull("Department doesn't exist", depts);

            SkilledRole skilledRole = referenceDataService.getSkilledRoleByCode("role.developer.java.senior");
            assertNotNull("SkilledRole doesn't exist", skilledRole);
            SkilledRoleDto skilledRoleDto = mapper.map(skilledRole, SkilledRoleDto.class);

            log.info("Now we want to create a new dto and associate it with the user's biography");
            PositionDto position = new PositionDto();
            position.setHistory(bDto.getId());
            position.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            position.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            position.setCity(city.getId());
            position.setCityName(city.getShortName());
            position.setSkilledRole(skilledRoleDto);
            position.setDepartment(depts.get(0).getId());
            position.setDepartmentName(depts.get(0).getShortName());
            position.setFrom(new Date());
            position.setTo(new Date());
            position.setType(EmploymentTypeCd.CONSULTANT.name());

            position = remotingProfileService.savePositionDto(position);
            assertNotNull("Saved dto is null", position);
            assertNotNull("Dto was not given a UUID", position.getId());
            assertEquals("Value doesn't match", city.getId(), position.getCity());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            city = referenceDataService.getCityByCode("city.london");
            position.setCity(city.getId());
            position.setCityName(city.getShortName());
            position = remotingProfileService.updatePositionDto(position);
            assertNotNull("Saved dto is null", position);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), position.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), position.getRecordStatusType());
            assertEquals("Value doesn't match", city.getId(), position.getCity());
            log.info("Updated dto successfully.");
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testProjectDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's history dto record");
            HistoryDto bDto = remotingProfileService.getHistoryDto(dto.getId());
            assertNotNull("Result history Dto is null", bDto);
            log.info("HistoryDto retrieved successfully");

            City city = referenceDataService.getCityByCode("city.new.york");
            CityDto cityDto = mapper.map(city, CityDto.class);
            assertNotNull("City doesn't exist", cityDto);

            Company company = corporateService.getCompanyByShortName(COMPANY);
            CompanyDto companyDto = mapper.map(company, CompanyDto.class);
            List<Department> depts = corporateService.getDepartments(company.getId(), null, 0, 1);
            DepartmentDto dept = mapper.map(depts.get(0), DepartmentDto.class);
            assertNotNull("Department doesn't exist", dept);

            SkilledRole skilledRole = referenceDataService.getSkilledRoleByCode("role.developer.java.senior");
            assertNotNull("SkilledRole doesn't exist", skilledRole);
            SkilledRoleDto skilledRoleDto = mapper.map(skilledRole, SkilledRoleDto.class);


            log.info("Now we want to create a new dto and associate it with the user's biography");
            ProjectDto project = new ProjectDto();
            project.setHistory(bDto.getId());
            project.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            project.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            project.setCity(cityDto);
            project.setSkilledRole(skilledRoleDto);
            project.setCompany(companyDto);
            project.setFrom(new Date());
            project.setTo(new Date());
            project.setName("Project name");

            project = remotingProfileService.saveProjectDto(project);
            assertNotNull("Saved dto is null", project);
            assertNotNull("Dto was not given a UUID", project.getId());
            assertEquals("Value doesn't match", cityDto.getId(), project.getCity().getId());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            city = referenceDataService.getCityByCode("city.london");
            cityDto = mapper.map(city, CityDto.class);
            project.setCity(cityDto);
            project = remotingProfileService.updateProjectDto(project);
            assertNotNull("Saved dto is null", project);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), project.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), project.getRecordStatusType());
            assertEquals("Value doesn't match", cityDto.getId(), project.getCity().getId());
            log.info("Updated dto successfully.");
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testCoverageDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's history dto record");
            HistoryDto bDto = remotingProfileService.getHistoryDto(dto.getId());
            assertNotNull("Result history Dto is null", bDto);
            log.info("HistoryDto retrieved successfully");

            Company company = corporateService.getCompanyByShortName(COMPANY);
            CompanyDto companyDto = mapper.map(company, CompanyDto.class);
            assertNotNull("Company doesn't exist", companyDto);

            Product product = corporateService.getProductByName(company.getId(), PRODUCT);
            ProductDto productDto = mapper.map(product, ProductDto.class);
            assertNotNull("Product doesn't exist", productDto);

            SkilledRole skilledRole = referenceDataService.getSkilledRoleByCode("role.developer.java.senior");
            assertNotNull("SkilledRole doesn't exist", skilledRole);
            SkilledRoleDto skilledRoleDto = mapper.map(skilledRole, SkilledRoleDto.class);

            Region region = referenceDataService.getRegionByCode("region.africa");
            RegionDto regionDto = mapper.map(region, RegionDto.class);
            assertNotNull("Region doesn't exist", region);

            log.info("Now we want to create a new dto and associate it with the user's biography");
            CoverageDto coverage = new CoverageDto();
            coverage.setHistory(bDto.getId());
            coverage.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            coverage.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            coverage.setProduct(productDto);
            coverage.setSkilledRole(skilledRoleDto);
            coverage.setCompany(companyDto);
            coverage.setRegion(regionDto);

            coverage = remotingProfileService.saveCoverageDto(coverage);
            assertNotNull("Saved dto is null", coverage);
            assertNotNull("Dto was not given a UUID", coverage.getId());
            assertEquals("Value doesn't match", regionDto.getId(), coverage.getRegion().getId());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            region = referenceDataService.getRegionByCode("region.americas");
            regionDto = mapper.map(region, RegionDto.class);
            assertNotNull("Region doesn't exist", region);

            coverage.setRegion(regionDto);
            coverage = remotingProfileService.updateCoverageDto(coverage);
            assertNotNull("Saved dto is null", coverage);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), coverage.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), coverage.getRecordStatusType());
            assertEquals("Value doesn't match", regionDto.getId(), coverage.getRegion().getId());
            log.info("Updated dto successfully.");
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testAffiliationDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's history dto record");
            HistoryDto bDto = remotingProfileService.getHistoryDto(dto.getId());
            assertNotNull("Result history Dto is null", bDto);
            log.info("HistoryDto retrieved successfully");

            log.info("Now we want to create a new dto and associate it with the user's biography");
            AffiliationDto affiliation = new AffiliationDto();
            affiliation.setHistory(bDto.getId());
            affiliation.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            affiliation.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            affiliation.setRole("Role 1");
            affiliation.setOrganization("Org 1");

            affiliation = remotingProfileService.saveAffiliationDto(affiliation);
            assertNotNull("Saved dto is null", affiliation);
            assertNotNull("Dto was not given a UUID", affiliation.getId());
            assertEquals("Value doesn't match", "Role 1", affiliation.getRole());
            assertEquals("Value doesn't match", "Org 1", affiliation.getOrganization());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            affiliation.setRole("Role 2");
            affiliation.setOrganization("Org 2");
            affiliation = remotingProfileService.updateAffiliationDto(affiliation);
            assertNotNull("Saved dto is null", affiliation);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), affiliation.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), affiliation.getRecordStatusType());
            assertEquals("Value doesn't match", "Role 2", affiliation.getRole());
            assertEquals("Value doesn't match", "Org 2", affiliation.getOrganization());

            log.info("Updated dto successfully.");
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testDegreeDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's history dto record");
            EducationDto bDto = remotingProfileService.getEducationDto(dto.getId());
            assertNotNull("Result history Dto is null", bDto);
            log.info("EducationDto retrieved successfully");

            School school = referenceDataService.getSchoolByShortName("NYU");
            assertNotNull("School is null", school);

            log.info("Now we want to create a new dto and associate it with the user's biography");
            DegreeDto degree = new DegreeDto();
            degree.setEducation(bDto.getId());
            degree.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            degree.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            degree.setCompletedDate(new Date());
            degree.setSchool(school.getId());
            degree.setSchoolName(school.getLongName());
            degree.setMajor(MajorTypeCd.ECONOMICS.name());
            degree.setType(DegreeTypeCd.BA.name());

            degree = remotingProfileService.saveDegreeDto(degree);
            assertNotNull("Saved dto is null", degree);
            assertNotNull("Dto was not given a UUID", degree.getId());
            assertEquals("Value doesn't match", MajorTypeCd.ECONOMICS.name(), degree.getMajor());
            assertEquals("Value doesn't match", DegreeTypeCd.BA.name(), degree.getType());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            degree.setMajor(MajorTypeCd.COMPUTER_SCIENCE.name());
            degree.setType(DegreeTypeCd.BBA.name());
            degree = remotingProfileService.updateDegreeDto(degree);
            assertNotNull("Saved dto is null", degree);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), degree.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), degree.getRecordStatusType());
            assertEquals("Value doesn't match", MajorTypeCd.COMPUTER_SCIENCE.name(), degree.getMajor());
            assertEquals("Value doesn't match", DegreeTypeCd.BBA.name(), degree.getType());

            log.info("Updated dto successfully.");
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testEducationCertificationDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's history dto record");
            EducationDto bDto = remotingProfileService.getEducationDto(dto.getId());
            assertNotNull("Result history Dto is null", bDto);
            log.info("EducationDto retrieved successfully");

            Certification cert = referenceDataService.getCertificationByShortName("Accredited Business Accountant");
            CertificationDto certDto = mapper.map(cert, CertificationDto.class);
            assertNotNull("Certification is null", certDto);

            log.info("Now we want to create a new dto and associate it with the user's biography");
            EducationCertificationDto edcert = new EducationCertificationDto();
            edcert.setEducation(bDto.getId());
            edcert.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            edcert.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            edcert.setIssueDate(new Date());
            edcert.setIssuedBy("Me");
            edcert.setTitle("Title");
            edcert.setCertification(certDto);

            edcert = remotingProfileService.saveEducationCertificationDto(edcert);
            assertNotNull("Saved dto is null", edcert);
            assertNotNull("Dto was not given a UUID", edcert.getId());
            assertEquals("Value doesn't match", "Me", edcert.getIssuedBy());
            assertEquals("Value doesn't match", "Title", edcert.getTitle());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            edcert.setIssuedBy("You");
            edcert.setTitle("Eltit");
            edcert = remotingProfileService.updateEducationCertificationDto(edcert);
            assertNotNull("Saved dto is null", edcert);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), edcert.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), edcert.getRecordStatusType());
            assertEquals("Value doesn't match", "You", edcert.getIssuedBy());
            assertEquals("Value doesn't match", "Eltit", edcert.getTitle());

            log.info("Updated dto successfully.");
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testLanguageDto() {
        assertNotNull("remotingProfileService is null", remotingProfileService);

        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Now retrieving person's history dto record");
            EducationDto bDto = remotingProfileService.getEducationDto(dto.getId());
            assertNotNull("Result history Dto is null", bDto);
            log.info("EducationDto retrieved successfully");

            Certification cert = referenceDataService.getCertificationByShortName("Accredited Business Accountant");
            CertificationDto certDto = mapper.map(cert, CertificationDto.class);
            assertNotNull("Certification is null", certDto);

            log.info("Now we want to create a new dto and associate it with the user's biography");
            LanguageDto lang = new LanguageDto();
            lang.setEducation(bDto.getId());
            lang.setRecordCreatorType(RecordCreatorTypeCd.PERSON.name());
            lang.setRecordStatusType(RecordStatusTypeCd.ACTIVE.name());
            lang.setReadwrite(LanguageLevelCd.FLUENT.name());
            lang.setVerbal(LanguageLevelCd.NATIVE.name());
            lang.setType(LanguageCd.AKHA.name());

            lang = remotingProfileService.saveLanguageDto(lang);
            assertNotNull("Saved dto is null", lang);
            assertNotNull("Dto was not given a UUID", lang.getId());
            assertEquals("Value doesn't match", LanguageLevelCd.FLUENT.name(), lang.getReadwrite());
            assertEquals("Value doesn't match", LanguageLevelCd.NATIVE.name(), lang.getVerbal());
            assertEquals("Value doesn't match", LanguageCd.AKHA.name(), lang.getType());
            log.info("Saved dto successfully.");

            log.info("Now we want to update that same dto by changing some values");
            lang.setReadwrite(LanguageLevelCd.BASIC.name());
            lang.setVerbal(LanguageLevelCd.BASIC.name());
            lang.setType(LanguageCd.ALURIAN.name());
            lang = remotingProfileService.updateLanguageDto(lang);
            assertNotNull("Saved dto is null", lang);
            assertEquals("Record creator type doesn't match", RecordCreatorTypeCd.PERSON.name(), lang.getRecordCreatorType());
            assertEquals("Record status doesn't match", RecordStatusTypeCd.ACTIVE.name(), lang.getRecordStatusType());
            assertEquals("Value doesn't match", LanguageLevelCd.BASIC.name(), lang.getReadwrite());
            assertEquals("Value doesn't match", LanguageLevelCd.BASIC.name(), lang.getVerbal());
            assertEquals("Value doesn't match", LanguageCd.ALURIAN.name(), lang.getType());

            log.info("Updated dto successfully.");
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Test
    public void testEndorsements() {
        try {
            log.info("Grabbing test person from dummy data called " + USERNAME + "...");
            PersonDto dto = remotingProfileService.getPersonDtoByUsername(USERNAME);
            assertNotNull("Result dto is null", dto);
            log.info("Test person retrieved successfully");

            log.info("Grabbing endorser person from dummy data called " + USERNAME2 + "...");
            PersonDto dto2 = remotingProfileService.getPersonDtoByUsername(USERNAME2);
            assertNotNull("Result dto2 is null", dto2);
            log.info("Test endorser person retrieved successfully");

            log.info("Now we want Allison to endorse everything she can for Jacob");

            HistoryDto history = remotingProfileService.getHistoryDto(dto.getId());
            assertNotNull("History entity is null for person", history);

            List<CoverageDto> coverages = history.getCoverages();
            assertNotNull("History entity has no coverages", coverages);
            CoverageDto coverage = coverages.get(0);
            CoverageEndorsementDto cDto = remotingProfileService.endorseCoverageDto(coverage.getId(), dto2.getId(), 5);
            assertNotNull("coverage endorsement is null", cDto);

            List<ProjectDto> projects = history.getProjects();
            assertNotNull("History entity has no projects", projects);
            ProjectDto project = projects.get(0);
            ProjectEndorsementDto pDto = remotingProfileService.endorseProjectDto(project.getId(), dto2.getId(), 5);
            assertNotNull("project endorsement is null", pDto);

            List<PositionDto> positions = history.getPositions();
            assertNotNull("History entity has no position", positions);
            PositionDto position = positions.get(0);
            PositionEndorsementDto ppDto = remotingProfileService.endorsePositionDto(position.getId(), dto2.getId(), 5);
            assertNotNull("position endorsement is null", ppDto);

            BiographyDto biography = remotingProfileService.getBiographyDto(dto.getId());
            assertNotNull("Biography entity is null for person", history);
            List<BiographySkillDto> bs = biography.getBiographySkills();
            assertNotNull("Biography entity has no skills", bs);
            BiographySkillDto bsdto = bs.get(0);
            BiographySkillEndorsementDto bseDto = remotingProfileService.endorseBiographySkillDto(bsdto.getId(), dto2.getId(), 5);
            assertNotNull("biography skill endorsement is null", ppDto);

            EducationDto education = remotingProfileService.getEducationDto(dto.getId());
            assertNotNull("Education entity is null for person", education);
            List<LanguageDto> languages = education.getLanguages();
            assertNotNull("Education entity has no languages", languages);
            LanguageDto language = languages.get(0);
            LanguageEndorsementDto ledto = remotingProfileService.endorseLanguageDto(language.getId(), dto2.getId(), 5);
            assertNotNull("language endorsement is null", ledto);

            log.info("All endorsements inserted correctly.");
            log.info("Now we want to update an endorsement. We do this by submitting an identical one. Let's do it with language.");
            ledto = remotingProfileService.endorseLanguageDto(language.getId(), dto2.getId(), 1);
            assertNotNull("language endorsement is null", ledto);
            assertEquals("endorsement level doesn't match", 1, ledto.getEndorsement().getStars(), 0);
            log.info("Updated endorsement correctly");

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RemotingProfileService remotingProfileService;

    @Autowired
    private ReferenceDataService referenceDataService;

    @Autowired
    CorporateService corporateService;

    @Autowired
    private Mapper mapper;
}
