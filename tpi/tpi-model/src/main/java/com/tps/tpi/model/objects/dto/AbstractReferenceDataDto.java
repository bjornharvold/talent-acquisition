package com.tps.tpi.model.objects.dto;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 12:14:31 PM
 * Responsibility:
 */
public class AbstractReferenceDataDto extends AbstractDto {
    private String code;
    private String description;
    private String shortName;
    private String longName;
    private String ref;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AbstractReferenceDataDto {");
        sb.append(super.toString());
        sb.append(", code='").append(code).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", longName='").append(longName).append('\'');
        sb.append(", ref='").append(ref).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
