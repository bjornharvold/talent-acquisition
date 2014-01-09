/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.Industry;
import com.tps.tpi.model.objects.entity.IndustryGroup;
import com.tps.tpi.model.objects.entity.Source;
import com.tps.tpi.model.objects.enums.SourceTypeCd;
import com.tps.tpi.service.ReferenceDataService;
import com.tps.tpi.service.SourcingService;
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
import java.util.Date;
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 * It expects a regular xml file to parse and create skills and skillgroup objects for.
 * Xml looks like this:
 * <industries>
 * <industrygroup code="industrygroup.agricultural.production" en_US="Agricultural Production">
 * <industrygroup code="industrygroup.crops" en_US="Crops">
 * <industry code="industry.cash Grains" en_US="Cash Grains"/>
 * <industry code="industry.wheat" en_US="Wheat"/>
 */
@Component("industryDataCreator")
public class IndustryDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(IndustryDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final ReferenceDataService service;
    private final SourcingService sourcingService;
    private final Resource file = new ClassPathResource("industries.xml");

    @Autowired
    public IndustryDataCreator(ReferenceDataService service, SourcingService sourcingService) {
        this.service = service;
        this.sourcingService = sourcingService;
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

        log.debug("Populated " + populated + " industry groups in db");
        log.debug("Omitted " + omitted + " industry groups from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processIndustryGroups();

            // uncomment if you want the class to create resource bundle string for default language
            // handleResourceBundleEntries();

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    /**
     * Print out all resource bundle entries for industrygroup and industry
     */
    private void handleResourceBundleEntries() {
        try {
            List<IndustryGroup> industryGroupList = service.getIndustryGroups(null, null, null);
            List<Industry> industryList = service.getIndustries(null, null, null);

            // print statements for resource bundles
            System.out.println("#==================================== INDUSTRY GROUPS ====================================");
            for (IndustryGroup ig : industryGroupList) {
                System.out.println(ig.getCode() + "=" + ig.getShortName());
            }
            System.out.println("#==================================== END INDUSTRY GROUPS ====================================");

            // print statements for resource bundles
            System.out.println("#==================================== INDUSTRIES ====================================");
            for (Industry industry : industryList) {
                System.out.println(industry.getCode() + "=" + industry.getShortName());
            }
            System.out.println("#==================================== END INDUSTRIES ====================================");
        } catch (DomainException e) {
            log.error("Could not retrieve lists: " + e.getMessage(), e);
        }

    }

    /**
     * This could have been written nicer. Right now there's persistence code at the bottom
     * in a method that's called parse xml. lame!
     *
     * @throws java.io.IOException
     * @throws com.tps.tpi.datacreator.DataCreatorException
     *
     */
    private void processIndustryGroups() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            Element rootElement = document.getRootElement();

            List<Element> industrygroups = rootElement.elements();

            if (industrygroups != null) {

                for (Element e : industrygroups) {
                    processIndustryGroup(null, e);
                }

            }


        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    /**
     * Handles parsing out the. This method will go commit and recurse down into the xml tree
     *
     * @param region
     */
    private IndustryGroup processIndustryGroup(IndustryGroup parent, Element region) throws DataCreatorException {
        // first we commit the region
        IndustryGroup ig = null;
        String code = region.attributeValue("code");
        String defaultLanguage = region.attributeValue("en_US");

        try {
            if (StringUtils.isNotBlank(code)) {
                ig = service.getIndustryGroupByCode(code);

                if (ig == null) {
                    log.debug("IndustryGroup with code: " + code + " does not exist. Create new one.");
                    ig = new IndustryGroup();
                    ig.setCode(code);
                    ig.setShortName(defaultLanguage);

                    if (parent != null) {
                        ig.setParent(parent);
                    }
                    ig = service.saveIndustryGroup(ig);

                    log.debug("Saved new IndustryGroup in db: " + ig);

                    // now we source it
                    Source source = new Source(ig.getId(), SourceTypeCd.INDUSTRYGROUP, ig.getShortName(), null, null, null, null, new Date());
                    source = sourcingService.saveSourceFromGTE(source);
                    populated++;
                } else {
                    log.debug("IndustryGroup already exists: " + ig);
                    omitted++;
                }

                // now we create industries or child subIndustryGroup for
                List<Element> subIndustryGroup = region.elements("industrygroup");

                if (subIndustryGroup != null) {
                    for (Element child : subIndustryGroup) {
                        processIndustryGroup(ig, child);
                    }
                }

                List<Element> industries = region.elements("industry");

                if (industries != null) {
                    for (Element industry : industries) {
                        processIndustry(ig, industry);
                    }

                }
            } else {
                log.error("IndustryGroup element is missing an attribute named code");
            }
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return ig;
    }

    /**
     * Saves a skill and associates it to a skillgroup
     *
     * @param ig
     * @param industry
     */
    private Industry processIndustry(IndustryGroup ig, Element industry) throws DataCreatorException {
        Industry result = null;
        String code = industry.attributeValue("code");
        String defaultLanguage = industry.attributeValue("en_US");

        try {
            result = service.getIndustryByCode(code);

            if (result == null) {
                result = new Industry();
                result.setCode(code);
                result.setShortName(defaultLanguage);
                result.setIndustryGroup(ig);

                result = service.saveIndustry(result);
                log.debug("Saved new result in db: " + result);

                // now we source it
                Source source = new Source(result.getId(), SourceTypeCd.INDUSTRY, result.getShortName(), null, null, null, null, new Date());
                source = sourcingService.saveSourceFromGTE(source);
            } else {
                log.debug("Industry already exists: " + result);
            }

        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return result;
    }

}