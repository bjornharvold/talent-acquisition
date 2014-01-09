package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 22, 2009
 * Time: 11:28:56 PM
 * Responsibility:
 */
public class CountryDto extends AbstractReferenceDataDto implements Serializable {
    private final static long serialVersionUID = 1042L;
    private List<CityDto> cities;

    public void addCity(CityDto dto) {
        if (cities == null) {
            cities = new ArrayList<CityDto>();
        }

        boolean isNew = true;
        for (CityDto aDto : cities) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            cities.add(dto);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CountryDto {");
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
