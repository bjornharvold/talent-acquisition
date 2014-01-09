package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.CompanyTitle;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.CompanyTitleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts an CompanyTitle entity to an CompanyTitleDto dto.
 */
public class CompanyTitleConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(CompanyTitleConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof CompanyTitle && dto instanceof CompanyTitleDto) {
            CompanyTitle entity = (CompanyTitle) source;

            ((CompanyTitleDto) dto).setRating(entity.getRating());

            if (entity.getTitle() != null) {
                ((CompanyTitleDto) dto).setTitle(entity.getTitle().getId());
                ((CompanyTitleDto) dto).setTitleName(entity.getTitle().getCode());
            }

            if (entity.getCompany() != null) {
                ((CompanyTitleDto) dto).setCompany(entity.getCompany().getId());
                ((CompanyTitleDto) dto).setCompanyName(entity.getCompany().getShortName());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof CompanyTitleDto && entity instanceof CompanyTitle) {
            CompanyTitleDto dto = (CompanyTitleDto) source;

            // we're not going to create company titles using dtos so this can be empty
        }

        return entity;
    }
    
}