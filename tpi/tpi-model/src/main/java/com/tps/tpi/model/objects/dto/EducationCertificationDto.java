package com.tps.tpi.model.objects.dto;

import java.util.Date;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 3:45:59 PM
 * Responsibility:
 */
public class EducationCertificationDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1018L;
    private String education;
    private CertificationDto certification;
    private String title;
    private String issuedBy;
    private Date issueDate;

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public CertificationDto getCertification() {
        return certification;
    }

    public void setCertification(CertificationDto certification) {
        this.certification = certification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}
