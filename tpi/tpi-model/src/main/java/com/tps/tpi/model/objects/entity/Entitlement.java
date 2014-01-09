package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.enums.EntitlementTypeCd;
import com.tps.tpi.model.objects.enums.VisibilityCd;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 8, 2009
 * Time: 3:21:14 PM
 * Responsibility: For every fields described on this entity, a person can set whether he wants that fields to be displayed or not
 */
@Entity
@TypeDefs(
        {
                @TypeDef(name = "type",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.EntitlementTypeCd")}
                ),
                @TypeDef(name = "visibility",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.VisibilityCd")}
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
public class Entitlement extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 40L;
    private EntitlementTypeCd type;
    private VisibilityCd firstName;
    private VisibilityCd lastName;
    // TODO complete dataset here
    private Person person;
    
    @ManyToOne(optional = true, targetEntity = Person.class)
    @JoinColumn
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Type(type = "type")
    @Column(nullable = false)
    public EntitlementTypeCd getType() {
        return type;
    }

    public void setType(EntitlementTypeCd type) {
        this.type = type;
    }

    @Type(type = "visibility")
    @Column(nullable = false)
    public VisibilityCd getFirstName() {
        return firstName;
    }

    public void setFirstName(VisibilityCd firstName) {
        this.firstName = firstName;
    }

    @Type(type = "visibility")
    public VisibilityCd getLastName() {
        return lastName;
    }

    public void setLastName(VisibilityCd lastName) {
        this.lastName = lastName;
    }
}
