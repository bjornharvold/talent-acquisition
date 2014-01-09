/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.Skill;
import com.tps.tpi.model.objects.entity.SkillGroup;
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
 * <skills>
 * <skillgroup code="skillgroup.technology" en_US="Technology">
 * <skillgroup code="skillgroup.languages" en_US="Languages">
 * <skill code="skill.dot.net">.Net</skill>
 * <skill code="skill.c">C</skill>
 */
@Component("skillDataCreator")
public class SkillDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(SkillDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final ReferenceDataService service;
    private final SourcingService sourcingService;
    private final Resource file = new ClassPathResource("skills.xml");

    @Autowired
    public SkillDataCreator(ReferenceDataService service, SourcingService sourcingService) {
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

        log.debug("Populated " + populated + " skill groups in db");
        log.debug("Omitted " + omitted + " skill groups from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processSkillGroups();

            // uncomment if you want the class to create resource bundle string for default language
            // handleResourceBundleEntries();

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    private void handleResourceBundleEntries() {
        try {
            List<SkillGroup> skillGroups = service.getSkillGroups(null, null, null);
            List<Skill> skills = service.getSkills(null, null, null);

            // print statements for resource bundles
            System.out.println("#==================================== SKILL GROUPS ====================================");
            for (SkillGroup ig : skillGroups) {
                System.out.println(ig.getCode() + "=" + ig.getShortName());
            }
            System.out.println("#==================================== END SKILL GROUPS ====================================");

            // print statements for resource bundles
            System.out.println("#==================================== SKILLS ====================================");
            for (Skill skill : skills) {
                System.out.println(skill.getCode() + "=" + skill.getShortName());
            }
            System.out.println("#==================================== END SKILLS ====================================");
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
    private void processSkillGroups() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            Element skills = document.getRootElement();

            List<Element> skillgroups = skills.elements();

            for (Element e : skillgroups) {
                SkillGroup skillgroup = processSkillGroup(null, e);

            }
        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            throw new DataCreatorException(e.getMessage(), e);
        }

    }

    /**
     * Handles parsing out the. This method will go commit and recurse down into the xml tree
     *
     * @param skillgroup
     */
    private SkillGroup processSkillGroup(SkillGroup parent, Element skillgroup) throws DataCreatorException {
        // first we commit the skillgroup
        SkillGroup sg = null;
        String code = skillgroup.attributeValue("code");
        String defaultLanguage = skillgroup.attributeValue("en_US");

        try {
            if (StringUtils.isNotBlank(code)) {
                sg = service.getSkillGroupByCode(code);

                if (sg == null) {
                    log.debug("SkillGroup with code: " + code + " does not exist. Create new one.");
                    sg = new SkillGroup();
                    sg.setCode(code);
                    sg.setShortName(defaultLanguage);

                    if (parent != null) {
                        sg.setParent(parent);
                    }
                    sg = service.saveSkillGroup(sg);

                    log.debug("Saved new skillgroup in db: " + sg);

                    // now we source it
                    Source source = new Source(sg.getId(), SourceTypeCd.SKILLGROUP, sg.getShortName(), null, null, null, null, new Date());
                    source = sourcingService.saveSourceFromGTE(source);

                    populated++;
                } else {
                    log.debug("Skill group already exists: " + sg);
                    omitted++;
                }

                // no we create skills or child skillgroups for
                List<Element> skillgroups = skillgroup.elements("skillgroup");

                if (skillgroups != null) {
                    for (Element child : skillgroups) {
                        processSkillGroup(sg, child);
                    }
                }

                List<Element> skills = skillgroup.elements("skill");

                if (skills != null) {
                    for (Element skill : skills) {
                        processSkill(sg, skill);
                    }
                }
            } else {
                log.error("SkillGroup element is missing an attribute named code");
            }
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        return sg;
    }

    /**
     * Saves a skill and associates it to a skillgroup
     *
     * @param sg
     * @param skill
     */
    private void processSkill(SkillGroup sg, Element skill) throws DataCreatorException {
        String code = skill.attributeValue("code");
        String defaultLanguage = skill.getStringValue();

        try {
            Skill s = service.getSkillByCode(code);

            if (s == null) {
                s = new Skill();
                s.setCode(code);
                s.setShortName(defaultLanguage);
                s.setParent(sg);

                s = service.saveSkill(s);

                // now we source it
                Source source = new Source(s.getId(), SourceTypeCd.SKILL, s.getShortName(), null, null, null, null, new Date());
                source = sourcingService.saveSourceFromGTE(source);

                log.debug("Saved new skill in db: " + s);
                log.debug("Sourced new skill in db: " + source);
            } else {
                log.debug("Skill already exists: " + s);
            }
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }


}