/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
 */

package com.tps.tpi.web.controller.secure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import com.tps.tpi.service.UserService;
import com.tps.tpi.service.ProfileService;
import com.tps.tpi.model.objects.dto.PrincipalData;

/**
 * User: Bjorn Harvold
 * Date: Aug 26, 2009
 * Time: 5:32:36 PM
 * Responsibility:
 */
@RequestMapping("/secure/dashboard/**")
@Controller
public class DashboardController {
    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
    private final UserService userService;
    private final ProfileService profileService;

    private static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";

    @Autowired
    public DashboardController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    /**
     * This method is called after succesful login. Based on the user's roles, she will either
     * get forwarded to the flex application with role of only user, or the admin space with role
     * administrator
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/dashboard", method = RequestMethod.GET)
    public String dashboard() throws Exception {
        PrincipalData pd = userService.getPrincipal();
        String view = "flexapp";

        if (pd == null) {
            throw new RuntimeException("This is not possible. The user cannot log in and get to this place without having a principal.");
        }

        // save last login on person
        String username = pd.getUsername();
        profileService.updateLastLogin(username);

        for (String role : pd.getRoles()) {
            if (StringUtils.equals(role, ROLE_ADMINISTRATOR)) {
                view = "redirect:/secure/dashboard/admin";
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("Dispatching view: '" + view + "'");
        }

        return view;
    }

    @RequestMapping(value = "/secure/dashboard/flex", method = RequestMethod.GET)
    protected String showFlex() throws Exception {
        String result = "flexapp";

        if (log.isTraceEnabled()) {
            log.trace("Dispatching view: '" + result + "'");
        }

        return result;
    }

    @RequestMapping(value = "/secure/dashboard/admin", method = RequestMethod.GET)
    protected String showAdminDashboard() throws Exception {
        String result = "dashboard";

        if (log.isTraceEnabled()) {
            log.trace("Dispatching view: '" + result + "'");
        }

        return result;
    }
}
