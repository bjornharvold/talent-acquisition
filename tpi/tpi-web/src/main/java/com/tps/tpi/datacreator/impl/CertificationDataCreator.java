/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.model.objects.entity.Certification;
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
 *
 */
@Component("certificationDataCreator")
public class CertificationDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(CertificationDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final ReferenceDataService service;
    private final SourcingService sourcingService;
    private final Resource file = new ClassPathResource("certifications.xml");

    @Autowired
    public CertificationDataCreator(ReferenceDataService service, SourcingService sourcingService) {
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

        log.debug("Populated " + populated + " certifications in db");
        log.debug("Omitted " + omitted + " certifications from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processCertifications();

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    /**
     * This could have been written nicer. Right now there's persistence code at the bottom
     * in a method that's called parse xml. lame!
     *
     * @throws java.io.IOException
     * @throws com.tps.tpi.datacreator.DataCreatorException
     */
    private void processCertifications() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> certifications = document.selectNodes("//certifications/certification");

            for (Element e : certifications) {
                Certification cert = new Certification();
                String description = e.element("description").getStringValue();
                String code = e.element("code").getStringValue();
                String longName = e.element("longname").getStringValue();
                String shortName = e.element("shortname").getStringValue();

                cert.setCode(code);
                cert.setLongName(longName);
                cert.setShortName(shortName);
                cert.setDescription(description);

                Certification tmp = service.getCertificationByShortName(cert.getShortName());

                if (tmp == null) {
                    populated++;
                    cert = service.saveCertification(cert);

                    // now we source it
                    Source source = new Source(cert.getId(), SourceTypeCd.CERTIFICATION, cert.getShortName(), null, null, null, null, new Date());
                    source = sourcingService.saveSourceFromGTE(source);

                } else {
                    log.debug("Certification already exists: " + cert);
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