package com.tps.tpi.model.objects.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 26, 2009
 * Time: 3:25:51 PM
 * Responsibility:
 */
public class CompanyDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1012L;
    private String contactName;
    private String contactPhone;
    private String parent;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
