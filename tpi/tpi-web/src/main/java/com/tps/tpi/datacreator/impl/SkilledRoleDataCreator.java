/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.SkilledRole;
import com.tps.tpi.model.objects.entity.SkilledRoleGroup;
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
 * It expects a regular xml file to parse and create professional roles and  professional role group objects for.
 * Xml looks like this:
 * <roles>
 * <rolegroup code="rolegroup.manager" en_US="Manager">
 * <role code="role.project.leader">Project Leader</role>
 * <role code="role.project.manager">Project Manager</role>
 */
@Component("skilledRoleDataCreator")
public class SkilledRoleDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(SkilledRoleDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final ReferenceDataService service;
    private final SourcingService sourcingService;
    private final Resource file = new ClassPathResource("skilled_roles.xml");

    @Autowired
    public SkilledRoleDataCreator(ReferenceDataService service, SourcingService sourcingService) {
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

        log.debug("Populated " + populated + " professional role groups in db");
        log.debug("Omitted " + omitted + " professional role groups from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processSkilledRoleGroups();

            // uncomment if you want the class to create resource bundle string for default language
            // handleResourceBundleEntries();

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    private void handleResourceBundleEntries() {
        try {
            List<SkilledRoleGroup> skilledRoleGroups = service.getSkilledRoleGroups(null, null, null);
            List<SkilledRole> skilledRoles = service.getSkilledRoles(null, null, null);

            // print statements for resource bundles
            System.out.println("#==================================== SKILLED ROLE GROUPS ====================================");
            for (SkilledRoleGroup ig : skilledRoleGroups) {
                System.out.println(ig.getCode() + "=" + ig.getShortName());
            }
            System.out.println("#==================================== END SKILED ROLE GROUPS ====================================");

            // print statements for resource bundles
            System.out.println("#==================================== SKILLED ROLES ====================================");
            for (SkilledRole skill : skilledRoles) {
                System.out.println(skill.getCode() + "=" + skill.getShortName());
            }
            System.out.println("#==================================== END SKILLED ROLES ====================================");
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
    private void processSkilledRoleGroups() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            Element roles = document.getRootElement();

            List<Element> skilledrolegroups = roles.elements();

            for (Element e : skilledrolegroups) {
                SkilledRoleGroup group = processSkilledRoleGroup(null, e);

            }
        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            throw new DataCreatorException(e.getMessage(), e);
        }

    }

    /**
     * Handles parsing out the. This method will go commit and recurse down into the xml tree
     *
     * @param group
     */
    private SkilledRoleGroup processSkilledRoleGroup(SkilledRoleGroup parent, Element group) throws DataCreatorException {
        // first we commit the group
        SkilledRoleGroup prg = null;
        String code = group.attributeValue("code");
        String defaultLanguage = group.attributeValue("en_US");

        try {
            if (StringUtils.isNotBlank(code)) {
                prg = service.getSkilledRoleGroupByCode(code);

                if (prg == null) {
                    log.debug("SkilledRoleGroup with code: " + code + " does not exist. Create new one.");
                    prg = new SkilledRoleGroup();
                    prg.setCode(code);
                    prg.setShortName(defaultLanguage);

                    if (parent != null) {
                        prg.setParent(parent);
                    }
                    prg = service.saveSkilledRoleGroup(prg);

                    // now we source it
                    Source source = new Source(prg.getId(), SourceTypeCd.SKILLEDROLEGROUP, prg.getShortName(), null, null, null, null, new Date());
                    source = sourcingService.saveSourceFromGTE(source);

                    log.debug("Saved new group in db: " + prg);
                    populated++;
                } else {
                    log.debug("SkilledRoleGroup already exists: " + prg);
                    omitted++;
                }

                // no we create roles or child groups for
                List<Element> childGroups = group.elements("rolegroup");

                if (childGroups != null) {
                    for (Element child : childGroups) {
                        processSkilledRoleGroup(prg, child);
                    }
                }

                List<Element> roles = group.elements("role");

                if (roles != null) {
                    for (Element skill : roles) {
                        processRole(prg, skill);
                    }
                }
            } else {
                log.error("SkilledRoleGroup element is missing an attribute named code");
            }
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return prg;
    }

    /**
     * Saves a skill and associates it to a skillgroup
     *
     * @param sg
     * @param skill
     */
    private void processRole(SkilledRoleGroup sg, Element skill) throws DataCreatorException {
        String code = skill.attributeValue("code");
        String defaultLanguage = skill.attributeValue("en_US");

        try {
            SkilledRole pr = service.getSkilledRoleByCode(code);

            if (pr == null) {
                pr = new SkilledRole();
                pr.setCode(code);
                pr.setShortName(defaultLanguage);
                pr.setSkilledRoleGroup(sg);

                pr = service.saveSkilledRole(pr);

                // now we source it
                Source source = new Source(pr.getId(), SourceTypeCd.SKILLEDROLE, pr.getShortName(), null, null, null, null, new Date());
                source = sourcingService.saveSourceFromGTE(source);

                log.debug("Saved new professional role in db: " + pr);
            } else {
                log.debug("ProfessionalRole already exists: " + pr);
            }
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }
}