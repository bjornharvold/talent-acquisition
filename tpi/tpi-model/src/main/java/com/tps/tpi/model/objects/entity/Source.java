package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.enums.SourceTypeCd;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Dec 11, 2009
 * Time: 11:25:52 PM
 * Responsibility:
 */
@Entity
@TypeDefs(
        {
                @TypeDef(name = "type",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.SourceTypeCd")}
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
public class Source extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 54L;
    private String sourceId;
    private SourceTypeCd sourceType;
    private String sourceDescription;
    private String sourcerId;
    private SourceTypeCd sourcerType;
    private String sourcerDescription;
    private String workflowId;
    private Date sourceDate;

    public Source() {
    }

    public Source(String sourceId, SourceTypeCd sourceType, String sourceDescription, String sourcerId, SourceTypeCd sourcerType, String sourcerDescription, String workflowId, Date sourceDate) {
        this.sourceId = sourceId;
        this.sourceType = sourceType;
        this.sourceDescription = sourceDescription;
        this.sourcerId = sourcerId;
        this.sourcerType = sourcerType;
        this.sourcerDescription = sourcerDescription;
        this.workflowId = workflowId;
        this.sourceDate = sourceDate;
    }

    @NotNull
    @Column(nullable = false)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @NotNull
    @Column(nullable = false)
    @Type(type = "type")
    public SourceTypeCd getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeCd sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    @NotNull
    @Column(nullable = false)
    public String getSourcerId() {
        return sourcerId;
    }

    public void setSourcerId(String sourcerId) {
        this.sourcerId = sourcerId;
    }

    @NotNull
    @Column(nullable = false)
    @Type(type = "type")
    public SourceTypeCd getSourcerType() {
        return sourcerType;
    }

    public void setSourcerType(SourceTypeCd sourcerType) {
        this.sourcerType = sourcerType;
    }

    public String getSourcerDescription() {
        return sourcerDescription;
    }

    public void setSourcerDescription(String sourcerDescription) {
        this.sourcerDescription = sourcerDescription;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getSourceDate() {
        return sourceDate;
    }

    public void setSourceDate(Date sourceDate) {
        this.sourceDate = sourceDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Source {");
        sb.append(super.toString());
        sb.append(", sourceId='").append(sourceId).append('\'');
        sb.append(", sourceType=").append(sourceType);
        sb.append(", sourceDescription='").append(sourceDescription).append('\'');
        sb.append(", sourcerId='").append(sourcerId).append('\'');
        sb.append(", sourcerType=").append(sourcerType);
        sb.append(", sourcerDescription='").append(sourcerDescription).append('\'');
        sb.append(", workflowId='").append(workflowId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
