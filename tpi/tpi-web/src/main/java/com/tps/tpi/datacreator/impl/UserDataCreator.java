/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;
import com.tps.tpi.model.objects.entity.Role;
import com.tps.tpi.model.objects.entity.User;
import com.tps.tpi.model.objects.entity.UserRole;
import com.tps.tpi.model.objects.enums.UserStatusCd;
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
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 *
 */
@Component("userDataCreator")
public class UserDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(UserDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private Role role = null;
    private final UserService service;
    private final Resource file = new ClassPathResource("users.xml");

    @Autowired
    public UserDataCreator(UserService service) {
        this.service = service;
    }

    public void create() throws DataCreatorException {

        try {
            if (file.exists()) {
                role = service.getRoleByStatusCode("ROLE_ADMINISTRATOR");
                processCreation();
            } else {
                throw new DataCreatorException("XML file could not be found");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.debug("Populated " + populated + " administrators in db");
        log.debug("Omitted " + omitted + " administrators from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processUsers();

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    /**
     * This could have been written nicer. Right now there's persistence code at the bottom
     * in a method that's called parse xml. lame!
     *
     * @throws IOException
     * @throws DataCreatorException
     */
    private void processUsers() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> users = document.selectNodes("//users/user");

            User user = null;

            for (Element e : users) {
                String username = e.valueOf("@username");
                String password = e.selectSingleNode("//user[@username='" + username + "']/password").getStringValue();
                String firstName = e.selectSingleNode("//user[@username='" + username + "']/firstName").getStringValue();
                String lastName = e.selectSingleNode("//user[@username='" + username + "']/lastName").getStringValue();
                String email = e.selectSingleNode("//user[@username='" + username + "']/email").getStringValue();

                user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                user.setStatus(UserStatusCd.ACTIVE);

                // now we save the user
                User tmp = (User) service.getUserByUsername(user.getUsername());

                if (tmp == null) {
                    populated++;
                    // ready fr save all
                    user = service.saveUser(user);

                    // then we add user roles
                    List<Element> roles = e.selectNodes("//user[@username='" + username + "']/roles/role");
                    for (Element role : roles) {
                        Role r = service.getRoleByStatusCode(role.getText());

                        UserRole ur = new UserRole(user, r);
                        service.saveUserRole(ur);
                    }
                } else {
                    log.debug("Admin user already exists: " + user.getUsername());
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