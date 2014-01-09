/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.Source;
import com.tps.tpi.model.objects.entity.Title;
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
@Component("titleDataCreator")
public class TitleDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(TitleDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final ReferenceDataService service;
    private final SourcingService sourcingService;
    private final Resource file = new ClassPathResource("titles.xml");

    @Autowired
    public TitleDataCreator(ReferenceDataService service, SourcingService sourcingService) {
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

        log.debug("Populated " + populated + " titles in db");
        log.debug("Omitted " + omitted + " titles from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processTitles();

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
            List<Title> titles = service.getTitles(null, null, null);

            // print statements for resource bundles
            System.out.println("#==================================== TITLES ====================================");
            for (Title ig : titles) {
                System.out.println(ig.getCode() + "=" + ig.getShortName());
            }
            System.out.println("#==================================== END TITLES ====================================");
        } catch (DomainException e) {
            log.error("Could not retrieve lists: " + e.getMessage(), e);
        }

    }

    /**
     * This could have been written nicer. Right now there's persistence code at the bottom
     * in a method that's called parse xml. lame!
     *
     * @throws java.io.IOException
     * @throws DataCreatorException
     */
    private void processTitles() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> titles = document.selectNodes("//titles/title");

            for (Element e : titles) {
                Title title = new Title();
                String description = e.element("description").getStringValue();
                String code = e.element("code").getStringValue();
                String longName = e.element("longname").getStringValue();
                String shortName = e.element("shortname").getStringValue();

                title.setCode(code);
                title.setLongName(longName);
                title.setShortName(shortName);
                title.setDescription(description);

                Title tmp = service.getTitleByShortName(title.getShortName());

                if (tmp == null) {
                    populated++;
                    title = service.saveTitle(title);

                    // now we source it
                    Source source = new Source(title.getId(), SourceTypeCd.TITLE, title.getShortName(), null, null, null, null, new Date());
                    source = sourcingService.saveSourceFromGTE(source);

                } else {
                    log.debug("Title already exists: " + title);
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