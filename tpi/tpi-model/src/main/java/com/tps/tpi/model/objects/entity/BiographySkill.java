package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.enums.ProficiencyCd;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 1:35:03 PM
 * Responsibility: A skill the person has held
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
                ),
                @TypeDef(name = "proficiency",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.ProficiencyCd")}
                )
        }
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BiographySkill extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 29L;
    private Biography biography;
    private List<BiographySkillEndorsement> biographySkillEndorsements;
    private Skill skill;
    private ProficiencyCd proficiency;
    private Integer years;

    @ManyToOne(optional = false, targetEntity = Biography.class)
    @ContainedIn
    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }

    @OneToMany(mappedBy = "biographySkill")
    public List<BiographySkillEndorsement> getBiographySkillEndorsements() {
        return biographySkillEndorsements;
    }

    public void setBiographySkillEndorsements(List<BiographySkillEndorsement> biographySkillEndorsements) {
        this.biographySkillEndorsements = biographySkillEndorsements;
    }

    @ManyToOne(optional = false, targetEntity = Skill.class)
    @IndexedEmbedded
    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    @Field(index = Index.UN_TOKENIZED)
    @Type(type = "proficiency")
    public ProficiencyCd getProficiency() {
        return proficiency;
    }

    public void setProficiency(ProficiencyCd proficiency) {
        this.proficiency = proficiency;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }
}
