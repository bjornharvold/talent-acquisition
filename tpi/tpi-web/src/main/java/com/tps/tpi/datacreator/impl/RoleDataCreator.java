/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.model.objects.entity.Role;
import com.tps.tpi.service.UserService;
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
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 * Inserts required roles into the system
 */
@Component("roleDataCreator")
public class RoleDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(RoleDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private final UserService service;
    private final Resource file;

    @Autowired
    public RoleDataCreator(UserService service) {
        this.service = service;
        this.file = new ClassPathResource("user_roles.xml");
    }

    public void create() throws DataCreatorException {

        if (file.exists()) {
            processCreation();
        } else {
            throw new DataCreatorException("XML file could not be found");
        }

        log.debug("Populated " + populated + " roles in db");
        log.debug("Omitted " + omitted + " roles from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            persist(parseXMLFile());

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    private List<Role> parseXMLFile() throws IOException {
        List<Role> result = new ArrayList<Role>();

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> roles = document.selectNodes("//roles/role");

            Role role = null;

            for (Element e : roles) {
                List<Element> cells = e.elements();

                if (cells.size() == 2) {

                    String name = cells.get(0).getTextTrim();
                    String description = cells.get(1).getTextTrim();

                    role = new Role();
                    role.setCode(name);
                    role.setShortName(name);
                    role.setLongName(name);
                    role.setDescription(description);

                    result.add(role);
                } else {
                    log.error("Not the right file. Expecting 2 tokens, found " + cells.size());
                }
            }
        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            System.exit(1);
        }

        return result;
    }

    /**
     * Saves the admin users to the db before the application becomes active
     *
     * @param roles
     * @throws DataCreatorException
     *
     */
    private void persist(List<Role> roles) throws DataCreatorException {
        List<Role> dbList = new ArrayList<Role>();
        try {

            for (Role role : roles) {
                Role tmp = service.getRoleByStatusCode(role.getCode());

                if (tmp == null) {
                    dbList.add(role);
                    populated++;
                } else {
                    log.debug("Role already exists with status code: " + role.getCode());
                    omitted++;
                }
            }

            // ready fr save all
            if (dbList.size() > 0) {
                for (Role role : dbList) {
                    service.saveRole(role);
                }
            }
        } catch (Exception e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }
}