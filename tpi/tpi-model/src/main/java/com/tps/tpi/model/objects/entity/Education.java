package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.exception.ModelException;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 1:18:48 PM
 * Responsibility: A person entity has an association with education which contains all related educational data
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
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Education extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 11L;
    private Person person;
    private List<Degree> degrees;
    private List<EducationCertification> educationCertifications;
    private List<Language> languages;

    @ManyToOne(optional = false, targetEntity = Person.class)
    @ContainedIn
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @OneToMany(mappedBy = "education")
    @IndexedEmbedded
    public List<Degree> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<Degree> degrees) {
        this.degrees = degrees;
    }

    @OneToMany(mappedBy = "education")
    @IndexedEmbedded
    public List<EducationCertification> getEducationCertifications() {
        return educationCertifications;
    }

    public void setEducationCertifications(List<EducationCertification> educationCertifications) {
        this.educationCertifications = educationCertifications;
    }

    @OneToMany(mappedBy = "education")
    @IndexedEmbedded
    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Education");
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }

    public void addLanguage(Language language) throws ModelException {
        if (languages == null) {
            languages = new ArrayList<Language>();
        }

        if (StringUtils.isBlank(language.getId())) {
            throw new ModelException("Language entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Language bs : languages) {
            if (StringUtils.equals(language.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            languages.add(language);
        }
    }

    public void addDegree(Degree degree) throws ModelException {
        if (degrees == null) {
            degrees = new ArrayList<Degree>();
        }

        if (StringUtils.isBlank(degree.getId())) {
            throw new ModelException("Degree entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Degree bs : degrees) {
            if (StringUtils.equals(degree.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            degrees.add(degree);
        }
    }

    public void addCertification(EducationCertification certification) throws ModelException {
        if (educationCertifications == null) {
            educationCertifications = new ArrayList<EducationCertification>();
        }

        if (StringUtils.isBlank(certification.getId())) {
            throw new ModelException("Language entity needs to already be persisted");
        }

        boolean isNew = true;

        for (EducationCertification ec : educationCertifications) {
            if (StringUtils.equals(certification.getId(), ec.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            educationCertifications.add(certification);
        }
    }
}
