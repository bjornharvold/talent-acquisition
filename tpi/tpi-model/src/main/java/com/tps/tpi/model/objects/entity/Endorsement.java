package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Sep 28, 2009
 * Time: 10:23:31 AM
 * Responsibility: A person can be endorsed by another person for a specific type
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
public class Endorsement extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 12L;
    private Integer stars;
    private Date endorsedOn;
    private Person endorser;

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Date getEndorsedOn() {
        return endorsedOn;
    }

    public void setEndorsedOn(Date endorsedOn) {
        this.endorsedOn = endorsedOn;
    }

    @ManyToOne(optional = false, targetEntity = Person.class)
    @JoinColumn
    public Person getEndorser() {
        return endorser;
    }

    public void setEndorser(Person endorser) {
        this.endorser = endorser;
    }
}
