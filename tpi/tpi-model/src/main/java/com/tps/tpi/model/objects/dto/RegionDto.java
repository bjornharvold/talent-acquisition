package com.tps.tpi.model.objects.dto;

import com.tps.tpi.model.objects.entity.Region;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 26, 2009
 * Time: 3:29:47 PM
 * Responsibility:
 */
public class RegionDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1035L;
    private String parent;
    private String parentName;
    private List<RegionDto> children;
    private List<CountryDto> countries;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<RegionDto> getChildren() {
        return children;
    }

    public void setChildren(List<RegionDto> children) {
        this.children = children;
    }

    public List<CountryDto> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryDto> countries) {
        this.countries = countries;
    }

    public void addChild(RegionDto dto) {
        if (children == null) {
            children = new ArrayList<RegionDto>();
        }

        boolean isNew = true;
        for (RegionDto aDto : children) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            children.add(dto);
        }
    }

    public void addCountry(CountryDto dto) {
        if (countries == null) {
            countries = new ArrayList<CountryDto>();
        }

        boolean isNew = true;
        for (CountryDto aDto : countries) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            countries.add(dto);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RegionDto {");
        sb.append(super.toString());
        sb.append(", parent='").append(parent).append('\'');
        sb.append(", parentName='").append(parentName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
