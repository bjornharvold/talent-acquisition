package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 26, 2009
 * Time: 3:02:41 PM
 * Responsibility:
 */
public class SkillDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1037L;
    private String parent;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
