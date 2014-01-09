package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.enums.PublicationTypeCd;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 12:19:57 PM
 * Responsibility: A publication written by this person
 */
@Entity
@Indexed
@TypeDefs(
        {
                @TypeDef(name = "type",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.PublicationTypeCd")}
                ),
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
public class Publication extends AbstractReferenceDataEntity implements Serializable {
    private final static long serialVersionUID = 24L;
    private PublicationTypeCd type;
    private Date issueDate;
    private Biography biography;

    @ManyToOne(optional = false, targetEntity = Biography.class)
    @JoinColumn
    @ContainedIn
    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }

    @Type(type = "type")
    @Field(index = org.hibernate.search.annotations.Index.UN_TOKENIZED)
    public PublicationTypeCd getType() {
        return type;
    }

    public void setType(PublicationTypeCd type) {
        this.type = type;
    }

    @Field
    @DateBridge(resolution = Resolution.YEAR)
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Publication {");
        sb.append(super.toString());
        sb.append(", type=").append(type);
        sb.append(", issueDate=").append(issueDate);
        sb.append('}');
        return sb.toString();
    }
}
