package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.exception.ModelException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 11:50:44 AM
 * Responsibility: A one to one relationship with person. Person has a professional bio with much related data
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
public class Biography extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 21L;
    private Person person;
    private String summary;
    private List<BiographySkilledRole> biographySkilledRoles;
    private List<BiographyDepartment> biographyDepartments;
    private List<BiographyCity> biographyCities;
    private List<BiographySkill> biographySkills;
    private List<BiographyCompanyTitle> biographyCompanyTitles;
    private List<Patent> patents;
    private List<Award> awards;
    private List<Publication> publications;

    @ManyToOne(optional = false, targetEntity = Person.class)
    @ContainedIn
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Field
    @Column(length = 4000)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @OneToMany(mappedBy = "biography", targetEntity = BiographySkilledRole.class)
    @IndexedEmbedded
    public List<BiographySkilledRole> getBiographySkilledRoles() {
        return biographySkilledRoles;
    }

    public void setBiographySkilledRoles(List<BiographySkilledRole> biographySkilledRoles) {
        this.biographySkilledRoles = biographySkilledRoles;
    }

    @OneToMany(mappedBy = "biography", targetEntity = BiographyDepartment.class)
    @IndexedEmbedded
    public List<BiographyDepartment> getBiographyDepartments() {
        return biographyDepartments;
    }

    public void setBiographyDepartments(List<BiographyDepartment> departments) {
        this.biographyDepartments = departments;
    }

    @OneToMany(mappedBy = "biography", targetEntity = BiographyCity.class)
    @IndexedEmbedded
    public List<BiographyCity> getBiographyCities() {
        return biographyCities;
    }

    public void setBiographyCities(List<BiographyCity> biographyCities) {
        this.biographyCities = biographyCities;
    }

    @OneToMany(mappedBy = "biography", targetEntity = BiographySkill.class)
    @IndexedEmbedded
    public List<BiographySkill> getBiographySkills() {
        return biographySkills;
    }

    public void setBiographySkills(List<BiographySkill> biographySkills) {
        this.biographySkills = biographySkills;
    }

    @OneToMany(mappedBy = "biography", targetEntity = BiographyCompanyTitle.class)
    @IndexedEmbedded
    public List<BiographyCompanyTitle> getBiographyCompanyTitles() {
        return biographyCompanyTitles;
    }

    public void setBiographyCompanyTitles(List<BiographyCompanyTitle> biographyCompanyTitles) {
        this.biographyCompanyTitles = biographyCompanyTitles;
    }

    @OneToMany(mappedBy = "biography", targetEntity = Patent.class)
    @IndexedEmbedded
    public List<Patent> getPatents() {
        return patents;
    }

    public void setPatents(List<Patent> patents) {
        this.patents = patents;
    }

    @OneToMany(mappedBy = "biography", targetEntity = Award.class)
    @IndexedEmbedded
    public List<Award> getAwards() {
        return awards;
    }

    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }

    @OneToMany(mappedBy = "biography", targetEntity = Publication.class)
    @IndexedEmbedded
    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Biography {");
        sb.append(super.toString());
        sb.append(", summary='").append(summary).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Utility method
     * @param skill
     */
    public void addBiographySkill(BiographySkill skill) throws ModelException {
        if (biographySkills == null) {
            biographySkills = new ArrayList<BiographySkill>();
        }

        if (StringUtils.isBlank(skill.getId())) {
            throw new ModelException("BiographySkill entity needs to already be persisted");
        }

        boolean isNew = true;

        for (BiographySkill bs : biographySkills) {
            if (StringUtils.equals(skill.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographySkills.add(skill);
        }
    }

    public void addBiographyCity(BiographyCity city) throws ModelException {
        if (biographyCities == null) {
            biographyCities = new ArrayList<BiographyCity>();
        }

        if (StringUtils.isBlank(city.getId())) {
            throw new ModelException("BiographyCity entity needs to already be persisted");
        }

        boolean isNew = true;

        for (BiographyCity bs : biographyCities) {
            if (StringUtils.equals(city.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographyCities.add(city);
        }
    }

    public void addBiographySkilledRole(BiographySkilledRole role) throws ModelException {
        if (biographySkilledRoles == null) {
            biographySkilledRoles = new ArrayList<BiographySkilledRole>();
        }

        if (StringUtils.isBlank(role.getId())) {
            throw new ModelException("BiographySkilledRole entity needs to already be persisted");
        }

        boolean isNew = true;

        for (BiographySkilledRole bs : biographySkilledRoles) {
            if (StringUtils.equals(role.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographySkilledRoles.add(role);
        }
    }

    public void addBiographyCompanyTitle(BiographyCompanyTitle title) throws ModelException {
        if (biographyCompanyTitles == null) {
            biographyCompanyTitles = new ArrayList<BiographyCompanyTitle>();
        }

        if (StringUtils.isBlank(title.getId())) {
            throw new ModelException("BiographyCompanyTitle entity needs to already be persisted");
        }

        boolean isNew = true;

        for (BiographyCompanyTitle bs : biographyCompanyTitles) {
            if (StringUtils.equals(title.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographyCompanyTitles.add(title);
        }
    }

    public void addAward(Award award) throws ModelException {
        if (awards == null) {
            awards = new ArrayList<Award>();
        }

        if (StringUtils.isBlank(award.getId())) {
            throw new ModelException("Award entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Award bs : awards) {
            if (StringUtils.equals(award.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            awards.add(award);
        }
    }

    public void addPublication(Publication publication) throws ModelException {
        if (publications == null) {
            publications = new ArrayList<Publication>();
        }

        if (StringUtils.isBlank(publication.getId())) {
            throw new ModelException("Publication entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Publication bs : publications) {
            if (StringUtils.equals(publication.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            publications.add(publication);
        }
    }

    public void addPatent(Patent patent) throws ModelException {
        if (patents == null) {
            patents = new ArrayList<Patent>();
        }

        if (StringUtils.isBlank(patent.getId())) {
            throw new ModelException("Patent entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Patent bs : patents) {
            if (StringUtils.equals(patent.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            patents.add(patent);
        }
    }
}
