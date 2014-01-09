package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Sep 28, 2009
 * Time: 10:32:11 AM
 * Responsibility: A Person might have received awards for work performed
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
public class Award extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 3L;
    private Biography biography;
    private String awardName;
    private Date issuedDate;
    private String issuer;

    @ManyToOne(optional = false, targetEntity = Biography.class)
    @JoinColumn
    @ContainedIn
    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }

    @Field
    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    @Temporal(TemporalType.DATE)
    @Field
    @DateBridge(resolution = Resolution.YEAR)
    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    @Field
    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Award {");
        sb.append(super.toString());
        sb.append(", awardName='").append(awardName).append('\'');
        sb.append(", issuedDate=").append(issuedDate);
        sb.append(", issuer='").append(issuer).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
