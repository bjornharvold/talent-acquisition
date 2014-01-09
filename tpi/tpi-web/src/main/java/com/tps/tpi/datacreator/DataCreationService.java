/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:17:07 AM
 */
public class DataCreationService {
    private final static Logger log = LoggerFactory.getLogger(DataCreationService.class);

    public void init() {
        if (enabled) {
            log.info("DataCreationService is enabled");
            if (dataCreators != null && dataCreators.size() > 0) {
                secureChannel();
                try {
                    for (DataCreator dc : dataCreators) {

                        log.info("Creating data with: " + dc.toString());
                        dc.create();
                        log.info("Success: " + dc.toString());

                    }
                } catch (DataCreatorException e) {
                    log.error("Error creating data! " + e.getMessage(), e);
                }
                unsecureChannel();
            }
        } else {
            log.info("DataCreationService is currently disabled. Check application.properties file to change flag.");
        }
    }

    /**
     * Have to do this in order to save roles as PlatformManager is restrictive
     */
    private void secureChannel() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test", "Test",
                new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_ADMINISTRATOR"), new GrantedAuthorityImpl("ROLE_USER")});
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    /**
     * When everything is done we can go ahead and remove that channel
     */
    private void unsecureChannel() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    // Spring IoC
    private List<DataCreator> dataCreators = null;
    private Boolean enabled = Boolean.TRUE;

    @Required
    public void setDataCreators(List<DataCreator> dataCreators) {
        this.dataCreators = dataCreators;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
