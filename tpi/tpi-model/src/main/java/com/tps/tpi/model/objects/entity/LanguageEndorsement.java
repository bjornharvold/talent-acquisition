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
 * Responsibility: An endorsement for a language
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
public class LanguageEndorsement extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 35L;
    private Language language;
    private Endorsement endorsement;

    @ManyToOne(optional = false, targetEntity = Language.class)
    @JoinColumn
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
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
