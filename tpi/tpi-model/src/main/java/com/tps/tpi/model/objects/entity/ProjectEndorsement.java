package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 2:27:59 PM
 * Responsibility: An endorsement for this person
 */
@Entity
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
public class ProjectEndorsement extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 38L;
    private Project project;
    private Endorsement endorsement;

    @ManyToOne(optional = false, targetEntity = Project.class)
    @JoinColumn
    public Project getProject() {
        return project;
    }

    public void setProject(Project language) {
        this.project = language;
    }

    @ManyToOne(optional = false, targetEntity = Endorsement.class, cascade = CascadeType.ALL)
    @JoinColumn
    public Endorsement getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(Endorsement endorsement) {
        this.endorsement = endorsement;
    }
}