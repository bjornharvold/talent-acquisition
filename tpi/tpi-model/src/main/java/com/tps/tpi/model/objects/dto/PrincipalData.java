package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 8, 2009
 * Time: 2:08:43 PM
 * Responsibility:
 */
public class PrincipalData implements Serializable {
    private final static long serialVersionUID = 1030L;
    private String username;
    private String[] roles;

    public PrincipalData(String username, String[] roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
