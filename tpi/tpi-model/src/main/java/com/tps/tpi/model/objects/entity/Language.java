package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.enums.LanguageCd;
import com.tps.tpi.model.objects.enums.LanguageLevelCd;
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
 * Time: 1:31:16 PM
 * Responsibility: A person can speak languages
 */
@Entity
@Indexed
@TypeDefs(
        {
                @TypeDef(name = "type",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.LanguageCd")}
                ),
                @TypeDef(name = "level",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.LanguageLevelCd")}
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
public class Language extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 14L;
    private Education education;
    private LanguageCd type;
    private LanguageLevelCd verbal;
    private LanguageLevelCd readwrite;
    private List<LanguageEndorsement> languageEndorsements;

    @ManyToOne(optional = false, targetEntity = Education.class)
    @ContainedIn
    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }
    
    @Type(type = "type")
    @Field(index = Index.UN_TOKENIZED)
    public LanguageCd getType() {
        return type;
    }

    public void setType(LanguageCd type) {
        this.type = type;
    }

    @Type(type = "level")
    @Field(index = Index.UN_TOKENIZED)
    public LanguageLevelCd getVerbal() {
        return verbal;
    }

    public void setVerbal(LanguageLevelCd verbal) {
        this.verbal = verbal;
    }

    @Type(type = "level")
    @Field(index = Index.UN_TOKENIZED)
    public LanguageLevelCd getReadwrite() {
        return readwrite;
    }

    public void setReadwrite(LanguageLevelCd readwrite) {
        this.readwrite = readwrite;
    }

    @OneToMany(mappedBy = "language")
    public List<LanguageEndorsement> getLanguageEndorsements() {
        return languageEndorsements;
    }

    public void setLanguageEndorsements(List<LanguageEndorsement> languageEndorsements) {
        this.languageEndorsements = languageEndorsements;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Language");
        sb.append(super.toString());
        sb.append(", type=").append(type);
        sb.append(", verbal=").append(verbal);
        sb.append(", readwrite=").append(readwrite);
        sb.append('}');
        return sb.toString();
    }
}
