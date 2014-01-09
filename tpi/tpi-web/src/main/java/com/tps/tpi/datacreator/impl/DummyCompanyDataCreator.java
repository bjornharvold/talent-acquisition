/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.enums.SourceTypeCd;
import com.tps.tpi.service.CorporateService;
import com.tps.tpi.service.ReferenceDataService;
import com.tps.tpi.service.SearchService;
import com.tps.tpi.service.SourcingService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 */
@Component("dummyCompanyDataCreator")
public class DummyCompanyDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(DummyCompanyDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final CorporateService service;
    private final ReferenceDataService referenceDataService;
    private final SourcingService sourcingService;
    private final SearchService searchService;
    private final Resource file = new ClassPathResource("dummydata/companies.xml");

    @Autowired
    public DummyCompanyDataCreator(CorporateService service, ReferenceDataService referenceDataService,
                                   SourcingService sourcingService, SearchService searchService) {
        this.service = service;
        this.referenceDataService = referenceDataService;
        this.sourcingService = sourcingService;
        this.searchService = searchService;
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
            searchService.purgeSearchIndex(Company.class);
            searchService.purgeSearchIndex(CompanyIndustry.class);

            processCompanies();

            searchService.optimize();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new DataCreatorException(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    /**
     * This could have been written nicer. Right now there's persistence code at the bottom
     * in a method that's called parse xml. lame!
     *
     * @throws java.io.IOException
     * @throws DataCreatorException
     */
    private void processCompanies() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> companies = document.selectNodes("//companies/company");

            for (Element e : companies) {
                String shortName = e.element("shortname").getStringValue();
                Company company = service.getCompanyByShortName(shortName);

                if (company == null) {
                    company = new Company();
                    String code = e.element("code").getStringValue();
                    String longName = e.element("longname").getStringValue();

                    company.setLongName(code);
                    company.setCode(code);
                    company.setLongName(longName);
                    company.setShortName(shortName);

                    Element namesE = e.element("names");
                    if (namesE != null) {
                        List<Element> names = namesE.elements("name");
                        List<CompanyName> cNames = new ArrayList<CompanyName>(names.size());
                        for (Element name : names) {
                            CompanyName cn = new CompanyName();
                            cn.setShortName(name.getStringValue());
                            cNames.add(cn);
                        }
                        company.setCompanyNames(cNames);
                    }
                    populated++;
                    company = service.saveCompany(company);

                    log.debug("Saved new company: " + company);

                    // now we source it
                    Source source = new Source(company.getId(), SourceTypeCd.COMPANY, company.getShortName(), null, null, null, null, new Date());
                    source = sourcingService.saveSourceFromGTE(source);
                } else {
                    log.debug("Company already exists: " + company);
                    omitted++;
                }

                // now we need to create the company structure
                Element divisionsE = e.element("divisions");

                if (divisionsE != null) {
                    List<Element> divisions = divisionsE.elements("division");

                    for (Element division : divisions) {
                        processDivision(company, null, division);
                    }
                }

                // now we need to create titles
                Element titlesE = e.element("titles");

                if (titlesE != null) {
                    List<Element> titles = titlesE.elements("title");

                    for (Element title : titles) {
                        processTitle(company, title);
                    }
                }

                // now we need to create products
                Element productsE = e.element("products");

                if (productsE != null) {
                    List<Element> products = productsE.elements("product");

                    for (Element product : products) {
                        processProduct(company, product);
                    }
                }

                // now we need to create products
                Element industriesE = e.element("industries");

                if (industriesE != null) {
                    List<Element> industry = industriesE.elements("industry");

                    for (Element ind : industry) {
                        company.addCompanyIndustry(processCompanyIndustry(company, ind));
                    }
                }

                // finally we update the company entity to create all the lucene indexes
                service.updateCompany(company);
            }
        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            System.exit(1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    private void processProduct(Company company, Element product) throws DomainException {
        String name = product.attributeValue("name");

        Product prod = service.getProductByName(company.getId(), name);

        if (prod == null) {
            prod = new Product();
            prod.setCompany(company);
            prod.setShortName(name);

            prod = service.saveProduct(prod);
            log.debug("Saved new Product: " + prod);

            // now we source it
            Source source = new Source(prod.getId(), SourceTypeCd.PRODUCT, prod.getShortName(), null, null, null, null, new Date());
            source = sourcingService.saveSourceFromGTE(source);
        } else {
            log.debug("Product already exists: " + prod);
        }
    }

    private CompanyIndustry processCompanyIndustry(Company company, Element industry) throws DomainException {
        String code = industry.attributeValue("code");

        CompanyIndustry ci = service.getCompanyIndustryByCode(company.getId(), code);

        if (ci == null) {
            ci = new CompanyIndustry();
            ci.setCompany(company);
            ci.setIndustry(referenceDataService.getIndustryByCode(code));

            ci = service.saveCompanyIndustry(ci);
            log.debug("Saved new CompanyIndustry: " + ci);
        } else {
            log.debug("CompanyIndustry already exists: " + ci);
        }

        return ci;
    }

    private void processTitle(Company company, Element title) throws DomainException {
        String code = title.attributeValue("code");
        CompanyTitle ct = service.getCompanyTitleByCode(company.getId(), code);

        if (ct == null) {
            ct = new CompanyTitle();
            Title t = referenceDataService.getTitleByCode(code);

            if (t == null) {
                throw new DomainException("Cannot find title for: " + code);
            }

            ct.setTitle(t);
            ct.setCompany(company);
            String rating = title.element("rating").getStringValue();
            ct.setRating(Integer.parseInt(rating));

            ct = service.saveCompanyTitle(ct);
            log.debug("Saved new company title: " + ct);

            // now we source it
            Source source = new Source(ct.getId(), SourceTypeCd.COMPANYTITLE, ct.getTitle().getShortName(), null, null, null, null, new Date());
            source = sourcingService.saveSourceFromGTE(source);
        } else {
            log.debug("CompanyTitle already exists: " + ct);
        }
    }

    private void processDivision(Company company, Division parent, Element division) throws DomainException {
        String name = division.attributeValue("name");
        Division d = service.getDivisionByName(company.getId(), name);

        if (d == null) {
            d = new Division();
            d.setShortName(name);
            d.setCompany(company);
            d.setParent(parent);

            d = service.saveDivision(d);

            log.debug("Saved new division: " + d);

            // now we source it
            Source source = new Source(d.getId(), SourceTypeCd.DIVISION, d.getShortName(), null, null, null, null, new Date());
            source = sourcingService.saveSourceFromGTE(source);
        } else {
            log.debug("Division already exists: " + d);
        }

        // now we process sub divisions
        List<Element> subDivisions = division.elements("division");
        if (subDivisions != null) {
            for (Element subDivision : subDivisions) {
                processDivision(company, d, subDivision);
            }

        }

        // and departments
        Element departments = division.element("departments");

        if (departments != null) {
            List<Element> depList = departments.elements("department");

            for (Element department : depList) {
                processDepartment(d, null, department);
            }

        }
    }

    private void processDepartment(Division d, Department parent, Element department) throws DomainException {
        String name = department.attributeValue("name");

        Department dept = service.getDepartmentByName(d.getId(), name);

        if (dept == null) {
            dept = new Department();
            dept.setShortName(name);
            dept.setDivision(d);
            dept.setParent(parent);
            dept = service.saveDepartment(dept);

            log.debug("Saved new division: " + dept);

            // now we source it
            Source source = new Source(dept.getId(), SourceTypeCd.DEPARTMENT, dept.getShortName(), null, null, null, null, new Date());
            source = sourcingService.saveSourceFromGTE(source);
        } else {
            log.debug("Department already exists: " + dept);
        }

        // now we handle sub departments
        List<Element> subDepts = department.elements("department");

        if (subDepts != null) {
            for (Element subDept : subDepts) {
                processDepartment(d, dept, subDept);
            }
        }
    }
}