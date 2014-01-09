/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.model.objects.entity.School;
import com.tps.tpi.model.objects.entity.Source;
import com.tps.tpi.model.objects.enums.SourceTypeCd;
import com.tps.tpi.service.ReferenceDataService;
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
import java.util.Date;
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 */
@Component("schoolDataCreator")
public class SchoolDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(SchoolDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final ReferenceDataService service;
    private final SourcingService sourcingService;
    private final Resource file = new ClassPathResource("schools.xml");

    @Autowired
    public SchoolDataCreator(ReferenceDataService service, SourcingService sourcingService) {
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

        log.debug("Populated " + populated + " schools in db");
        log.debug("Omitted " + omitted + " schools from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processSchools();

        } catch (IOException e) {
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
    private void processSchools() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> schools = document.selectNodes("//schools/school");

            for (Element e : schools) {
                String ref = e.element("ref").getStringValue();
                String code = e.element("code").getStringValue();
                String longName = e.element("longname").getStringValue();
                String shortName = e.element("shortname").getStringValue();
                School school = service.getSchoolByShortName(shortName);

                if (school == null) {
                    school = new School();
                    school.setCode(code);
                    school.setLongName(longName);
                    school.setShortName(shortName);
                    school.setRef(ref);

                    populated++;
                    school = service.saveSchool(school);

                    // now we source it
                    Source source = new Source(school.getId(), SourceTypeCd.SCHOOL, null, null, null, null, null, new Date());
                    source = sourcingService.saveSourceFromGTE(source);
                } else {
                    log.debug("School already exists: " + school);
                    omitted++;
                }
            }
        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            System.exit(1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}