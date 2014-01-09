package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 2:27:59 PM
 * Responsibility: An endorsement of a professional skill given by another person
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
public class BiographySkillEndorsement extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 36L;
    private BiographySkill biographySkill;
    private Endorsement endorsement;

    @ManyToOne(optional = false, targetEntity = BiographySkill.class)
    @JoinColumn
    public BiographySkill getBiographySkill() {
        return biographySkill;
    }

    public void setBiographySkill(BiographySkill language) {
        this.biographySkill = language;
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