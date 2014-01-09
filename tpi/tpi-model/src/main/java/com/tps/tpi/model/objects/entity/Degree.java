package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.enums.DegreeTypeCd;
import com.tps.tpi.model.objects.enums.MajorTypeCd;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 1:21:51 PM
 * Responsibility: A degree the person completed in school
 */
@Entity
@Indexed
@TypeDefs(
        {
                @TypeDef(name = "type",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.DegreeTypeCd")}
                ),
                @TypeDef(name = "major",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.MajorTypeCd")}
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Degree extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 9L;
    private Education education;
    private School school;
    private DegreeTypeCd type;
    private MajorTypeCd major;
    private Date completedDate;

    @ManyToOne(optional = false, targetEntity = Education.class)
    @JoinColumn
    @ContainedIn
    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    @ManyToOne(optional = true, targetEntity = School.class)
    @JoinColumn
    @IndexedEmbedded
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Type(type = "type")
    @Field(index = Index.UN_TOKENIZED)
    public DegreeTypeCd getType() {
        return type;
    }

    public void setType(DegreeTypeCd type) {
        this.type = type;
    }

    @Type(type = "major")
    @Field(index = Index.UN_TOKENIZED)
    public MajorTypeCd getMajor() {
        return major;
    }

    public void setMajor(MajorTypeCd major) {
        this.major = major;
    }

    @Temporal(value = TemporalType.DATE)
    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Degree {");
        sb.append(super.toString());
        sb.append(", type=").append(type);
        sb.append(", major=").append(major);
        sb.append(", completedDate=").append(completedDate);
        sb.append('}');
        return sb.toString();
    }
}
