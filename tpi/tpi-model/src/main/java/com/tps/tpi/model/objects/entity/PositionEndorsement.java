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
 * Responsibility: Endorsement for a position a person held in a department
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
public class PositionEndorsement extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 37L;
    private Position position;
    private Endorsement endorsement;

    @ManyToOne(optional = false, targetEntity = Position.class)
    @JoinColumn
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position language) {
        this.position = language;
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