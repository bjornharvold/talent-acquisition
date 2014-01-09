package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 1:30:03 PM
 * Responsibility: A person might have received a prominent certificate during school
 */
@Entity
@Indexed
@TypeDefs(
        {
                @TypeDef(name = "recordCreatorType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.RecordCreatorTypeCd")}
                ),
                @TypeDef(name = "recordStatusType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.RecordStatusTypeCd")}
                )
        }
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EducationCertification extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 4L;
    private Education education;
    private Certification certification;
    private String title;
    private String issuedBy;
    private Date issueDate;

    @ManyToOne(optional = false, targetEntity = Education.class)
    @ContainedIn
    public Education getEducation() {
        return education;
    }

    @ManyToOne(optional = false, targetEntity = Certification.class)
    @IndexedEmbedded
    public Certification getCertification() {
        return certification;
    }

    public void setCertification(Certification certification) {
        this.certification = certification;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    @Field
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Field
    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    @Temporal(value = TemporalType.DATE)
    @DateBridge(resolution = Resolution.YEAR)
    @Field
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("EducationCertification");
        sb.append(super.toString());
        sb.append(", certification=").append(certification);
        sb.append(", title='").append(title).append('\'');
        sb.append(", issuedBy='").append(issuedBy).append('\'');
        sb.append(", issueDate=").append(issueDate);
        sb.append('}');
        return sb.toString();
    }
}
