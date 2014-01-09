package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 26, 2009
 * Time: 3:25:19 PM
 * Responsibility:
 */
public class SkilledRoleDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1038L;
    private String skilledRoleGroup;

    public String getSkilledRoleGroup() {
        return skilledRoleGroup;
    }

    public void setSkilledRoleGroup(String skilledRoleGroup) {
        this.skilledRoleGroup = skilledRoleGroup;
    }
}
