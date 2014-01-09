package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.enums.*;
import com.tps.tpi.model.exception.ModelException;
import com.tps.tpi.service.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Sep 28, 2009
 * Time: 4:24:27 PM
 * Responsibility:
 */
@Component("dummyPersonDataCreator")
public class DummyPersonDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(DummyCompanyDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final ProfileService profileService;
    private final Resource file = new ClassPathResource("dummydata/persons.xml");
    private final UserService userService;
    private final SearchService searchService;
    private final ReferenceDataService referenceDataService;
    private final CorporateService corporateService;
    private final SourcingService sourcingService;
    private final DateFormat df;

    @Autowired
    public DummyPersonDataCreator(ProfileService profileService, UserService userService,
                                  ReferenceDataService referenceDataService, CorporateService corporateService,
                                  SearchService searchService, SourcingService sourcingService) {
        this.profileService = profileService;
        this.userService = userService;
        this.referenceDataService = referenceDataService;
        this.corporateService = corporateService;
        this.searchService = searchService;
        this.sourcingService = sourcingService;
        this.df = new SimpleDateFormat("MM/dd/yyyy");
    }

    public void create() throws DataCreatorException {

        try {
            if (file.exists()) {
                processCreation();
            } else {
                throw new DataCreatorException("XML file could not be found");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.debug("Populated " + populated + " companies in db");
        log.debug("Omitted " + omitted + " companies from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processPersons();

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    private void processPersons() throws IOException, DataCreatorException {

        try {

            // purge all lucene indexes
            // before we process entities here we want to make sure the lucene index is clean of old data
            searchService.purgeSearchIndex(Person.class);
            searchService.purgeSearchIndex(Biography.class);
            searchService.purgeSearchIndex(BiographySkill.class);
            searchService.purgeSearchIndex(BiographyCity.class);
            searchService.purgeSearchIndex(BiographyDepartment.class);
            searchService.purgeSearchIndex(BiographySkilledRole.class);
            searchService.purgeSearchIndex(Education.class);
            searchService.purgeSearchIndex(Language.class);
            searchService.purgeSearchIndex(City.class);
            searchService.purgeSearchIndex(SkilledRole.class);
            searchService.purgeSearchIndex(Skill.class);
            searchService.purgeSearchIndex(Department.class);
//            searchService.purgeSearchIndex(Affiliation.class);
//            searchService.purgeSearchIndex(Coverage.class);
//            searchService.purgeSearchIndex(Project.class);
//            searchService.purgeSearchIndex(Position.class);
//            searchService.purgeSearchIndex(History.class);
//            searchService.purgeSearchIndex(Publication.class);
//            searchService.purgeSearchIndex(Patent.class);

            searchService.purgeSearchIndex(EducationCertification.class);
            searchService.purgeSearchIndex(Degree.class);

            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> persons = document.selectNodes("//persons/person");

            for (Element e : persons) {
                processPerson(e);
            }

            searchService.optimize();
        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            throw new DataCreatorException(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DataCreatorException(e.getMessage(), e);
        }

    }

    private Person processPerson(Element e) throws DomainException, DataCreatorException {
        Person p = null;

        try {


            String aim = e.element("aim").getStringValue();
            String availabilityS = e.element("availability").getStringValue();
            String availabilityTypeS = e.element("availability").attributeValue("type");
            String availableEndorsementsS = e.element("availableEndorsements").getStringValue();
            Integer availableEndorsements = Integer.parseInt(availableEndorsementsS);
            Date availability = df.parse(availabilityS);
            AvailabilityTypeCd availabilityType = AvailabilityTypeCd.valueOf(availabilityTypeS);

            String companyName = e.element("currentDepartment").attributeValue("companyCode");
            String department = e.element("currentDepartment").attributeValue("name");
            String divisionName = e.element("currentDepartment").attributeValue("divisionName");
            String facebook = e.element("facebook").getStringValue();
            String firstName = e.element("firstName").getStringValue();
            String lastName = e.element("lastName").getStringValue();
            String middleName = e.element("middleName").getStringValue();
            String gtalk = e.element("gtalk").getStringValue();
            String homeEmail = e.element("homeEmail").getStringValue();
            String maritalStatusS = e.element("maritalStatus").attributeValue("code");
            MaritalStatusCd maritalStatus = MaritalStatusCd.valueOf(maritalStatusS);
            String msn = e.element("msn").getStringValue();
            String preferredEmail = e.element("preferredEmail").getStringValue();
            String preferredSalaryS = e.element("preferredSalary").getStringValue();
            Float preferredSalary = Float.parseFloat(preferredSalaryS);
            String currentSalaryS = e.element("currentSalary").getStringValue();
            Float currentSalary = Float.parseFloat(currentSalaryS);
            String profileImageUrl = e.element("profileImageUrl").getStringValue();
            String profileImageUrlSmall = e.element("profileImageUrlSmall").getStringValue();
            String profileImageUrlLarge = e.element("profileImageUrlLarge").getStringValue();
            String skype = e.element("skype").getStringValue();
            String statusS = e.element("status").attributeValue("code");
            PersonStatusCd pStatus = PersonStatusCd.valueOf(statusS);
            String timezone = e.element("timezone").getStringValue();
            String twitter = e.element("twitter").getStringValue();
            String yahoo = e.element("yahoo").getStringValue();
            String workEmail = e.element("workEmail").getStringValue();
            String workPhone = e.element("workPhone").getStringValue();
            String workPhoneExtension = e.element("workPhoneExtension").getStringValue();
            String workPhoneCountryCode = e.element("workPhoneCountryCode").getStringValue();
            String homePhone = e.element("homePhone").getStringValue();
            String homePhoneExtension = e.element("homePhoneExtension").getStringValue();
            String homePhoneCountryCode = e.element("homePhoneCountryCode").getStringValue();
            String mobilePhone = e.element("mobilePhone").getStringValue();
            String mobilePhoneExtension = e.element("mobilePhoneExtension").getStringValue();
            String mobilePhoneCountryCode = e.element("mobilePhoneCountryCode").getStringValue();
            String fax = e.element("fax").getStringValue();
            String faxCountryCode = e.element("faxCountryCode").getStringValue();
            String preferredPhone = e.element("preferredPhone").getStringValue();
            String preferredPhoneExtension = e.element("preferredPhoneExtension").getStringValue();
            String preferredPhoneCountryCode = e.element("preferredPhoneCountryCode").getStringValue();

            SalutationCd salutation = null;
            if (e.element("salutation") != null) {
                String salutationS = e.element("salutation").attributeValue("code");
                salutation = SalutationCd.valueOf(salutationS);
            }

            SexCd sex = null;
            if (e.element("sex") != null) {
                String sexS = e.element("sex").attributeValue("code");
                sex = SexCd.valueOf(sexS);
            }

            String race = null;
            if (e.element("race") != null) {
                race = e.element("race").getStringValue();
            }

            Date dob = null;
            if (e.element("dob") != null) {
                String dobS = e.element("dob").getStringValue();
                dob = df.parse(dobS);
            }

            String preferredLocationS = e.element("currentLocation").attributeValue("code");
            String currentEmploymentS = e.element("currentEmploymentType").attributeValue("code");
            EmploymentTypeCd currentEmployment = EmploymentTypeCd.valueOf(currentEmploymentS);
            City preferredLocation = referenceDataService.getCityByCode(preferredLocationS);
            Element addressesE = e.element("addresses");
            Element interestsE = e.element("interests");
            User u = createRandomUser(firstName + lastName);

            p = profileService.getPersonByUsername(u.getUsername());

            if (p == null) {
                p = new Person();
                // this sets the recordcreator and recordstatus
                processCommonProperties(p, e);

                p.setAim(aim);
                p.setAvailability(availability);
                p.setAvailabilityType(availabilityType);
                p.setAvailableEndorsements(availableEndorsements);
                p.setFacebook(facebook);
                p.setFax(fax);
                p.setFaxCountryCode(faxCountryCode);
                p.setFirstName(firstName);
                p.setGtalk(gtalk);
                p.setHomeEmail(homeEmail);
                p.setHomePhone(homePhone);
                p.setHomePhoneCountryCode(homePhoneCountryCode);
                p.setHomePhoneExtension(homePhoneExtension);
                p.setMobilePhone(mobilePhone);
                p.setMobilePhoneCountryCode(mobilePhoneCountryCode);
                p.setMobilePhoneExtension(mobilePhoneExtension);
                p.setLastName(lastName);
                p.setMaritalStatus(maritalStatus);
                p.setMiddleName(middleName);
                p.setMsn(msn);
                p.setPreferredEmail(preferredEmail);
                p.setCurrentLocation(preferredLocation);
                p.setPreferredPhone(preferredPhone);
                p.setPreferredPhoneExtension(preferredPhoneExtension);
                p.setPreferredPhoneCountryCode(preferredPhoneCountryCode);
                p.setPreferredSalary(preferredSalary);
                p.setCurrentSalary(currentSalary);
                p.setProfileImageUrl(profileImageUrl);
                p.setProfileImageUrlSmall(profileImageUrlSmall);
                p.setProfileImageUrlLarge(profileImageUrlLarge);
                p.setSkype(skype);
                p.setStatus(pStatus);
                p.setTimezone(timezone);
                p.setTwitter(twitter);
                p.setUser(u);
                p.setYahoo(yahoo);
                p.setWorkEmail(workEmail);
                p.setWorkPhone(workPhone);
                p.setWorkPhoneExtension(workPhoneExtension);
                p.setWorkPhoneCountryCode(workPhoneCountryCode);
                p.setDob(dob);
                p.setRace(race);
                p.setSex(sex);
                p.setSalutation(salutation);
                p.setCurrentEmploymentType(currentEmployment);

                Company c = corporateService.getCompanyByCode(companyName);
                p.setCurrentDepartment(corporateService.getDepartment(department, c.getId(), divisionName));

                p = profileService.savePerson(p);

                populated++;
                log.debug("Saved new person: " + p);

                List<PersonAddress> pas = processesAddresses(p, addressesE);
                List<Interest> interests = processInterests(p, interestsE);

                // this is for lucene indexing purposes
                p.setPersonAddresses(pas);
                p.setInterests(interests);

                // set the preferred address to be the first address from our list
                if (p.getPersonAddresses() != null) {
                    p.setPreferredPersonAddress(p.getPersonAddresses().get(0));
                }

                // time to go for biography, education and history
                History history = processHistory(p, e.element("history"));
                Biography bio = processBiography(p, e.element("biography"));
                Education education = processEducation(p, e.element("education"));

                p.addBiography(bio);
                p.addEducation(education);
                p.addHistory(history);
                
                // now we set the first role as the current role
                if (bio.getBiographySkilledRoles() != null) {
                    p.setCurrentBiographySkilledRole(bio.getBiographySkilledRoles().get(0));
                }

                if (bio.getBiographyCompanyTitles() != null) {
                    p.setCurrentBiographyCompanyTitle(bio.getBiographyCompanyTitles().get(0));
                }

                p = profileService.updatePerson(p);
            } else {
                log.debug("Person already exists: " + p);
                omitted++;
            }
        } catch (ParseException e1) {
            throw new DataCreatorException(e1.getMessage(), e1);
        } catch (ModelException e1) {
            throw new DataCreatorException(e1.getMessage(), e1);
        }

        return p;
    }

    private List<Interest> processInterests(Person p, Element element) throws DomainException {
        List<Interest> result = null;

        if (element != null) {

            List<Element> interestsE = element.elements("interest");
            for (Element interestE : interestsE) {

                if (result == null) {
                    result = new ArrayList<Interest>();
                }

                Interest interest = new Interest();
                // this sets the recordcreator and recordstatus
                processCommonProperties(interest, interestE);
                interest.setPerson(p);

                interest.setShortName(interestE.getStringValue());

                interest = profileService.saveInterest(interest);

                result.add(interest);
                log.debug("Saved new interest: " + interest);
            }
        }

        return result;
    }

    private List<PersonAddress> processesAddresses(Person p, Element element) throws DomainException {
        List<PersonAddress> result = null;

        if (element != null) {

            List<Element> addressesE = element.elements("address");

            for (Element addieE : addressesE) {
                if (result == null) {
                    result = new ArrayList<PersonAddress>();
                }

                PersonAddress pAddress = new PersonAddress();
                // this sets the recordcreator and recordstatus
                processCommonProperties(pAddress, element);

                Address addie = new Address();

                String add1 = addieE.element("address1").getStringValue();
                String add2 = addieE.element("address2").getStringValue();
                String state = addieE.element("state").getStringValue();
                String zip = addieE.element("zip").getStringValue();
                String typeS = addieE.element("type").attributeValue("code");
                AddressTypeCd type = AddressTypeCd.valueOf(typeS);
                String cityS = addieE.element("city").attributeValue("code");

                City city = referenceDataService.getCityByCode(cityS);

                addie.setAddress1(add1);
                addie.setAddress2(add2);
                addie.setCity(city);
                addie.setState(state);
                addie.setType(type);
                addie.setZip(zip);

                addie = referenceDataService.saveAddress(addie);

                pAddress.setAddress(addie);
                pAddress.setPerson(p);

                pAddress = profileService.savePersonAddress(pAddress);

                result.add(pAddress);
                log.debug("Saved new person address: " + pAddress);
            }
        }

        return result;
    }

    private History processHistory(Person p, Element element) throws DomainException, DataCreatorException, ModelException {

        History h = new History();

        // this sets the recordcreator and recordstatus
        processCommonProperties(h, element);

        h.setPerson(p);

        h = profileService.saveHistory(h);

        log.debug("Saved new history: " + h);

        Element positionsE = element.element("positions");
        Element projectsE = element.element("projects");
        Element coveragesE = element.element("coverages");
        Element affiliationsE = element.element("affiliations");

        if (positionsE != null) {
            List<Element> positions = positionsE.elements("position");

            for (Element position : positions) {
                h.addPosition(processPosition(h, position));
            }
        }

        if (projectsE != null) {
            List<Element> projects = projectsE.elements("project");

            for (Element project : projects) {
                h.addProject(processProject(h, project));
            }
        }

        if (coveragesE != null) {
            List<Element> coverages = coveragesE.elements("coverage");

            for (Element coverage : coverages) {
                h.addCoverage(processCoverage(h, coverage));
            }
        }

        if (affiliationsE != null) {
            List<Element> affiliations = affiliationsE.elements("affiliation");

            for (Element affiliation : affiliations) {
                h.addAffiliation(processAffiliation(h, affiliation));
            }
        }


        return h;
    }

    private Affiliation processAffiliation(History h, Element affiliation) throws DomainException {
        Affiliation af = null;

        if (affiliation != null) {

            af = new Affiliation();

            // this sets the recordcreator and recordstatus
            processCommonProperties(af, affiliation);

            String org = affiliation.element("organization").getStringValue();
            String role = affiliation.element("role").getStringValue();

            af.setOrganization(org);
            af.setRole(role);
            af.setHistory(h);

            af = profileService.saveAffiliation(af);
            log.debug("Saved new affiliation: " + af);
        }

        return af;
    }

    private Coverage processCoverage(History h, Element coverage) throws DomainException {
        Coverage c = null;

        if (coverage != null) {

            c = new Coverage();

            // this sets the recordcreator and recordstatus
            processCommonProperties(c, coverage);

            c.setHistory(h);
            String companyNameS = coverage.element("company").attributeValue("name");
            String skilledRoleS = coverage.element("skilledrole").attributeValue("code");
            String regionS = coverage.element("region").attributeValue("code");
            String productName = coverage.element("product").attributeValue("name");
            String currentS = coverage.element("current").getStringValue();

            Company company = corporateService.getCompanyByShortName(companyNameS);
            SkilledRole skilledRole = referenceDataService.getSkilledRoleByCode(skilledRoleS);
            Region region = referenceDataService.getRegionByCode(regionS);
            Product product = corporateService.getProductByName(company.getId(), productName);
            Boolean current = Boolean.parseBoolean(currentS);

            if (skilledRole == null) {
                throw new DomainException("Could not find skilledrole: " + skilledRoleS);
            }

            if (company == null) {
                throw new DomainException("Could not find company: " + companyNameS);
            }

            if (region == null) {
                throw new DomainException("Could not find region: " + regionS);
            }

            if (product == null) {
                throw new DomainException("Could not find product: " + productName);
            }

            c.setCompany(company);
            c.setSkilledRole(skilledRole);
            c.setRegion(region);
            c.setProduct(product);
            c.setCurrent(current);

            c = profileService.saveCoverage(c);

            log.debug("Saved new coverage: " + c);
        }

        return c;
    }

    private Project processProject(History h, Element project) throws DomainException, DataCreatorException {
        Project p = null;

        try {
            if (project != null) {

                p = new Project();

                // this sets the recordcreator and recordstatus
                processCommonProperties(p, project);

                String name = project.element("name").getStringValue();
                String companyNameS = project.element("company").attributeValue("name");
                String skilledRoleS = project.element("skilledrole").attributeValue("code");
                String cityS = project.element("city").attributeValue("code");
                String toS = project.element("to").getStringValue();
                String fromS = project.element("from").getStringValue();

                Company company = corporateService.getCompanyByShortName(companyNameS);
                SkilledRole skilledRole = referenceDataService.getSkilledRoleByCode(skilledRoleS);
                City city = referenceDataService.getCityByCode(cityS);
                Date to = df.parse(toS);
                Date from = df.parse(fromS);

                if (skilledRole == null) {
                    throw new DomainException("Could not find skilledrole: " + skilledRoleS);
                }

                if (company == null) {
                    throw new DomainException("Could not find company: " + companyNameS);
                }

                if (city == null) {
                    throw new DomainException("Could not find city: " + cityS);
                }

                p.setHistory(h);
                p.setName(name);
                p.setCompany(company);
                p.setSkilledRole(skilledRole);
                p.setCity(city);
                p.setTo(to);
                p.setFrom(from);

                p = profileService.saveProject(p);
                log.debug("Saved new project: " + p);
            }

        } catch (ParseException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return p;

    }

    private Position processPosition(History h, Element position) throws DomainException, DataCreatorException {
        Position p = null;

        try {
            if (position != null) {

                p = new Position();

                // this sets the recordcreator and recordstatus
                processCommonProperties(p, position);

                String companyNameS = position.element("department").attributeValue("companyCode");
                String deptS = position.element("department").attributeValue("name");
                String divisionS = position.element("department").attributeValue("divisionName");

                String skilledRoleS = position.element("skilledrole").attributeValue("code");
                String employmentTypeS = position.element("employmenttype").attributeValue("code");
                String toS = position.element("to").getStringValue();
                String fromS = position.element("from").getStringValue();
                String cityS = position.element("city").attributeValue("code");

                Company company = corporateService.getCompanyByCode(companyNameS);
                if (company != null) {
                    SkilledRole skilledRole = referenceDataService.getSkilledRoleByCode(skilledRoleS);
                    Department dept = corporateService.getDepartment(deptS, company.getId(), divisionS);
                    City city = referenceDataService.getCityByCode(cityS);
                    Date to = df.parse(toS);
                    Date from = df.parse(fromS);

                    if (skilledRole == null) {
                        throw new DomainException("Could not find skilledrole: " + skilledRoleS);
                    }

                    if (dept == null) {
                        throw new DomainException("Could not find dept.: " + deptS);
                    }

                    if (city == null) {
                        throw new DomainException("Could not find city: " + cityS);
                    }

                    EmploymentTypeCd type = null;

                    try {
                        type = EmploymentTypeCd.valueOf(employmentTypeS);
                    } catch (Exception e) {
                        throw new DomainException("No such employment type enum: " + employmentTypeS);
                    }

                    p.setHistory(h);
                    p.setCity(city);
                    p.setDepartment(dept);
                    p.setSkilledRole(skilledRole);
                    p.setType(type);
                    p.setFrom(from);
                    p.setTo(to);

                    p = profileService.savePosition(p);

                    log.debug("Saved new position: " + p);
                } else {
                    log.error("Company is null");
                }
            }
        } catch (ParseException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return p;
    }

    private Education processEducation(Person p, Element element) throws DomainException, DataCreatorException, ModelException {

        Education e = new Education();

        // this sets the recordcreator and recordstatus
        processCommonProperties(e, element);

        e.setPerson(p);

        e = profileService.saveEducation(e);

        Element degreesE = element.element("degrees");
        Element certificationsE = element.element("certificates");
        Element languagesE = element.element("languages");

        if (degreesE != null) {
            List<Element> degrees = degreesE.elements("degree");

            for (Element degree : degrees) {
                e.addDegree(processDegree(e, degree));
            }
        }

        if (certificationsE != null) {
            List<Element> certs = certificationsE.elements("certification");

            for (Element cert : certs) {
                e.addCertification(processCertification(e, cert));
            }
        }

        if (languagesE != null) {
            List<Element> languages = languagesE.elements("language");

            for (Element language : languages) {
                e.addLanguage(processLanguage(e, language));
            }
        }

        e = profileService.updateEducation(e);

        return e;
    }

    private Language processLanguage(Education e, Element element) throws DomainException {
        Language l = null;

        if (element != null) {

            l = new Language();

            // this sets the recordcreator and recordstatus
            processCommonProperties(l, element);

            String language = element.attributeValue("code");
            String verbal = element.element("verbal").attributeValue("code");
            String readwrite = element.element("readwrite").attributeValue("code");

            LanguageCd lang = LanguageCd.valueOf(language);
            LanguageLevelCd v = LanguageLevelCd.valueOf(verbal);
            LanguageLevelCd rw = LanguageLevelCd.valueOf(readwrite);

            l.setEducation(e);
            l.setReadwrite(rw);
            l.setVerbal(v);
            l.setType(lang);

            l = profileService.saveLanguage(l);

            log.debug("Saved new language: " + l);
        }

        return l;
    }

    private EducationCertification processCertification(Education e, Element element) throws DataCreatorException, DomainException {
        EducationCertification c = null;

        try {
            if (element != null) {

                c = new EducationCertification();

                // this sets the recordcreator and recordstatus
                processCommonProperties(c, element);

                String certificationName = element.attributeValue("name");
                String title = element.element("title").getStringValue();
                String issuedBy = element.element("issuedby").getStringValue();
                String issuedDateS = element.element("issueddate").getStringValue();

                Certification cert = referenceDataService.getCertificationByShortName(certificationName);

                if (cert != null) {
                    Date issuedDate = df.parse(issuedDateS);

                    c.setEducation(e);
                    c.setCertification(cert);
                    c.setTitle(title);
                    c.setIssuedBy(issuedBy);
                    c.setIssueDate(issuedDate);

                    c = profileService.saveEducationCertification(c);
                    log.debug("Saved new certification: " + c);
                } else {
                    throw new DomainException("Cannot find certificate with shortName: " + certificationName);
                }
            }
        } catch (ParseException ex) {
            throw new DataCreatorException(ex.getMessage(), ex);
        }

        return c;
    }

    private Degree processDegree(Education e, Element element) throws DataCreatorException, DomainException {
        Degree d = null;

        try {
            if (element != null) {

                d = new Degree();

                // this sets the recordcreator and recordstatus
                processCommonProperties(d, element);

                String schoolS = element.element("school").attributeValue("name");
                String typeS = element.element("type").attributeValue("code");
                String majorS = element.element("major").attributeValue("code");
                String completedDateS = element.element("completeddate").getStringValue();

                School school = referenceDataService.getSchoolByShortName(schoolS);
                DegreeTypeCd type = DegreeTypeCd.valueOf(typeS);
                MajorTypeCd major = MajorTypeCd.valueOf(majorS);
                Date completedDate = df.parse(completedDateS);

                d.setEducation(e);
                d.setSchool(school);
                d.setType(type);
                d.setMajor(major);
                d.setCompletedDate(completedDate);

                d = profileService.saveDegree(d);
                log.debug("Saved new degree: " + d);
            }
        } catch (ParseException ex) {
            throw new DataCreatorException(ex.getMessage(), ex);
        }

        return d;
    }

    /**
     * Parse Biography xml
     *
     * @param p
     * @param element
     * @return
     * @throws DomainException
     */
    private Biography processBiography(Person p, Element element) throws DomainException, DataCreatorException, ModelException {

        Biography bio = new Biography();

        // this sets the recordcreator and recordstatus
        processCommonProperties(bio, element);

        String summary = normalizeString(element.element("summary").getStringValue());
        bio.setPerson(p);
        bio.setSummary(summary);
        bio = profileService.saveBiography(bio);

        Element awardsE = element.element("awards");

        if (awardsE != null) {
            List<Element> awards = awardsE.elements("award");

            for (Element awardE : awards) {
                bio.addAward(processAward(bio, awardE));
            }
        }

        Element titlesE = element.element("titles");

        if (titlesE != null) {
            List<Element> titles = titlesE.elements("title");

            for (Element titleE : titles) {
                bio.addBiographyCompanyTitle(processBiographyCompanyTitle(bio, titleE));
            }
        }

        Element rolesE = element.element("roles");

        if (rolesE != null) {
            List<Element> roles = rolesE.elements("role");

            for (Element roleE : roles) {
                bio.addBiographySkilledRole(processBiographySkilledRole(bio, roleE));
            }
        }

        Element departmentsE = element.element("departments");

        if (departmentsE != null) {
            List<Element> departments = departmentsE.elements("department");

            bio.setBiographyDepartments(processBiographyDepartments(bio, departments));

        }

        Element citiesE = element.element("cities");

        if (citiesE != null) {
            List<Element> cities = citiesE.elements("city");

            for (Element cityE : cities) {
                bio.addBiographyCity(processCity(bio, cityE));
            }
        }

        Element skillsE = element.element("skills");

        if (skillsE != null) {
            List<Element> skills = skillsE.elements("skill");

            for (Element skillE : skills) {
                bio.addBiographySkill(processBiographySkill(bio, skillE));
            }
        }

        Element patentsE = element.element("patents");

        if (patentsE != null) {
            List<Element> patents = patentsE.elements("patent");

            for (Element patentE : patents) {
                bio.addPatent(processPatent(bio, patentE));
            }
        }

        Element publicationsE = element.element("publications");

        if (publicationsE != null) {
            List<Element> publications = publicationsE.elements("publication");

            for (Element publicationE : publications) {
                bio.addPublication(processPublication(bio, publicationE));
            }
        }

        // update bio again to account for all the lists
        bio = profileService.updateBiography(bio);

        return bio;
    }

    /**
     * removes all tabs, end lines etc
     * @param s
     * @return
     */
    private String normalizeString(String s) {
        return s.replaceAll("\t|\n", "").trim();
    }

    private BiographyCompanyTitle processBiographyCompanyTitle(Biography bio, Element element) throws DomainException {
        BiographyCompanyTitle bsr = null;

        if (element != null) {

            bsr = new BiographyCompanyTitle();

            // this sets the recordcreator and recordstatus
            processCommonProperties(bsr, element);

            String titleS = element.attributeValue("code");
            String startDateS = element.element("startDate").getStringValue();
            String endDateS = element.element("endDate").getStringValue();
            String companyCode = element.element("company").attributeValue("code");
            Company company = corporateService.getCompanyByCode(companyCode);

            if (company == null) {
                throw new DomainException("Could not find Company for: " + companyCode);
            }

            CompanyTitle sr = corporateService.getCompanyTitleByCode(company.getId(), titleS);

            if (sr == null) {
                throw new DomainException("Could not find CompanyTitle for: " + titleS);
            }

            bsr.setBiography(bio);
            bsr.setCompanyTitle(sr);

            try {
                bsr.setStartDate(df.parse(startDateS));
                bsr.setEndDate(df.parse(endDateS));
            } catch (ParseException e) {
                throw new DomainException("Cannot parse dates: " + startDateS + ", " + endDateS);
            }

            bsr = profileService.saveBiographyCompanyTitle(bsr);
            log.debug("Saved new BiographyCompanyTitle: " + bsr);
        }

        return bsr;
    }

    private Publication processPublication(Biography bio, Element element) throws DataCreatorException, DomainException {
        Publication p = null;

        try {
            if (element != null) {

                p = new Publication();

                // this sets the recordcreator and recordstatus
                processCommonProperties(p, element);

                String typeS = element.element("type").attributeValue("code");
                String description = element.element("description").getStringValue();
                String shortName = element.element("shortname").getStringValue();
                String issuedDateS = element.element("issueDate").getStringValue();

                PublicationTypeCd type = PublicationTypeCd.valueOf(typeS);
                Date issueDate = df.parse(issuedDateS);

                p.setBiography(bio);
                p.setType(type);
                p.setDescription(description);
                p.setIssueDate(issueDate);
                p.setShortName(shortName);

                p = profileService.savePublication(p);
                log.debug("Saved new publication: " + p);
            }
        } catch (ParseException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return p;
    }

    private Patent processPatent(Biography bio, Element element) throws DomainException {
        Patent p = null;

        if (element != null) {

            p = new Patent();

            // this sets the recordcreator and recordstatus
            processCommonProperties(p, element);

            String code = element.element("code").getStringValue();
            String ref = element.element("ref").getStringValue();
            String shortName = element.element("shortname").getStringValue();

            p.setBiography(bio);
            p.setShortName(shortName);
            p.setCode(code);
            p.setRef(ref);

            p = profileService.savePatent(p);
            log.debug("Saved new patent: " + p);
        }

        return p;

    }

    private BiographySkill processBiographySkill(Biography bio, Element element) throws DomainException {
        BiographySkill bs = null;

        if (element != null) {

            bs = new BiographySkill();

            // this sets the recordcreator and recordstatus
            processCommonProperties(bs, element);

            String skillS = element.attributeValue("code");
            String proficienyS = element.attributeValue("proficiency");
            String yearsS = element.element("years").getStringValue();

            Skill skill = referenceDataService.getSkillByCode(skillS);
            ProficiencyCd proficiency = ProficiencyCd.valueOf(proficienyS);

            bs.setBiography(bio);
            bs.setSkill(skill);
            bs.setProficiency(proficiency);
            bs.setYears(Integer.parseInt(yearsS));

            bs = profileService.saveBiographySkill(bs);
            log.debug("Saved new biographyskill: " + bs);
        }

        return bs;
    }

    private BiographyCity processCity(Biography bio, Element element) throws DomainException {
        BiographyCity bc = null;

        if (element != null) {

            bc = new BiographyCity();

            // this sets the recordcreator and recordstatus
            processCommonProperties(bc, element);

            String cityS = element.attributeValue("code");

            City city = referenceDataService.getCityByCode(cityS);

            bc.setBiography(bio);
            bc.setCity(city);

            bc = profileService.saveBiographyCity(bc);
            log.debug("Saved new biographycity: " + bc);
        }

        return bc;

    }

    private List<BiographyDepartment> processBiographyDepartments(Biography bio, List<Element> departments) throws DomainException {
        List<BiographyDepartment> result = null;

        if (departments != null) {

            result = new ArrayList<BiographyDepartment>();

            for (Element department : departments) {
                BiographyDepartment bd = new BiographyDepartment();


                // this sets the recordcreator and recordstatus
                processCommonProperties(bd, department);

                String deptName = department.attributeValue("name");
                String companyS = department.attributeValue("companyCode");
                String divisionName = department.attributeValue("divisionName");
                Company company = corporateService.getCompanyByCode(companyS);
                Department dept = corporateService.getDepartment(deptName, company.getId(), divisionName);

                bd.setBiography(bio);
                bd.setDepartment(dept);
                bd = profileService.saveBiographyDepartment(bd);

                log.debug("Saved new biographydepartment: " + bd);

                result.add(bd);
            }

        }

        return result;
    }

    private BiographySkilledRole processBiographySkilledRole(Biography bio, Element element) throws DomainException {
        BiographySkilledRole bsr = null;

        if (element != null) {

            bsr = new BiographySkilledRole();

            // this sets the recordcreator and recordstatus
            processCommonProperties(bsr, element);

            String roleS = element.attributeValue("code");
            String yearsS = element.element("years").getStringValue();
            SkilledRole sr = referenceDataService.getSkilledRoleByCode(roleS);

            if (sr == null) {
                throw new DomainException("Could not find SkilledRole for: " + roleS);
            }

            bsr.setBiography(bio);
            bsr.setSkilledRole(sr);
            bsr.setYears(Integer.parseInt(yearsS));

            bsr = profileService.saveBiographySkilledRole(bsr);
            log.debug("Saved new biographyskilledrole: " + bsr);
        }

        return bsr;

    }

    private Award processAward(Biography bio, Element element) throws DataCreatorException, DomainException {
        Award award = null;

        try {
            if (element != null) {

                award = new Award();

                // this sets the recordcreator and recordstatus
                processCommonProperties(award, element);

                String name = element.element("name").getStringValue();
                String issuedS = element.element("issued").getStringValue();
                String issuer = element.element("issuer").getStringValue();

                Date issued = df.parse(issuedS);

                award.setBiography(bio);
                award.setAwardName(name);
                award.setIssuedDate(issued);
                award.setIssuer(issuer);

                award = profileService.saveAward(award);
                log.debug("Saved new award: " + award);
            }
        } catch (ParseException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
        return award;
    }

    private User createRandomUser(String username) throws DomainException {

        User u = new User();
        u.setUsername(username.toLowerCase());
        u.setEmail(RandomStringUtils.randomAlphabetic(5) + "@tpi.com");
        u.setPassword("tpi");
        u.setStatus(UserStatusCd.ACTIVE);
        u = userService.saveUser(u);

        Role r = userService.getRoleByStatusCode("ROLE_USER");

        UserRole ur = new UserRole(u, r);
        userService.saveUserRole(ur);

        return u;
    }

    private void processCommonProperties(AbstractEntity entity, Element element) {
        if (element != null && entity != null) {
            String recordcreator = element.attributeValue("recordcreator");
            String recordstatus = element.attributeValue("recordstatus");

            if (StringUtils.isNotBlank(recordcreator)) {
                try {
                    RecordCreatorTypeCd creator = RecordCreatorTypeCd.valueOf(recordcreator);
                    entity.setRecordCreatorType(creator);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }

            if (StringUtils.isNotBlank(recordstatus)) {
                try {
                    RecordStatusTypeCd status = RecordStatusTypeCd.valueOf(recordstatus);
                    entity.setRecordStatusType(status);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } else {
            log.error("AbstractEntity or Element is null!!");
        }

    }

    public static void main(String[] args) {
        DummyPersonDataCreator c = new DummyPersonDataCreator(null, null, null, null, null, null);
        String s = "this \tis a \ntest";

        System.out.println(s);
        System.out.println(c.normalizeString(s));

        String trueS = "true";
        String falseS = "false";

        System.out.println(Boolean.parseBoolean(trueS));
        System.out.println(Boolean.parseBoolean(falseS));
    }
}
